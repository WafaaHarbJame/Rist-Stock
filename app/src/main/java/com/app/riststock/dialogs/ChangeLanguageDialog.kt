package com.app.riststock.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import com.app.riststock.R
import com.app.riststock.apiHandler.DataCallBack
import com.app.riststock.classes.Constants
import com.app.riststock.databinding.DialogChangeLangaugeBinding
import com.app.riststock.utils.UtilityApp

class ChangeLanguageDialog(
        var activity: Activity,
        val dataCallBack: DataCallBack
) : Dialog(activity) {
    var binding: DialogChangeLangaugeBinding

    val dialog: ChangeLanguageDialog
        get() = this

    var lang: String? = null

    init {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = DialogChangeLangaugeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;

        lang = UtilityApp.language

        binding.arabicRB.setOnClickListener {

            lang = Constants.Arabic
        }

        binding.englishRB.setOnClickListener {

            lang = Constants.English
        }

        if (lang == Constants.Arabic)
            binding.arabicRB.performClick()
        else
            binding.englishRB.performClick()

        binding.saveBtn.setOnClickListener {

            if (UtilityApp.language == lang) {
                dismiss()
            } else {
                UtilityApp.language = lang
                dismiss()
                dataCallBack.Result(lang, "", null)
            }

        }

        try {
            if (activity != null && !activity!!.isFinishing) show()
        } catch (e: Exception) {
            dismiss()
        }


    }

}


