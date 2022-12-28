package com.app.riststock.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.app.riststock.MainActivity
import com.app.riststock.R
import com.app.riststock.classes.GlobalData


open class ActivityBase : LocalizationActivity() {

    private var isMainActivity = false
    private var visible = View.VISIBLE
    private var gone = View.GONE
    protected var invisible = View.INVISIBLE

    var onStartCount = 0


    private var home: View? = null
    private var searchBtn: View? = null
    protected var orderMenuBtn: View? = null
    protected var editIbanBtn: View? = null
    protected var callBtn: View? = null
    private var aTitle: TextView? = null
    var toolbar: RelativeLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        sharedPManger = new SharedPManger(getActiviy());
//        String locationJson = sharedPManger.getDataString(Constants.KEY_MEMBER_LOCATION);
//        GlobalData.MEMEBR_LOCATION = new Gson().fromJson(locationJson, new TypeToken<MyLocationModel>() {
//        }.getType());

//        myLocationModel = UtilityApp.getUserLocation();
        onStartCount = 1
        if (savedInstanceState == null) // 1st time
        {
//            this.overridePendingTransition(R.anim.slide_in_bottom,
//                    R.anim.slide_out_top);
            overridePendingTransition(
                R.anim.fade_in,
                R.anim.fade_out
            )
        } else  // already created so reverse animation
        {
            onStartCount = 2
        }
    }


    override fun onStart() {
        super.onStart()
//        if (RootApplication.sessionDepth > 1)
//            RootApplication.sessionDepth = 0
        if (onStartCount > 1) {
//            this.overridePendingTransition(R.anim.slide_in_top,
//                    R.anim.slide_out_bottom);
            overridePendingTransition(
                R.anim.fade_in,
                R.anim.fade_out
            )
        } else if (onStartCount == 1) {
            onStartCount++
        }
    }


    override fun setTitle(title: CharSequence) {
//        toolbar = findViewById(R.id.tool_bar)
//        home = toolbar?.findViewById(R.id.home)
//        aTitle = toolbar?.findViewById(R.id.title)
//        orderMenuBtn = toolbar?.findViewById(R.id.orderMenuBtn)
//        editIbanBtn = toolbar?.findViewById(R.id.editIbanBtn)
//        callBtn = toolbar?.findViewById(R.id.callBtn)

//        boolean isLeftToRight = getResources().getBoolean(R.bool.is_left_to_right);
//        if (isLeftToRight) {
//            home.setScaleX(-1);
//            navic.setScaleX(1);
//        } else {
//            home.setScaleX(1);
//            navic.setScaleX(-1);
//        }

//        logoIcon.setVisibility(View.VISIBLE);
//        aTitle?.visibility = View.VISIBLE
//        aTitle?.text = title
//        if (!isMainActivity) {
//            home?.visibility = visible
//        } else {
//            aTitle?.visibility = gone
//            home?.visibility = gone
//        }

        home?.setOnClickListener { onBackPressed() }

        super.setTitle(title)
    }

    fun changeLanguage(lang: String?) {
        setLanguage(lang!!)
        GlobalData.Position = 0
        val intent = Intent(activiy, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    protected val activiy: Activity
        get() = this

    fun Toast(vararg msg: String?) {
        var msgs: String? = ""
        for (s in msg) {
            msgs += s
        }
        Toast.makeText(activiy, msgs, Toast.LENGTH_SHORT).show()
    }

    fun Toast(resId: Int) {
        Toast.makeText(activiy, getString(resId), Toast.LENGTH_SHORT).show()
    }

    fun setupUI(view: View) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event -> //                    System.out.println("Log event Action " + event.getAction());
                if (event.action != MotionEvent.ACTION_SCROLL) {
                    hideSoftKeyboard(activiy)
                }
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    fun hideSoftKeyboard(activity: Activity) {
        try {
            val inputMethodManager = activity.getSystemService(
                INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0
            )
        } catch (e: Exception) {
//            e.printStackTrace();
        }
    }

    open fun showSoftKeyboard(view: View,activity: Activity) {
        try {
//            val inputMethodManager = activity.getSystemService(
//                INPUT_METHOD_SERVICE
//            ) as InputMethodManager
////            inputMethodManager.hideSoftInputFromWindow(
////                activity.currentFocus!!.windowToken, 0
////
////            )
//
//            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);

            val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

            //            inputMethodManager.showSoftInput(_searchText, InputMethodManager.SHOW_FORCED);
        } catch (e: java.lang.Exception) {
//            e.printStackTrace();
        }
    }
}
