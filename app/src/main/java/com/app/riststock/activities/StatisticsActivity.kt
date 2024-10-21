package com.app.riststock.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.app.riststock.R
import com.app.riststock.adapters.StaticsAdapter
import com.app.riststock.apiHandler.DataCallBack
import com.app.riststock.classes.GlobalData
import com.app.riststock.databinding.ActivityStatisticsBinding
import com.app.riststock.models.ReportModel
import com.app.riststock.models.ReportModelResult
import com.app.riststock.models.StaticsModel
import com.app.riststock.utils.UtilityApp

class StatisticsActivity : ActivityBase() {

    lateinit var binding: ActivityStatisticsBinding
    private var userId: Int = 0
    private var areaId: Int = 0
    private var langID: String = ""
    var list: MutableList<StaticsModel>? = null
    private var url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        getUrl()
        initData()

        val user = UtilityApp.userData
        userId = user?.userId ?: 0
        areaId = user?.areaId ?: 0
        langID = user?.langId ?: "ar"

        list = mutableListOf()
        binding.recycler.layoutManager = LinearLayoutManager(activiy)


        initListeners()

        getReports(2, 130, "ar")
    }


    private fun initListeners() {
        binding.toolBar.mainBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Proper handling for back press

        }


    }


    private fun initData() {

        binding.toolBar.mainTitleTV.visibility = View.VISIBLE
        binding.toolBar.logoIcon.visibility = View.GONE
        binding.toolBar.mainBackBtn.visibility = View.VISIBLE
        binding.toolBar.logoutBut.visibility = View.GONE
        binding.toolBar.profileBut.visibility = View.GONE
        binding.toolBar.staticsBut.visibility = View.GONE
        binding.toolBar.mainTitleTV.text = getString(R.string.Statistics)

    }

    private fun getReports(userId: Int, areaId: Int, lang_id: String) {
        binding.loadingLY.loadingProgressLY.visibility = View.VISIBLE
        binding.failLY.failGetDataLY.visibility = View.GONE
        binding.dataLy.visibility = View.GONE

        AndroidNetworking.get(url.plus("Products/Reports"))
            .addQueryParameter("admin_id", userId.toString())
            .addQueryParameter("areaid", areaId.toString())
            .addQueryParameter("lang_id", lang_id)
            .setPriority(Priority.LOW)
            .build()
            .getAsObject(
                ReportModelResult::class.java,
                object : ParsedRequestListener<ReportModelResult> {
                    override fun onResponse(result: ReportModelResult?) {
                        binding.loadingLY.loadingProgressLY.visibility = View.GONE
                        binding.dataLy.visibility = View.VISIBLE
                        if (result?.status == 200) {
                            val report = result.data
                            initList(report)
                            initAdapter()


                        } else {
                            binding.dataLy.visibility = View.GONE
                            binding.failLY.failGetDataLY.visibility = View.VISIBLE
                        }


                    }

                    override fun onError(anError: ANError) {
                        val message = getString(R.string.fail_to_get_data)
                        GlobalData.hideProgressDialog()
                        GlobalData.errorDialog(activiy, R.string.login, message)

                        Log.d(javaClass.simpleName, "Log Error Login ${anError.errorCode}")
                        Log.d(javaClass.simpleName, "Log Error Login ${anError.errorDetail}")
                    }
                })
    }

    private fun initAdapter() {
        val adapter = StaticsAdapter(activiy, list, object : DataCallBack {
            override fun Result(obj: Any?, func: String?, otherData: Any?) {
            }
        })
        binding.recycler.adapter = adapter

    }

    fun initList(reportModel: ReportModel?) {

        list?.add(StaticsModel(getString(R.string.website_review_details), areaId))
        list?.add(
            StaticsModel(
                getString(R.string.listed_products_number),
                reportModel?.totalItemTake
            )
        )
        list?.add(
            StaticsModel(
                getString(R.string.reviewed_products_number),
                reportModel?.totalItemAudit
            )
        )
        list?.add(
            StaticsModel(
                getString(R.string.percent_of_referenced_products),
                reportModel?.totpresct
            )
        )
        list?.add(
            StaticsModel(
                getString(R.string.correct_products_number),
                reportModel?.totalItemCorrect
            )
        )
        list?.add(
            StaticsModel(
                getString(R.string.wrong_products_number),
                reportModel?.totalItemUnCorrect
            )
        )
        list?.add(
            StaticsModel(
                getString(R.string.percent_of_wrong_products),
                reportModel?.unCurrectPer
            )
        )


    }

    fun getUrl(): Unit {
        url = if (!UtilityApp.appUrl.isNullOrEmpty()) {
            UtilityApp.appUrl!!
        } else {
            GlobalData.ReleaseBaseURL
        }

    }
}