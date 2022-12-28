package com.app.riststock

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.TextView.OnEditorActionListener
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.app.riststock.activities.*
import com.app.riststock.classes.Constants
import com.app.riststock.classes.GlobalData
import com.app.riststock.databinding.ActivityMainBinding
import com.app.riststock.dialogs.MyConfirmDialog
import com.app.riststock.dialogs.ShowImageDialog
import com.app.riststock.models.*
import com.app.riststock.utils.UtilityApp
import com.github.dhaval2404.form_validation.rule.NonEmptyRule
import com.github.dhaval2404.form_validation.validation.FormValidator
import com.kcode.permissionslib.main.OnRequestPermissionsCallBack
import com.kcode.permissionslib.main.PermissionCompat


class MainActivity : ActivityBase() {
    lateinit var binding: ActivityMainBinding
    var confirmDialog: MyConfirmDialog? = null
    private val ZBAR_CAMERA_PERMISSION = 1
    private var scanLauncher: ActivityResultLauncher<Intent>? = null
    private var userId: Int = 0
    private var user: MemberModel? = null
    private var areaId: Int = 0
    private var groupId: Int = 0
    private var quantity: Int = 0
    private var barcodeText: String? = null
    private var langID: String = ""
    private var url: String = ""
    private var itemCode: String? = null

    private var validationCount = 0
    private val VALIDATION_ITEMS = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        user = UtilityApp.userData
        userId = user?.userId ?: 0
        areaId = user?.areaId ?: 0
        groupId = user?.userGroupId ?: 0
        langID = user?.langId ?: "ar"

        if (UtilityApp.isFirstRun) {
            val intent = Intent(activiy, ChangeUrlActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            if (!UtilityApp.isLogin) {
                val intent = Intent(activiy, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.barcodeTv.requestFocus()
        showDetails()
        getUrl()
        scanLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult? ->
            if (result?.resultCode == Activity.RESULT_OK) {
                val bundle = result.data?.extras
                barcodeText = bundle?.getString(Constants.code).toString()
                getProductData(userId, barcodeText, langID)

            }

        }

        initTextListener()
        initListeners()
        initButtons()
    }

    private fun showDetails() {
        if (groupId == 1) {
            binding.showDetailsBut.visibility = View.GONE
        } else {
            binding.showDetailsBut.visibility = View.VISIBLE

        }
    }

    private fun initTextListener() {

        val textListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.toString()?.isNotEmpty() == true) {
                    validationCount++
                } else {
                    if (validationCount > 0)
                        validationCount--
                }
                initButtons()
            }
        }

