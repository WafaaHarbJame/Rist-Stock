package com.app.riststock.classes

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.app.riststock.R
import com.app.riststock.dialogs.MyErrorDialog
import com.app.riststock.dialogs.MyLoadingDialog
import com.bumptech.glide.Glide
import java.lang.Exception
import java.util.*

object GlobalData {

    var BetaBaseURL = "https://risteh.com/StockTake/BH1/"
    var ReleaseBaseURL = "https://risteh.com/StockTake/BH1/"
    var BaseURL = ReleaseBaseURL
    var ApiURL = BaseURL
//    var ApiURL = BaseURL + "api/"
    var ImageURL = BaseURL

    var filterDialogParamList: Map<String, Any>? = null
    var changesMap: Map<String, Any> = HashMap()
    var Position = 0
    var MAIN_PAGER = 0

    var CHANGE_FAV = false
    var IS_FIRST_OPEN = true
    var SHOW_RATE_APP = false
    var SHOW_RATE_RESERVATION = true
    var SHOW_CHECK_VERSION = true

    //    private var progressDialog: AwesomeProgressDialog? = null
    private var progressDialog: MyLoadingDialog? = null
    private var errorDialog: MyErrorDialog? = null
//    private var infoDialog: AwesomeInfoDialog? = null
//    private var successDialog: AwesomeSuccessDialog? = null

    //===========================================================================

    fun glideImage(context: Context, url: String?, imageView: ImageView) {
        var resUrl = ""
        resUrl = if (url?.isNotEmpty() == true)
            url
        else
            ImageURL

        try {

            Glide.with(context)
                .asBitmap()
                .load(resUrl)
                .placeholder(R.drawable.error_logo)
                .into(imageView)

        } catch (e: Exception) {

        }
    }

    fun progressDialog(
        c: Activity,
        title: Int,
        msg: Int?
    ) { // to show dialog insert status = true to dismiss doialog status = false

        if (progressDialog == null) {
            progressDialog = MyLoadingDialog(c, title, c.getString(msg!!))
            progressDialog!!.setOnDismissListener { progressDialog = null }
        }

//        progressDialog = SweetAlertDialog(c, SweetAlertDialog.PROGRESS_TYPE);
//        progressDialog?.progressHelper?.barColor = ContextCompat.getColor(c, R.color.colorPrimary)
//        progressDialog?.titleText = c.getString(title)
//        progressDialog?.contentText = c.getString(msg ?: R.string.please_wait_loading)
//        progressDialog?.setCancelable(false)
//        progressDialog?.show()

//        progressDialog = AwesomeProgressDialog(c)
//            .setTitle(title!!)
//            .setMessage(msg!!)
//            .setColoredCircle(R.color.blue2)
//            .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
//            .setCancelable(false)


    }

    fun hideProgressDialog() {
        progressDialog?.dismiss()
    }


    fun errorDialog(
        c: Activity,
        title: Int,
        msg: String?
    ) { // to show dialog insert status = true to dismiss doialog status = false )

        if (errorDialog == null) {
            errorDialog = MyErrorDialog(c, title, msg?.trim())
            errorDialog!!.setOnDismissListener {
                errorDialog = null
            }
            errorDialog?.show()
        }

    }


    fun Toast(context: Context, vararg msg: String?) {
        var msgs: String? = ""
        for (s in msg) {
            msgs += s
        }
        Toast.makeText(context, msgs, Toast.LENGTH_SHORT).show()
    }

    fun Toast(context: Context, resId: Int) {
        Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT).show()
    }
}