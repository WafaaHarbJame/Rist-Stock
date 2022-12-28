package com.app.riststock.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import com.app.riststock.R
import com.app.riststock.classes.GlobalData
import com.app.riststock.databinding.ActivityUserDetailsBinding
import com.app.riststock.dialogs.MyConfirmDialog
import com.app.riststock.utils.UtilityApp

class UserDetailsActivity : ActivityBase() {
    lateinit var binding: ActivityUserDetailsBinding
    private var url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        binding.toolbar.mainTitleTV.text = getString(R.string.user)

        getUrl()
        initData()
        initListeners()

    }

    private fun initListeners() {
        binding.toolbar.mainBackBtn.setOnClickListener {
            onBackPressed()
        }

    }

    private fun initData() {
        binding.toolbar.mainTitleTV.visibility = View.VISIBLE
        binding.toolbar.logoIcon.visibility = View.GONE
        binding.toolbar.mainBackBtn.visibility = View.VISIBLE
        binding.toolbar.logoutBut.visibility = View.GONE
        binding.toolbar.profileBut.visibility = View.GONE
        binding.toolbar.staticsBut.visibility = View.GONE

        val user = UtilityApp.userData

        Log.d(javaClass.simpleName, "Log id : " + user?.userId)
        Log.d(javaClass.simpleName, "Log firstname : " + user?.userName)
        Log.d(javaClass.simpleName, "Log branch : " + user?.branchName)
        Log.d(javaClass.simpleName, "Log groupName : " + user?.groupName)
        Log.d(javaClass.simpleName, "Log groupName : " + user?.groupName)

        binding.usernameTv.text = user?.userName
        binding.categoryTv.text = user?.groupName
        binding.branchTv.text = user?.branchId.plus("_".plus(user?.branchName))
        binding.areaTv.text = user?.areaId.toString()
        binding.LanguageTv.text = user?.langId.toString()


    }

    fun getUrl(): Unit {
        url = if (!UtilityApp.appUrl.isNullOrEmpty()) {
            UtilityApp.appUrl!!
        } else {
            GlobalData.ReleaseBaseURL
        }

    }
}