        binding.codeTv.addTextChangedListener(textListener)
        binding.addQuantityTv.addTextChangedListener(textListener)

    }

    fun initButtons() {
        when (validationCount) {
            1 -> {
                binding.clearBut.background =
                    ContextCompat.getDrawable(
                        activiy,
                        R.drawable.round_corner_fill_white_stroke_white
                    )
                binding.clearBut.isEnabled = true
                binding.clearBut.setTextColor(
                    ContextCompat.getColor(
                        activiy,
                        R.color.colorPrimaryDark
                    )

                )


            }
            VALIDATION_ITEMS -> {
                binding.addBut.background =
                    ContextCompat.getDrawable(
                        activiy,
                        R.drawable.round_corner_green_fill
                    )
                binding.showDetailsBut.background =
                    ContextCompat.getDrawable(
                        activiy,
                        R.drawable.round_corner_fill_blue_stroke_primary
                    )
                binding.addBut.isEnabled = true
                binding.showDetailsBut.isEnabled = true
            }
            0 -> {
                binding.clearBut.background =
                    ContextCompat.getDrawable(
                        activiy,
                        R.drawable.round_corner_fill_white_ligth_stroke_gray
                    )
                binding.addBut.background =
                    ContextCompat.getDrawable(
                        activiy,
                        R.drawable.round_corner_green_light_fill
                    )
                binding.clearBut.setTextColor(ContextCompat.getColor(activiy, R.color.gray10))

                binding.showDetailsBut.background =
                    ContextCompat.getDrawable(
                        activiy,
                        R.drawable.round_corner_light_blue_fill
                    )


                binding.clearBut.isEnabled = false
                binding.addBut.isEnabled = false
                binding.showDetailsBut.isEnabled = false
            }
        }


    }

    private fun initListeners() {

        binding.toolBar.profileBut.setOnClickListener {
            val intent = Intent(activiy, UserDetailsActivity::class.java)
            startActivity(intent)

        }
        binding.addBut.setOnClickListener {
            if (isValidForm()) {
                quantity = binding.addQuantityTv.text.toString().toInt()
                addProductAsUser(quantity)

            }


        }

        binding.ImageIv.setOnClickListener {
            if (itemCode?.isNotEmpty() == true) {
                val showImageDialog = ShowImageDialog(
                    activiy, itemCode
                )
                showImageDialog.show()
            }

        }

        binding.scanBut.setOnClickListener {
            hideSoftKeyboard(activiy)
            checkCameraPermission()
        }

        binding.toolBar.logoutBut.setOnClickListener {
            val okClick = object : MyConfirmDialog.Click() {
                override fun click() {
                    UtilityApp.logOut()
                    val intent = Intent(activiy, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            }
            if (confirmDialog == null) {
                confirmDialog = MyConfirmDialog(
                    activiy,
                    getString(R.string.want_signout),
                    R.string.log_out,
                    R.string.cancel,
                    okClick,
                    null
                )
                confirmDialog!!.setOnDismissListener {
                    confirmDialog = null
                }
            }

        }



        binding.barcodeTv.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->
            barcodeText = binding.barcodeTv.text.toString()
            if (actionId == EditorInfo.IME_ACTION_DONE && barcodeText != null) {
                if (!binding.barcodeTv.text.isNullOrEmpty()) {
                    getProductData(userId, barcodeText, langID)
                } else {
                    Toast(getString(R.string.enter_barcode))
                    binding.barcodeTv.error = getString(R.string.enter_barcode)
                }

                return@OnEditorActionListener true
            }
            false
        })

        binding.showDetailsBut.setOnClickListener {
            if (!binding.barcodeTv.text.isNullOrEmpty()) {
                val intent = Intent(activiy, ProductsDetailsActivity::class.java)
                intent.putExtra(Constants.barcode, barcodeText)
//            intent.putExtra(Constants.barcode, "9556456990682")
                startActivity(intent)
            } else {
                Toast(getString(R.string.enter_barcode))
            }


        }

        binding.clearBut.setOnClickListener {
            clearData()

        }


        binding.toolBar.staticsBut.setOnClickListener {
            hideSoftKeyboard(activiy)
            val intent = Intent(activiy, SettingActivity::class.java)
            startActivity(intent)
        }


    }

    private fun clearData() {
        barcodeText = ""
        itemCode = ""
        binding.barcodeTv.requestFocus()
//        showSoftKeyboard(binding.barcodeTv,activiy)
        binding.barcodeTv.setText("")
        binding.codeTv.text = ""
        binding.referenceTv.text = ""
        binding.descriptionTv.text = ""
        binding.packTv.text = ""
        binding.priceTv.text = ""
        binding.oldQuantityTv.text = ""
        binding.addQuantityTv.setText("")
        binding.ImageIv.background = ContextCompat.getDrawable(
            activiy,
            R.drawable.round_corner_fill_dark_gray_stroke
        )
        binding.ImageIv.setImageDrawable(
            ContextCompat.getDrawable(
                activiy,
                R.drawable.ic_image_not_found
            )
        )

        binding.quantityLy.background =
            ContextCompat.getDrawable(
                activiy,
                R.drawable.round_corner_fill_gray_stroke_light_gray
            )
        binding.addQuantityLb.setTextColor(
            ContextCompat.getColor(
                activiy,
                R.color.gray9
            )
        )
        binding.addQuantityTv.error = null
        binding.barcodeTv.error = null

        validationCount = 0
        initButtons()
    }

    private fun getProductData(userId: Int, barcode: String?, lang_id: String?) {
        GlobalData.progressDialog(activiy, R.string.scanning, R.string.getData)
        AndroidNetworking.get(url.plus("Products"))
            .addQueryParameter("user_id", userId.toString())
            .addQueryParameter("barcode", barcode)
            .addQueryParameter("lang_id", lang_id)
            .setPriority(Priority.LOW)
            .build()
            .getAsObject(
                ProductResultsModel::class.java,
                object : ParsedRequestListener<ProductResultsModel> {
                    override fun onResponse(productsModel: ProductResultsModel?) {
                        GlobalData.hideProgressDialog()

                        if (productsModel?.status == 200) {
                            binding.addQuantityTv.requestFocus()

                            showSoftKeyboard(binding.addQuantityTv, activiy)
                            itemCode = productsModel.data?.itemCode

                            initData(productsModel.data)

                            binding.quantityLy.background =
                                ContextCompat.getDrawable(
                                    activiy,
                                    R.drawable.round_corner_fill_gray_stroke_blue
                                )
                            binding.addQuantityLb.setTextColor(
                                ContextCompat.getColor(
                                    activiy,
                                    R.color.colorPrimary
                                )
                            )

                        } else {
                            GlobalData.hideProgressDialog()
                            var message = getString(R.string.fail_to_get_data)
                            message = productsModel?.message ?: getString(R.string.fail_to_get_data)
                            barcodeText = ""
                            binding.barcodeTv.setText("")
                            binding.barcodeTv.requestFocus()
                            showSoftKeyboard(binding.barcodeTv, activiy)
                            Toast(message)

                        }


                    }

                    override fun onError(anError: ANError) {
                        val message = getString(R.string.fail_to_get_data)
                        GlobalData.hideProgressDialog()
                        GlobalData.errorDialog(activiy, R.string.getData, message)

                        Log.d(javaClass.simpleName, "Log Error Login ${anError.errorCode}")
                        Log.d(javaClass.simpleName, "Log Error Login ${anError.errorDetail}")
                    }
                })
    }

    private fun initData(productsModel: ProductsModel?) {
        binding.barcodeTv.setText(barcodeText)

        binding.codeTv.text = productsModel?.itemCode
        binding.referenceTv.text = productsModel?.itemRef
        binding.descriptionTv.text = productsModel?.description
        binding.packTv.text = productsModel?.packing.toString()
        binding.priceTv.text = productsModel?.price.toString()
        binding.packTv.text = productsModel?.packing.toString()
        binding.oldQuantityTv.text = productsModel?.quantity.toString()
        itemCode = productsModel?.itemCode ?: ""
        if (productsModel?.itemCode.isNullOrEmpty()) {
            binding.ImageIv.setImageDrawable(
                ContextCompat.getDrawable(
                    activiy,
                    R.drawable.ic_image_not_found_red
                )
            )
            binding.ImageIv.background = ContextCompat.getDrawable(
                activiy,
                R.drawable.round_corner_fill_dark_red_stroke
            )
        } else {
            binding.ImageIv.setImageDrawable(
                ContextCompat.getDrawable(
                    activiy,
                    R.drawable.ic_image_found
                )
            )

            binding.ImageIv.background = ContextCompat.getDrawable(
                activiy,
                R.drawable.round_corner_fill_dark_gray_stroke
            )
        }
    }


    private fun checkCameraPermission() {
        try {
            val builder = PermissionCompat.Builder(activiy)
            builder.addPermissions(
                arrayOf(
                    Manifest.permission.CAMERA,
                )
            )
            builder.addPermissionRationale(getString(R.string.should_allow_permission))
            builder.addRequestPermissionsCallBack(object : OnRequestPermissionsCallBack {
                override fun onGrant() {
                    startScan()
                }

                override fun onDenied(permission: String) {
                    Toast(R.string.some_permission_denied)
                }
            })
            builder.build().request()
        } catch (var2: Exception) {
            var2.printStackTrace()
        }


    }

    private fun startScan() {
        if (ContextCompat.checkSelfPermission(
                activiy,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activiy,
                arrayOf(Manifest.permission.CAMERA),
                ZBAR_CAMERA_PERMISSION
            )
        } else {

            val intent = Intent(activiy, FullScannerActivity::class.java)
            scanLauncher!!.launch(intent)

        }
    }


    private fun addProductAsUser(quantity: Int) {
        AndroidNetworking.get(url.plus("Products/SetQty"))
            .addQueryParameter("user_id", userId.toString())
            .addQueryParameter("area_id", areaId.toString())
            .addQueryParameter("barcode", barcodeText)
            .addQueryParameter("qty", quantity.toString())
            .addQueryParameter("lang_id", langID)
            .setPriority(Priority.LOW)
            .build()
            .getAsObject(PublicModel::class.java, object :
                ParsedRequestListener<PublicModel> {
                override fun onResponse(result: PublicModel) {
                    GlobalData.hideProgressDialog()
                    if (result.status == 200) {

                        Toast(getString(R.string.added_sucess))
                        clearData()

                    } else {
                        var message = getString(R.string.fail_to_add)
                        message = result.message ?: getString(R.string.fail_to_add)
                        GlobalData.errorDialog(activiy, R.string.add_product, message)

                    }
                }

                override fun onError(anError: ANError) {
                    val message = getString(R.string.fail_to_add)
                    GlobalData.hideProgressDialog()
                    GlobalData.errorDialog(activiy, R.string.add_product, message)

                    Log.d(javaClass.simpleName, "Log Error Login ${anError.errorCode}")
                    Log.d(javaClass.simpleName, "Log Error Login ${anError.errorDetail}")
                }
            })

    }

    fun getUrl(): Unit {
        url = if (!UtilityApp.appUrl.isNullOrEmpty()) {
            UtilityApp.appUrl!!
        } else {
            GlobalData.ReleaseBaseURL
        }

    }

    private fun isValidForm(): Boolean {
        return FormValidator.getInstance()
            .addField(binding.barcodeTv, NonEmptyRule(R.string.enter_barcode))
            .addField(binding.addQuantityTv, NonEmptyRule(R.string.enter_quantity))

            .validate()


    }


}