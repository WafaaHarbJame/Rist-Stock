package com.app.riststock.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.app.riststock.databinding.DialogMyConfirmBinding

class MyConfirmDialog(
    context: Context?,
    message: String?,
    okStr: Int,
    cancelStr: Int,
    okCall: Click?,
    cancelCall: Click?
) :
    Dialog(context!!) {

    abstract class Click {
        abstract fun click()
    }

    lateinit var binding: DialogMyConfirmBinding

    init {
        try {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestWindowFeature(Window.FEATURE_NO_TITLE) //before

            binding = DialogMyConfirmBinding.inflate(layoutInflater)
            setContentView(binding.root)

            setCancelable(false)


            binding.messageTxt.text = message
            binding.okBtn.text = context?.getString(okStr)
            binding.cancelBtn.text = context?.getString(cancelStr)
//
            binding.okBtn.setOnClickListener {
                okCall?.click()
                dismiss()
            }
            binding.cancelBtn.setOnClickListener {
                cancelCall?.click()
                dismiss()
            }

            show()
        } catch (e: Exception) {
            if (isShowing) dismiss()
        }
    }
}
