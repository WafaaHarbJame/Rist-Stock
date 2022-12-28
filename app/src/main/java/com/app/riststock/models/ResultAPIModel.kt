package com.app.riststock.models

import com.app.riststock.utils.UtilityApp
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap

    class ResultAPIModel<T> {
        //    var data: Any? = null
        var records: MutableList<T>? = null
        var data: T? = null
        private val status: String? = null
        val code: Int? = null
        var message: String? = null
        var token: String? = null
        var user_id: Int? = 0
        var phone: String? = null
        var userType: String? = null
        var userData: MemberModel? = null
        var payment_url: String? = null

//    inline fun <reified T> getResponseData(): T? {
//        var res: T? = null
//        var isArray = false
//        var dataType: Class<*>? = null
//        if (data is ArrayList<*>) {
//            dataType = ArrayList::class.java
//            isArray = true
//        } else {
//            dataType = LinkedTreeMap::class.java
//            isArray = false
//        }
//        val json = Gson().toJson(data, dataType)
////        println("Log response json $json")
////            res = UtilityApp.convertJsonToObject(json, dateClass)
//        res = UtilityApp.convertJsonToObject<T>(json, isArray)
//        return res
//    }

//    inline fun <reified T> getResponseDataArray(): MutableList<T?> {
//        var res: MutableList<T?>? = null
//        val dataType: Class<*> = if (data is ArrayList<*>) {
//            ArrayList::class.java
//        } else {
//            LinkedTreeMap::class.java
//        }
//        val json = Gson().toJson(data, dataType)
//        println("Log response json $json")
////            res = UtilityApp.convertJsonToObject(json, dateClass)
//        res = UtilityApp.convertJsonToObject<MutableList<T?>>(json, dataType is ArrayList<*>)
//        return res
//    }

        val isSuccess: Boolean
            get() = status == "success"
    }

