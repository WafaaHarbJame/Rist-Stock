package com.app.riststock

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import com.androidnetworking.AndroidNetworking
import com.app.riststock.classes.Constants
import com.app.riststock.utils.SharedPManger
import com.app.riststock.utils.UtilityApp
import java.util.*


class RootApplication : LocalizationApplication() {
    @get:Synchronized
    var sharedPManger: SharedPManger? = null


    companion object {
        @get:Synchronized
        var instance: RootApplication? = null

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        }


    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun getDefaultLanguage(base: Context): Locale {
        return Locale(Constants.English)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        sharedPManger = SharedPManger(this)
        AndroidNetworking.initialize(this);


        var appLanguage = UtilityApp.language
        if (appLanguage == null) {
            appLanguage = Constants.English;
            UtilityApp.language = appLanguage
        } else {
            UtilityApp.language = appLanguage
        }


    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
//        LocaleChanger.onConfigurationChanged();
    }


}