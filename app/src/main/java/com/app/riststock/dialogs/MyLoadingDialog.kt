package com.app.riststock.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.app.riststock.databinding.DialogMyLoadingBinding


class MyLoadingDialog(
    var activity: Activity,
    titleResId: Int,
    message: String?
) :
    Dialog(activity) {

    var binding: DialogMyLoadingBinding

    init {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogMyLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCancelable(false)

        binding.titleTV.text = context.getString(titleResId)
        binding.messageTV.text = message

        try {
            show()
        } catch (e: Exception) {
            dismiss()
        }
    }
}