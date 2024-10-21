package com.app.riststock.utils

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.provider.Settings
import com.app.riststock.RootApplication.Companion.instance
import com.app.riststock.classes.Constants
import com.app.riststock.models.MemberModel

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object UtilityApp {
    val appVersion: Int
        get() {
            var pinfo: PackageInfo? = null
            try {
                pinfo = instance!!.packageManager.getPackageInfo(
                    instance!!.packageName, 0
                )

                return pinfo.versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }


            //        int versionCode = BuildConfig.VERSION_CODE;
            return 0
        }

    val unique: String
        get() = Settings.Secure.getString(
            instance!!.contentResolver,
            Settings.Secure.ANDROID_ID
        )

    var currentLocation: String?
        get() {
            return instance?.sharedPManger?.getDataString(Constants.KEY_CURRENT_LOCATION)
        }
        set(location) {
            instance?.sharedPManger?.setData(Constants.KEY_CURRENT_LOCATION, location)
        }

    var locationServiceStarted: Boolean
        get() {
            return instance?.sharedPManger?.getDataBool(Constants.KEY_IS_LOCATION_STARTED, false)!!
        }
        set(isStart) {
            instance?.sharedPManger?.setData(Constants.KEY_IS_LOCATION_STARTED, isStart)
        }

    var isFirstRun: Boolean
        get() {
            return instance?.sharedPManger?.getDataBool(Constants.KEY_FIRST_OPEN, true)!!
        }
        set(isFirstRun) {
            instance?.sharedPManger?.setData(Constants.KEY_FIRST_OPEN, isFirstRun)
        }

    var isSendFCMToken: Boolean
        get() {
            return instance?.sharedPManger?.getDataBool(Constants.KEY_IS_SEND_FCM)!!
        }
        set(isSend) {
            instance?.sharedPManger?.setData(Constants.KEY_IS_SEND_FCM, isSend)
        }

    val isRatedApp: Boolean
        get() {
            return instance?.sharedPManger?.getDataBool(Constants.KEY_IS_RATE_APP, false)!!
        }

    fun setIsRateApp(isRateApp: Boolean) {
        instance?.sharedPManger?.setData(Constants.KEY_IS_RATE_APP, isRateApp)
    }


    val isKeyVisible: Boolean
        get() {
            return instance?.sharedPManger?.getDataBool(Constants.KEY_IS_KEYBOARD_VISIBLE, true) == true
        }

    fun setKeyVisible(isVisible: Boolean) {
        instance?.sharedPManger?.setData(Constants.KEY_IS_KEYBOARD_VISIBLE, isVisible)
    }

    val isLogin: Boolean
        get() {
            val userData: String? = instance?.sharedPManger?.getDataString(Constants.KEY_MEMBER)
            return userData != null
        }

    fun logOut() {
        instance?.sharedPManger?.setData(Constants.KEY_MEMBER, null)
    }

    var fCMToken: String?
        get() {
            return instance?.sharedPManger?.getDataString(Constants.KEY_FIREBASE_TOKEN)
        }
        set(fcmToken) {
            instance?.sharedPManger?.setData(Constants.KEY_FIREBASE_TOKEN, fcmToken)
        }

    var language: String?
        get() {
            return instance?.sharedPManger?.getDataString(Constants.KEY_MEMBER_LANGUAGE)
        }
        set(language) {
            instance?.sharedPManger?.setData(Constants.KEY_MEMBER_LANGUAGE, language)
        }

    var appUrl: String?
        get() {
            return instance?.sharedPManger?.getDataString(Constants.KEY_URL)
        }
        set(appUrl) {
            instance?.sharedPManger?.setData(Constants.KEY_URL, appUrl)
        }

    fun clearSearchData() {
        instance?.sharedPManger?.setData(Constants.KEY_MY_SEARCH_DATA, null)
    }

    var userData: MemberModel?

        get() {
            val userJsonData: String? =
                instance?.sharedPManger?.getDataString(Constants.KEY_MEMBER)
            return Gson().fromJson(userJsonData, MemberModel::class.java)
        }
        set(user) {
            val userData: String = Gson().toJson(user)
            instance?.sharedPManger?.setData(Constants.KEY_MEMBER, userData)
        }
    val userToken: String?
        get() {
            val memberModel: MemberModel? = userData
            if (memberModel != null) {
              //  return /*Constants.TOKEN_PREFIX + */memberModel.apiToken
            }
            return null
        }

    inline fun <reified T> convertJsonToObject(json: String, isArray: Boolean): T {
//        println("Log object T ${T::class.java} isArray $isArray")
        return if (isArray)
            Gson().fromJson(json, object : TypeToken<T>() {}.type)
        else
            Gson().fromJson(json, T::class.java)
//        return Gson().fromJson(json, genericType<T>())
//        return Gson().fromJson(json, objectClass)
    }

    fun initLanguage() {
        LocaleUtils.setLocale(Locale(language))
        LocaleUtils.updateConfig(instance, instance?.resources?.configuration)

//        Locale locale = new Locale(getLanguage());
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        RootApplication.Companion.getInstance().getResources().updateConfiguration(config, RootApplication.Companion.getInstance().getResources().getDisplayMetrics());
    }




}
