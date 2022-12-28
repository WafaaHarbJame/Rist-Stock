package com.app.riststock.spichalViews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.app.riststock.classes.Constants

class STextViewMedium : AppCompatTextView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context, attrs
    ) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context, attrs, defStyle
    ) {
        init()
    }

    private fun init() {
        val typeface = Typeface.createFromAsset(context.assets, Constants.MEDIUM_FONT)
        setTypeface(typeface)
    }
}