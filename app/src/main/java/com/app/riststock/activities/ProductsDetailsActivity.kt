package com.app.riststock.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.app.riststock.R
import com.app.riststock.adapters.TransactionAdapter
import com.app.riststock.apiHandler.DataCallBack
import com.app.riststock.classes.Constants
import com.app.riststock.classes.GlobalData
import com.app.riststock.databinding.ActivityDetailsBinding
import com.app.riststock.dialogs.ShowImageDialog
import com.app.riststock.models.ProductResultsModel
import com.app.riststock.models.ProductsModel
import com.app.riststock.models.Transaction
import com.app.riststock.models.TransactionResultsModel
import com.app.riststock.utils.UtilityApp

class ProductsDetailsActivity : ActivityBase() {

    lateinit var binding: ActivityDetailsBinding
    private var barcode: String? = null
    private var userId: Int = 0
    private var areaId: Int = 0
    private var langID: String = ""
    var list: MutableList<Transaction>? = null
    private var url: String = ""
    private var imageUrl: String = ""
    private var groupId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        val user = UtilityApp.userData
        userId = user?.userId ?: 0
        areaId = user?.areaId ?: 0
        langID = user?.langId ?: "ar"
        groupId = user?.userGroupId ?: 0

        getUrl()
        list = mutableListOf()
        binding.recycler.layoutManager = LinearLayoutManager(activiy)


        getDataIntent()
        initData()
        initListeners()

    }


    private fun initListeners() {
        binding.toolBar.mainBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()

        }

        binding.referenceTv.setOnClickListener {
            getProductData(userId, barcode, langID)

        }
        binding.ImageIv.setOnClickListener {
            if (imageUrl.isNotEmpty()) {
                val showImageDialog = ShowImageDialog(
                    activiy, imageUrl
                )
                showImageDialog.show()
            }

        }


    }

    private fun initData() {
        binding.toolBar.mainTitleTV.visibility = View.VISIBLE
        binding.toolBar.logoIcon.visibility = View.GONE
        binding.toolBar.mainBackBtn.visibility = View.VISIBLE
        binding.toolBar.logoutBut.visibility = View.GONE
        binding.toolBar.profileBut.visibility = View.GONE
        binding.toolBar.staticsBut.visibility = View.GONE

    }

    private fun getProductData(userId: Int, barcode: String?, lang_id: String?) {
        binding.loadingLY.loadingProgressLY.visibility = View.VISIBLE
        binding.failLY.failGetDataLY.visibility = View.GONE
        binding.recycler.visibility = View.GONE
        binding.dataLy.visibility = View.GONE

        Log.d(javaClass.simpleName, "Log userId${userId}")
        Log.d(javaClass.simpleName, "Log areaId${areaId}")
        Log.d(javaClass.simpleName, "Log barcode${barcode}")

        AndroidNetworking.get(url.plus("Products"))
            .addQueryParameter("user_id", userId.toString())
            .addQueryParameter("barcode", barcode)
            .addQueryParameter("lang_id", lang_id)
            .setPriority(Priority.LOW)
            .build()
            .getAsObject(
                ProductResultsModel::class.java,
                object : ParsedRequestListener<ProductResultsModel> {
                    override fun onResponse(result: ProductResultsModel?) {
                        binding.loadingLY.loadingProgressLY.visibility = View.GONE
                        if (result?.status == 200) {
                            val itemCode = result.data?.itemCode ?: ""
                            initProductData(result.data)
                            Log.d(javaClass.simpleName, "Log userId${userId}")
                            Log.d(javaClass.simpleName, "Log langID${langID}")
                            Log.d(javaClass.simpleName, "Log areaId${areaId}")

                            getTransactionData(userId, areaId, itemCode, langID)

                        } else {
                            binding.dataLy.visibility = View.GONE
                            binding.failLY.failGetDataLY.visibility = View.VISIBLE
                        }


                    }

                    override fun onError(anError: ANError) {
                        val message = getString(R.string.fail_to_get_data)
                        GlobalData.hideProgressDialog()
                        GlobalData.errorDialog(activiy, R.string.login, message)

                        Log.d(javaClass.simpleName, "Log Error Login ${anError.errorCode}")
                        Log.d(javaClass.simpleName, "Log Error Login ${anError.errorDetail}")
                    }
                })
    }

    private fun initProductData(productsModel: ProductsModel?) {
//        if (groupId == 2) {
//            binding.descriptionLy.visibility = View.GONE
//            binding.productLy.visibility = View.GONE
//        } else {
//            binding.descriptionLy.visibility = View.VISIBLE
//            binding.productLy.visibility = View.VISIBLE
//
//        }
        binding.codeTv.text = productsModel?.itemCode
        imageUrl = productsModel?.image ?: ""
        binding.referenceTv.text = productsModel?.itemRef?.replace(" ", "") ?: ""
        binding.descriptionTv.text = productsModel?.description?.replace(" ", "") ?: ""
        if (productsModel?.image.isNullOrEmpty()) {
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
            GlobalData.glideImage(activiy, productsModel?.image, binding.ImageIv)
        }


    }

    private fun getDataIntent() {

        val bundle = intent.extras
        barcode = bundle?.getString(Constants.barcode)

        binding.toolBar.mainTitleTV.text = barcode
        Log.d(javaClass.simpleName, "Log barcode : $barcode")
        binding.barcodeTv.text = barcode

        getProductData(userId, barcode, langID)


    }

    private fun getTransactionData(userId: Int, areaId: Int, item_code: String, lang_id: String) {

        AndroidNetworking.get(url.plus("Products/Transaction"))
            .addQueryParameter("user_id", userId.toString())
            .addQueryParameter("area_id", areaId.toString())
            .addQueryParameter("item_code", item_code)
            .addQueryParameter("lang_id", lang_id)
            .setPriority(Priority.LOW)
            .build()
            .getAsObject(
                TransactionResultsModel::class.java,
                object : ParsedRequestListener<TransactionResultsModel> {
                    override fun onResponse(result: TransactionResultsModel?) {
                        binding.loadingLY.loadingProgressLY.visibility = View.GONE
                        binding.dataLy.visibility = View.VISIBLE
                        if (result?.status == 200) {
                            if ((result.data?.size ?: 0) > 0) {
                                binding.recycler.visibility = View.VISIBLE
                                list = result.data as MutableList<Transaction>?
                                initAdapter()
                            } else {
                                binding.noDataTxt.visibility = View.VISIBLE
                                binding.recycler.visibility = View.GONE

                            }


                        } else {
                            binding.dataLy.visibility = View.GONE
                            binding.failLY.failGetDataLY.visibility = View.VISIBLE
                        }


                    }

                    override fun onError(anError: ANError) {
                        val message = getString(R.string.fail_to_get_data)
                        GlobalData.hideProgressDialog()
                        GlobalData.errorDialog(activiy, R.string.login, message)

                        Log.d(javaClass.simpleName, "Log Error Login ${anError.errorCode}")
                        Log.d(javaClass.simpleName, "Log Error Login ${anError.errorDetail}")
                    }
                })
    }

    private fun initAdapter() {
        val adapter = TransactionAdapter(activiy, list, object : DataCallBack {
            override fun Result(obj: Any?, func: String?, otherData: Any?) {
            }
        })
        binding.recycler.adapter = adapter

    }

    fun getUrl(): Unit {
        url = if (!UtilityApp.appUrl.isNullOrEmpty()) {
            UtilityApp.appUrl!!
        } else {
            GlobalData.ReleaseBaseURL
        }

    }

}