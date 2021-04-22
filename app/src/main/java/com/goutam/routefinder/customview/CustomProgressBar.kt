package com.goutam.routefinder.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.content.res.ResourcesCompat
import com.goutam.routefinder.customview.model.ProgressItem

class CustomProgressBar@JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = android.R.attr.seekBarStyle
): AppCompatSeekBar(context, attributeSet, defStyleAttr) {
    private var mProgressItemsList: List<ProgressItem>
    init {
        mProgressItemsList = mutableListOf()
    }
    fun initData(progressItemsList: List<ProgressItem>){
        this.mProgressItemsList = progressItemsList
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        if (mProgressItemsList.isNotEmpty()) {
            val progressBarWidth = width
            val progressBarHeight = height
            val thumboffset = thumbOffset
            var lastProgressX = 0
            var progressItemWidth: Int
            var progressItemRight: Int
            for (i in mProgressItemsList.indices) {
                val progressItem = mProgressItemsList[i]
                val progressPaint = Paint()
                progressPaint.color = ResourcesCompat.getColor(resources, progressItem.color, null)
                progressItemWidth = (progressItem.percentage * progressBarWidth / 100).toInt()
                progressItemRight = lastProgressX + progressItemWidth
                if (i == mProgressItemsList.size - 1 && progressItemRight != progressBarWidth) {
                    progressItemRight = progressBarWidth
                }
                val progressRect = Rect()
                progressRect.set(lastProgressX, thumboffset / 2,
                        progressItemRight, progressBarHeight - thumboffset / 2)
                canvas.drawRect(progressRect, progressPaint)
                lastProgressX = progressItemRight
            }
            super.onDraw(canvas)
        }
    }
}
