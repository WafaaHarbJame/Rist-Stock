package com.app.riststock.activities

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.app.riststock.MainActivity
import com.app.riststock.R
import com.app.riststock.classes.Constants
import com.app.riststock.classes.GlobalData
import com.app.riststock.databinding.ActivityLoginBinding
import com.app.riststock.models.LoginResultModel
import com.app.riststock.models.MemberModel
import com.app.riststock.utils.NumberHandler
import com.app.riststock.utils.UtilityApp
import com.github.dhaval2404.form_validation.rule.NonEmptyRule
import com.github.dhaval2404.form_validation.validation.FormValidator


class LoginActivity : ActivityBase() {
    lateinit var binding: ActivityLoginBinding
    private var url: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUrl()
        binding.userEt.requestFocus()
        initListeners()

    }

    override fun onBackPressed() {

    }

    private fun initListeners() {

        binding.loginBut.setOnClickListener {
            hideSoftKeyboard(activiy)
            if (isValidForm())
                onValidationSucceeded()

        }


        binding.showPassBut.setOnClickListener { view1 ->
            if (binding.PasswordEt.transformationMethod
                    .equals(PasswordTransformationMethod.getInstance())
            ) {
                binding.showPassBut.setImageResource(R.drawable.ic_visibility)
                binding.PasswordEt.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                binding.showPassBut.setImageResource(R.drawable.ic_visibility_off)
                binding.PasswordEt.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

    }

    private fun onValidationSucceeded() {

        val userIdStr = NumberHandler.arabicToDecimal(binding.userEt.text.toString())
        val areaIdStr = NumberHandler.arabicToDecimal(binding.areaNumberEt.text.toString())
        val passwordStr = NumberHandler.arabicToDecimal(binding.PasswordEt.text.toString())

        var hasError = false
        if (userIdStr.isEmpty()) {
            binding.userEt.error = getString(R.string.invalid_input)
            hasError = true
        }

        if (passwordStr.isEmpty()) {
            binding.PasswordEt.error = getString(R.string.invalid_input)
            hasError = true
        }
        if (hasError)
            return

        val loginMemberModel = MemberModel()
        loginMemberModel.areaId = areaIdStr.toInt()
        loginMemberModel.userId = userIdStr.toInt()
        loginMemberModel.password = passwordStr

        GlobalData.progressDialog(activiy, R.string.login, R.string.please_wait_login)
        AndroidNetworking.get(url.plus("Account"))
            .addQueryParameter("user_id", userIdStr)
            .addQueryParameter("password", passwordStr)
            .addQueryParameter("area_id", areaIdStr)
            .setPriority(Priority.LOW)
            .build()
            .getAsObject(
                LoginResultModel::class.java,
                object : ParsedRequestListener<LoginResultModel> {
                    override fun onResponse(result: LoginResultModel) {
                        GlobalData.hideProgressDialog()

                        if (result.status == 200) {
                            val user = result.data
                            // do anything with response
                            Log.d(javaClass.simpleName, "Log id : " + user?.userId)
                            Log.d(javaClass.simpleName, "Log firstname : " + user?.userName)
                            Log.d(javaClass.simpleName, "Log branch : " + user?.branchName)
                            Log.d(javaClass.simpleName, "Log groupName : " + user?.groupName)
                            Log.d(javaClass.simpleName, "Log groupName : " + user?.groupName)
                            UtilityApp.userData = user
                            UtilityApp.userData?.password = passwordStr
                            if (user?.langId.equals(Constants.Arabic)) {
                                UtilityApp.language = user?.langId
                            } else {
                                UtilityApp.language = Constants.English

                            }
                            val intent = Intent(activiy, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            var message = getString(R.string.fail_signin)
                            message = result.message ?: getString(R.string.fail_signin)
                            GlobalData.errorDialog(activiy, R.string.login, message)

                        }


                    }

                    override fun onError(anError: ANError) {
                        val message = getString(R.string.fail_signin)
                        GlobalData.hideProgressDialog()
                        GlobalData.errorDialog(activiy, R.string.login, message)

                        Log.d(javaClass.simpleName, "Log Error Login ${anError.errorCode}")
                        Log.d(javaClass.simpleName, "Log Error Login ${anError.errorDetail}")
                    }
                })


//            val apiCaller = ApiCaller(object : DataCallBack {
//                override fun Result(obj: Any?, func: String?, otherData: Any?) {
//
//                    GlobalData.progressDialogHide()
//
//                    if (func == Constants.ERROR) {
//                        val result = obj as String?
//                        var message = getString(R.string.fail_signin)
//                        if (result != null) {
//                            message = result
//
//                        }
//                        GlobalData.errorDialog(activiy, R.string.login, message)
//                    } else {
//                        if (func == Constants.SUCCESS) {
//                            Log.i(javaClass.simpleName,"Log Login Constants.SUCCESS.")
//
////                            val result: MemberModel = obj as MemberModel
//                            val result = obj as (ResultAPIModel<MemberModel?>?)
//                            val memberModel: MemberModel = result?.data ?: MemberModel()
//                            val user = MemberModel().apply {
//                                this.userId = memberModel.userId
//                                this.password = memberModel.password
//                                this.areaId = memberModel.areaId
//                                this.branchId = memberModel.branchId
//                                this.branchName = memberModel.branchName
//                                this.groupId = memberModel.groupId
//                                this.groupName = memberModel.groupName
//                                this.userName = memberModel.userName
//                            }
//                            UtilityApp.userData = user
//                            Log.i(javaClass.simpleName,"Log Login "+user.areaId)
//
//                            val intent = Intent(
//                                activiy,
//                                Constants.MAIN_ACTIVITY
//                            )
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                            startActivity(intent)
//                            finish()
//                        }
//                     else {
//                            Log.i(javaClass.simpleName,"Log Login error.")
//
//                            Toast(getString(R.string.fail_signin))
//                    }
//                }
//
//            }
//        }, false)
//
//        apiCaller.login(userIdStr.toInt(), areaIdStr.toInt(),passwordStr)
//
//
//    } catch (e: Exception)
//    {
//        e.printStackTrace()
//
//   // }
//                    }
    }


    private fun isValidForm(): Boolean {
        return FormValidator.getInstance()
            .addField(binding.userEt, NonEmptyRule(R.string.invalid_input))
            .addField(binding.areaNumberEt, NonEmptyRule(R.string.invalid_area_number))
            .addField(binding.PasswordEt, NonEmptyRule(R.string.invalid_password))

            .validate()
    }

    fun getUrl(): Unit {
        url = if (!UtilityApp.appUrl.isNullOrEmpty()) {
            UtilityApp.appUrl!!
        } else {
            url
        }

    }
}
