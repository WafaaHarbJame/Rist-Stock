package com.app.riststock.spichalViews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import com.app.riststock.classes.Constants

class SSwitch : SwitchCompat {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        val typeface =
            Typeface.createFromAsset(context.assets, Constants.NORMAL_FONT)
        setTypeface(typeface)
    }
}
