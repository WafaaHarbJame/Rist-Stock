package com.app.riststock.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.app.riststock.R
import com.app.riststock.classes.Constants
import com.app.riststock.classes.GlobalData
import com.app.riststock.databinding.ActivityChangeUrlBinding
import com.app.riststock.utils.NumberHandler
import com.app.riststock.utils.UtilityApp

class ChangeUrlActivity : ActivityBase() {
    lateinit var binding: ActivityChangeUrlBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeUrlBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)


        initListeners()


    }

    override fun onBackPressed() {

    }

    private fun initListeners() {
        binding.changeUrlBut.setOnClickListener {
            val url = NumberHandler.arabicToDecimal(binding.urlEt.text.toString())

            if (url.isEmpty()) {
                binding.urlEt.requestFocus()
                binding.urlEt.error = getString(R.string.invalid_input)
                Toast(getString(R.string.invalid_input))
            } else {
                UtilityApp.isFirstRun = false
                GlobalData.ReleaseBaseURL = url
                UtilityApp.appUrl = url
                val intent = Intent(activiy, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}