package com.app.riststock.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.app.riststock.databinding.DialogMyErrorBinding

class MyErrorDialog(
    var activity: Activity,
    titleResId: Int,
    message: String?,
) :
    Dialog(activity) {

    private val dialogMy: MyErrorDialog
        get() = this

    var binding: DialogMyErrorBinding

    init {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogMyErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titleTV.text = context.getString(titleResId)
        binding.messageTV.text = message

        binding.okBtn.setOnClickListener {
            dismiss()
        }

        try {
            show()
        } catch (e: Exception) {
            dismiss()
        }
    }
}