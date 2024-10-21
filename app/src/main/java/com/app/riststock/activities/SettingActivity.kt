package com.app.riststock.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.app.riststock.R
import com.app.riststock.apiHandler.DataCallBack
import com.app.riststock.classes.Constants
import com.app.riststock.databinding.ActivitySettingBinding
import com.app.riststock.dialogs.ChangeLanguageDialog
import com.app.riststock.utils.UtilityApp

class SettingActivity : ActivityBase() {
    lateinit var binding: ActivitySettingBinding
    var changeLanguageDialog: ChangeLanguageDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        initListeners()
        initButtons()
       binding.keyboardSwitch.isChecked = UtilityApp.isKeyVisible
    }

    private fun initButtons() {
        binding.languageTxt.text =
            if (UtilityApp.language == Constants.Arabic) getString(R.string.arabic) else getString(R.string.english)
        binding.toolBar.mainTitleTV.text = getString(R.string.setting)
        binding.toolBar.mainTitleTV.visibility = View.VISIBLE
        binding.toolBar.logoIcon.visibility = View.GONE
        binding.toolBar.mainBackBtn.visibility = View.VISIBLE
        binding.toolBar.logoutBut.visibility = View.GONE
        binding.toolBar.profileBut.visibility = View.GONE
        binding.toolBar.staticsBut.visibility = View.GONE
        binding.toolBar.mainTitleTV.text = getString(R.string.setting)

    }

    private fun initListeners() {


        binding.keyboardSwitch.setOnCheckedChangeListener { _, isChecked ->
            UtilityApp.setKeyVisible(isChecked)
//            toggleKeyboardVisibility(isChecked)
        }
        binding.staticBut.setOnClickListener {
            val intent = Intent(activiy, StatisticsActivity::class.java)
            startActivity(intent)


        }
        binding.changeLanguageBtn.setOnClickListener {

            if (changeLanguageDialog == null) {
                changeLanguageDialog = ChangeLanguageDialog(activiy, object : DataCallBack {
                    override fun Result(obj: Any?, func: String?, otherData: Any?) {
                        val lang = obj as String
                        setLanguage(lang)
                        val intent = Intent(activiy, Constants.MAIN_ACTIVITY)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                })
                changeLanguageDialog!!.setOnCancelListener { changeLanguageDialog = null }
            }
        }


        binding.toolBar.mainBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Proper handling for back press

        }


    }


}