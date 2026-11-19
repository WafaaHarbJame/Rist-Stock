package com.app.riststock.dialogs


import android.app.Activity
import android.app.Dialog
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.app.riststock.R
import com.app.riststock.classes.GlobalData
import com.app.riststock.databinding.DialogSowImageBinding
import com.app.riststock.models.MemberModel
import com.app.riststock.models.ProductImageModel
import com.app.riststock.utils.UtilityApp
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


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
        binding.failLY.refreshBtn.setOnClickListener {
            getProductData(areaId, userId, itemCode, langID)
        }

        getProductData(areaId, userId, itemCode, langID)

        try {
            if (!activity.isFinishing) show()
        } catch (e: Exception) {
            dismiss()
        }

    }

    private fun getProductData(area_id: Int, userId: Int, itemCode: String?, lang_id: String?) {
        binding.loadingLY.loadingProgressLY.visibility = View.VISIBLE
        binding.failLY.failGetDataLY.visibility = View.GONE
        binding.ivAdImageView.visibility = View.GONE
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
                        if (activity.isFinishing || activity.isDestroyed) return

                        if (productsModel?.status == 200) {
                            val imageData = productsModel.imageModel?.image
                            val imageType = productsModel.imageModel?.imageType?.trim()?.lowercase()

                            if (imageData.isNullOrBlank()) {
                                showErrorState(productsModel.message)
                                return
                            }

                            val isUrl = imageType == "url" ||
                                (imageType == null && (imageData.startsWith("http://", ignoreCase = true) ||
                                    imageData.startsWith("https://", ignoreCase = true)))

                            if (isUrl) {
                                loadUrlImage(imageData)
                            } else {
                                loadBase64Image(imageData)
                            }
                        } else {
                            showErrorState(productsModel?.message)
                        }
                    }

                    override fun onError(anError: ANError) {
                        if (activity.isFinishing || activity.isDestroyed) return
                        binding.loadingLY.loadingProgressLY.visibility = View.GONE
                        val message = activity.getString(R.string.fail_to_get_data)
                        binding.failLY.failGetDataLY.visibility = View.VISIBLE
                        GlobalData.errorDialog(activity, R.string.getData, message)
                        Log.d(javaClass.simpleName, "Log Error Login ${anError.errorCode}")
                        Log.d(javaClass.simpleName, "Log Error Login ${anError.errorDetail}")
                    }
                })
    }

    private fun loadUrlImage(imageUrl: String) {
        binding.ivAdImageView.visibility = View.INVISIBLE
        Glide.with(binding.ivAdImageView)
            .load(imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (!activity.isFinishing && !activity.isDestroyed) {
                        showErrorState(null)
                    }
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (!activity.isFinishing && !activity.isDestroyed) {
                        binding.loadingLY.loadingProgressLY.visibility = View.GONE
                        binding.failLY.failGetDataLY.visibility = View.GONE
                        binding.ivAdImageView.visibility = View.VISIBLE
                    }
                    return false
                }
            })
            .into(binding.ivAdImageView)
    }

    private fun loadBase64Image(base64Data: String) {
        try {
            val cleanBase64 = base64Data.substringAfter("base64,", base64Data).trim()
            val decodedString = Base64.decode(cleanBase64, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            if (decodedByte != null) {
                binding.loadingLY.loadingProgressLY.visibility = View.GONE
                binding.failLY.failGetDataLY.visibility = View.GONE
                binding.ivAdImageView.visibility = View.VISIBLE
                Glide.with(binding.ivAdImageView)
                    .load(decodedByte)
                    .into(binding.ivAdImageView)
            } else {
                showErrorState(null)
            }
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, "Log Error decoding base64 image", e)
            showErrorState(null)
        }
    }

    private fun showErrorState(serverMessage: String?) {
        binding.loadingLY.loadingProgressLY.visibility = View.GONE
        binding.ivAdImageView.visibility = View.GONE
        binding.failLY.failGetDataLY.visibility = View.VISIBLE
        val message = serverMessage?.takeIf { it.isNotBlank() }
            ?: activity.getString(R.string.fail_to_get_data)
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun getUrl() {
        url = if (!UtilityApp.appUrl.isNullOrEmpty()) {
            UtilityApp.appUrl!!
        } else {
            GlobalData.ReleaseBaseURL
        }

    }


}
