package com.goutam.routefinder.customview

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.goutam.routefinder.R

class ThemeTextView@JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
): AppCompatTextView(context, attributeSet, defStyleAttr) {
    init {
        context.theme.obtainStyledAttributes(attributeSet, R.styleable.ThemeTextView, 0, 0).apply {
            val font = getString(R.styleable.ThemeTextView_tv_fontName)
            try {
                val fontName = font ?: "Montserrat-Regular.ttf"
                val myTypeface = Typeface.createFromAsset(context.assets, "fonts/$fontName")
                typeface = myTypeface
            } catch (e: Exception) {
                e.printStackTrace()
            }
            recycle()
        }
    }
}
