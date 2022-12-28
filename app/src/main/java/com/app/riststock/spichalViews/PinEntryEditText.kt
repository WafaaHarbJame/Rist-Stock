package com.app.riststock.spichalViews

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.app.riststock.R
import com.app.riststock.RootApplication


class PinEntryEditText : androidx.appcompat.widget.AppCompatEditText {
    var mSpace = 24f //24 dp by default
    var mCharSize = 0f
    var mNumChars = 6f
    var mLineSpacing = 8f //8dp by default
    private var mLineStroke = 1f //1dp by default
    private var mLinesPaint: Paint? = null
    var mStates = arrayOf(
        intArrayOf(android.R.attr.state_selected),
        intArrayOf(android.R.attr.state_focused),
        intArrayOf(-android.R.attr.state_focused)
    )

    val mColors = intArrayOf(
        ContextCompat.getColor(RootApplication.instance!!, R.color.header2),
        ContextCompat.getColor(RootApplication.instance!!, R.color.colorPrimary),
        ContextCompat.getColor(RootApplication.instance!!, R.color.header2)
    )
    val mColorStates = ColorStateList(mStates, mColors)
    private fun getColorForState(states: IntArray): Int {
        return mColorStates.getColorForState(states, Color.GRAY)
    }

    private var mClickListener: OnClickListener? = null

    //     next = is the current char the next character to be input?
    private fun updateColorForLines(next: Boolean) {
        if (isFocused) {
            mLinesPaint?.color = getColorForState(intArrayOf(android.R.attr.state_selected))
            if (next) {
                mLinesPaint?.color = getColorForState(intArrayOf(android.R.attr.state_focused))
            }
        } else {
            mLinesPaint?.color = getColorForState(intArrayOf(-android.R.attr.state_focused))
        }
    }

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(
        context: Context, attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        setBackgroundResource(0)
        val multi = context.resources.displayMetrics.density
        mLineStroke *= multi
        mLinesPaint = Paint(paint)
        mLinesPaint!!.strokeWidth = mLineStroke
        mSpace *= multi //convert to pixels for our density
        mLineSpacing *= multi //convert to pixels
        //        mMaxLength = attrs.getAttributeIntValue(
//                XML_NAMESPACE_ANDROID, "maxLength", 4);
//        mNumChars = mMaxLength;

        //Disable copy paste
        super.setCustomSelectionActionModeCallback(
            object : ActionMode.Callback {
                override fun onPrepareActionMode(
                    mode: ActionMode,
                    menu: Menu
                ): Boolean {
                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode) {}
                override fun onCreateActionMode(
                    mode: ActionMode,
                    menu: Menu
                ): Boolean {
                    return false
                }

                override fun onActionItemClicked(
                    mode: ActionMode,
                    item: MenuItem
                ): Boolean {
                    return false
                }
            })
        //When tapped, move cursor to end of the text
        super.setOnClickListener { v ->
            setSelection(text?.length ?: 0)
            if (mClickListener != null) {
                mClickListener!!.onClick(v)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas);
        val availableWidth = width - paddingRight - paddingLeft
        mCharSize = if (mSpace < 0) {
            availableWidth / (mNumChars * 2 - 1)
        } else {
            (availableWidth - mSpace * (mNumChars - 1)) / mNumChars
        }
        var startX = paddingLeft
        val bottom = height - paddingBottom

        //Text Width
        val text = text
        val textLength = text?.length
        val textWidths = FloatArray(textLength ?: 0)
        paint.getTextWidths(getText(), 0, textLength ?: 0, textWidths)
        var i = 0
        while (i < mNumChars) {

            updateColorForLines(i == textLength);
            canvas.drawLine(
                startX.toFloat(), bottom.toFloat(), startX + mCharSize,
                bottom.toFloat(), mLinesPaint!!
            )
            if (getText()?.length ?: 0 > i) {
                val middle = startX + mCharSize / 2
                canvas.drawText(
                    text ?: "",
                    i,
                    i + 1,
                    middle - textWidths[0] / 2,
                    bottom - mLineSpacing,
                    paint
                )
            }
            startX += if (mSpace < 0) {
                (mCharSize * 2).toInt()
            } else {
                (mCharSize + mSpace).toInt()
            }
            i++
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        mClickListener = l
    }

//    fun setCustomSelectionActionModeCallback(actionModeCallback: ActionMode.Callback) {
//        throw RuntimeException("setCustomSelectionActionModeCallback() not supported.")
//    }
}