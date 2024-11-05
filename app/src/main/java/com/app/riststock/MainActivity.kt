package com.app.riststock

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
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
import com.google.gson.Gson
import com.kcode.permissionslib.main.OnRequestPermissionsCallBack
import com.kcode.permissionslib.main.PermissionCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ActivityBase() {
    lateinit var binding: ActivityMainBinding
    private var debounceJob: Job? = null
    private var isApiCallInProgress = false
    private var lastBarcode: String? = null

    var confirmDialog: MyConfirmDialog? = null
    private val ZBAR_CAMERA_PERMISSION = 1
    private var scanLauncher: ActivityResultLauncher<Intent>? = null
    private var userId: Int = 0
    private var user: MemberModel? = null
    private var areaId: Int = 0
    private var groupId: Int = 0
    private var quantity: Double = 0.0
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

        Log.d(javaClass.simpleName, "Log groupId $groupId")

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
                hideSoftKeyboard(activiy)
                getProductData(userId, barcodeText, langID,false)

            }

        }

        initTextListener()
        initListeners()
        initButtons()
    }

    private fun showDetails() {
        if (groupId == 1) {
            binding.showDetailsBut.visibility = View.GONE
            binding.showDetailsBut.isEnabled = false

            binding.showDetailsBut.background =
                ContextCompat.getDrawable(
                    activiy,
                    R.drawable.round_corner_light_blue_fill
                )
        } else {
            binding.showDetailsBut.visibility = View.VISIBLE
            binding.showDetailsBut.isEnabled = true
            binding.showDetailsBut.background =
                ContextCompat.getDrawable(
                    activiy,
                    R.drawable.round_corner_fill_blue_stroke_primary
                )

        }
    }

    private fun initTextListener() {



//        binding.addQuantityTv.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->
//            barcodeText = binding.barcodeTv.text.toString()
//            if (actionId == EditorInfo.IME_ACTION_DONE && barcodeText.toString().isNotEmpty()) {
//                if (!binding.barcodeTv.text.isNullOrEmpty()) {
//                    hideSoftKeyboard(activiy)
//                    getProductData(userId, barcodeText, langID,false)
//                } else {
//                    Toast(getString(R.string.enter_barcode))
//                    binding.barcodeTv.error = getString(R.string.enter_barcode)
//                }
//
//                return@OnEditorActionListener true
//            }
//            false
//        })



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
        Log.d(javaClass.simpleName, "Log validationCount $validationCount")
        Log.d(javaClass.simpleName, "Log VALIDATION_ITEMS $VALIDATION_ITEMS")

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
//                binding.showDetailsBut.background =
//                    ContextCompat.getDrawable(
//                        activiy,
//                        R.drawable.round_corner_fill_blue_stroke_primary
//                    )
//
//                binding.showDetailsBut.isEnabled = true

                binding.addBut.isEnabled = true
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

//                binding.showDetailsBut.background =
//                    ContextCompat.getDrawable(
//                        activiy,
//                        R.drawable.round_corner_light_blue_fill
//                    )
//                binding.showDetailsBut.isEnabled = false

                binding.clearBut.isEnabled = false
                binding.addBut.isEnabled = false

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
                quantity = binding.addQuantityTv.text.toString().toDoubleOrNull()?:0.0
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
                    finish()
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
                confirmDialog?.setOnDismissListener {
                    confirmDialog = null
                }
            }

        }


//        binding.barcodeTv.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                debounceJob?.cancel()
//                debounceJob = lifecycleScope.launch {
//                    delay(500) // Debounce for 500ms
//                    barcodeText = s.toString()
//                    if (barcodeText?.isNotEmpty() == true) {
//                        hideSoftKeyboard(this@MainActivity)
//                        getProductData(userId, barcodeText, langID,true)
//                    } else {
//                        Toast(getString(R.string.enter_barcode))
//                        binding.barcodeTv.error = getString(R.string.enter_barcode)
//                    }
//                }
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//        })


        binding.barcodeTv.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->

            barcodeText = binding.barcodeTv.text.toString()
            if (actionId == EditorInfo.IME_ACTION_SEARCH && barcodeText.toString().isNotEmpty()) {
                if (!binding.barcodeTv.text.isNullOrEmpty()) {
                    hideSoftKeyboard(activiy)
                    getProductData(userId, barcodeText, langID,false)
                } else {
                    Toast(getString(R.string.enter_barcode))
                    binding.barcodeTv.error = getString(R.string.enter_barcode)
                }

                return@OnEditorActionListener true
            }
            false
        })


        binding.addQuantityTv.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->
            quantity = binding.addQuantityTv.text.toString().toDoubleOrNull()?:0.0
            if (actionId == EditorInfo.IME_ACTION_DONE && quantity!=0.0) {
                if (!binding.addQuantityTv.text.isNullOrEmpty()) {
                    hideSoftKeyboard(activiy)
                    addProductAsUser(quantity)
                } else {
                    Toast(getString(R.string.enter_quantity))
                    binding.addQuantityTv.error = getString(R.string.enter_quantity)
                }

                return@OnEditorActionListener true
            }
            false
        })


