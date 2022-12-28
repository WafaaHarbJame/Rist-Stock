package com.app.riststock.dialogs


import android.app.Activity
import android.app.Dialog
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.app.riststock.R
import com.app.riststock.classes.GlobalData
import com.app.riststock.databinding.ActivityFullScannerBinding
import com.app.riststock.databinding.DialogPickImageBinding
import com.app.riststock.databinding.DialogSowImageBinding
import com.app.riststock.models.MemberModel
import com.app.riststock.models.ProductImageModel
import com.app.riststock.models.ProductResultsModel
import com.app.riststock.utils.UtilityApp
import com.bumptech.glide.Glide


class ShowImageDialog(var activity: Activity, var itemCode: String?) : Dialog(
    activity
) {
    var binding: DialogSowImageBinding
    private var url: String = ""
    private var userId: Int = 0
    private var user: MemberModel? = null
    private var areaId: Int = 0
    private var langID: String = ""

    init {
        getUrl()
        user = UtilityApp.userData
        userId = user?.userId ?: 0
        areaId = user?.areaId ?: 0
        langID = user?.langId ?: "ar"

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE) //before

        binding = DialogSowImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        setCancelable(true)
        binding.closeBtn.setOnClickListener { dismiss() }

        getProductData(areaId, userId, itemCode, langID)

        try {
            if (!activity.isFinishing) show()
        } catch (e: Exception) {
            dismiss()
        }

    }

    private fun getProductData(area_id: Int, userId: Int, itemCode: String?, lang_id: String?) {
        binding.loadingLY.loadingProgressLY.visibility=View.VISIBLE
        AndroidNetworking.get(url.plus("Products/Image"))
            .addQueryParameter("user_id", userId.toString())
            .addQueryParameter("item_code", itemCode)
            .addQueryParameter("area_id", area_id.toString())
            .addQueryParameter("lang_id", lang_id)
            .setPriority(Priority.LOW)
            .build()
            .getAsObject(
                ProductImageModel::class.java,
                object : ParsedRequestListener<ProductImageModel> {
                    override fun onResponse(productsModel: ProductImageModel?) {
                        binding.loadingLY.loadingProgressLY.visibility=View.GONE

                        if (productsModel?.status == 200) {
                            val imageData= productsModel.imageModel?.imageData
                            val stringBuffer = StringBuffer()
                            stringBuffer.append(imageData)
                            binding.loadingLY.loadingProgressLY.visibility=View.GONE

                            val decodedString =
                                Base64.decode(stringBuffer.toString(), Base64.DEFAULT)
                            val decodedByte =
                                BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                            binding.ivAdImageView.visibility=View.VISIBLE

                            Glide.with(activity).load(decodedByte).thumbnail(0.02f)
                                .into(binding.ivAdImageView)



                        } else {
                            var message = activity.getString(R.string.fail_to_get_data)
                            message = productsModel?.message
                                ?: activity.getString(R.string.fail_to_get_data)
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                            binding.failLY.failGetDataLY.visibility=View.VISIBLE

                        }


                    }

                    override fun onError(anError: ANError) {
                        val message = activity.getString(R.string.fail_to_get_data)
                        binding.failLY.failGetDataLY.visibility=View.VISIBLE
                        GlobalData.errorDialog(activity, R.string.getData, message)
                        Log.d(javaClass.simpleName, "Log Error Login ${anError.errorCode}")
                        Log.d(javaClass.simpleName, "Log Error Login ${anError.errorDetail}")
                    }
                })
    }

    private fun getUrl() {
        url = if (!UtilityApp.appUrl.isNullOrEmpty()) {
            UtilityApp.appUrl!!
        } else {
            GlobalData.ReleaseBaseURL
        }

    }


}