//        binding.addQuantityTv.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                debounceJob?.cancel()
//                debounceJob = lifecycleScope.launch {
//                    delay(500) // Debounce for 500ms
//                    quantity = s.toString().toInt()
//                    if (s.toString().isNotEmpty()) {
//                        hideSoftKeyboard(this@MainActivity)
//                        addProductAsUser(quantity)
//
//                    } else {
//                        Toast(getString(R.string.enter_quantity))
//                        binding.addQuantityTv.error = getString(R.string.enter_quantity)
//                    }
//                }
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//        })


        var barcodeDebounceJob: Job? = null

//        binding.barcodeTv.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//
//                val barcodeTextValue = s.toString()
//                barcodeText=barcodeTextValue
//                Log.i("tag", "Scanned Barcode: $barcodeText")
//
//                // Cancel any previously pending job to avoid multiple calls
//                barcodeDebounceJob?.cancel()
//
//                // Only trigger the product lookup if the barcode is not empty
//                barcodeDebounceJob = CoroutineScope(Dispatchers.Main).launch {
//                    delay(500)  // Add a short delay to debounce multiple events (if needed)
//                    if (barcodeText?.isNotEmpty() == true) {
//                        hideSoftKeyboard(activiy)  // Hide keyboard if open
//                        getProductData(userId, barcodeText, langID)
//                    }
//                    else {
//                        Toast(getString(R.string.enter_barcode))
//                        binding.barcodeTv.error = getString(R.string.enter_barcode)
//                    }
//                }
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//        })


//        binding.barcodeTv.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                val barcodeText = s.toString()
//                Log.i("tag", "Scanned Barcode: $barcodeText")
//
//                // Cancel any previously pending job to avoid multiple calls
//                barcodeDebounceJob?.cancel()
//
//                // Only trigger the product lookup if the barcode is not empty
//                barcodeDebounceJob = CoroutineScope(Dispatchers.Main).launch {
//                    delay(500)  // Add a short delay to debounce multiple events (if needed)
//                    if (barcodeText.isNotEmpty()) {
//                        hideSoftKeyboard(activiy)  // Hide keyboard if open
//                        // Pass the current barcodeText as an argument to avoid race conditions
//                        getProductData(userId, barcodeText, langID)
//                    }
//
////                    else {
////                        Toast(getString(R.string.enter_barcode))
////                        binding.barcodeTv.error = getString(R.string.enter_barcode)
////                    }
//                }
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//        })


//        binding.barcodeTv.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                val barcodeText = s.toString()
//                Log.i("tag", "Barcode: $barcodeText")
//
//                // Automatically fetch product data when the barcode is not empty
//                if (barcodeText.isNotEmpty()) {
//                    hideSoftKeyboard(activiy)
//                    getProductData(userId, barcodeText, langID)
//                } else {
//                    Toast(getString(R.string.enter_barcode))
//                    binding.barcodeTv.error = getString(R.string.enter_barcode)
//                }
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//        })


        binding.showDetailsBut.setOnClickListener {
            if (!binding.barcodeTv.text.isNullOrEmpty()) {
                hideSoftKeyboard(activiy)
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

    private fun getProductData(userId: Int, barcode: String?, lang_id: String?,fromScan:Boolean) {
            Log.d("gg","Log getProductData url "+url.plus("Products"))
        if (fromScan) {
            if (lastBarcode == barcode) return // Prevent duplicate calls
            lastBarcode = barcode // Update the last scanned barcode
        }
        GlobalData.progressDialog(activiy, R.string.scanning, R.string.getData)
        AndroidNetworking.get(url.plus("Products"))
            .addQueryParameter("user_id", userId.toString())
            .addQueryParameter("area_id", areaId.toString())
            .addQueryParameter("barcode", barcode)
            .addQueryParameter("lang_id", lang_id)
            .setPriority(Priority.LOW)
            .build()
            .getAsObject(
                ProductResultsModel::class.java,
                object : ParsedRequestListener<ProductResultsModel> {
                    override fun onResponse(productsModel: ProductResultsModel?) {
                        Log.d("gg","Log getProductData params  "+userId+areaId+barcode+lang_id)

                        GlobalData.hideProgressDialog()
                        //62810064095696281006409569
                        if (productsModel?.status == 200) {
                            binding.addQuantityTv.requestFocus()

                            Log.d(
                                javaClass.simpleName,
                                "Log UtilityApp.isKeyVisible ${UtilityApp.isKeyVisible}"
                            )

                            if (UtilityApp.isKeyVisible) {
                                showSoftKeyboard(binding.addQuantityTv, activiy)
                            } else {
                                Handler(Looper.getMainLooper()).postDelayed({
                                    val imm =
                                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                    imm.hideSoftInputFromWindow(
                                        binding.addQuantityTv.windowToken,
                                        0
                                    )
                                }, 100) // Adjust delay time if needed

                            }


                            itemCode = productsModel.data?.itemCode

                            Log.d("gg","Log getProductData  productsModel.data"+Gson().toJson(productsModel.data))


                            initData(productsModel.data, barcode)

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

    private fun initData(productsModel: ProductsModel?, barcodeTex: String?) {
        binding.barcodeTv.setText(barcodeTex)
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


    private fun addProductAsUser(quantity: Double) {
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