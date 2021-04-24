package com.goutam.routefinder.ui.routedetails

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.goutam.routefinder.R

object MapUtils {
    fun getBusBitmap(context: Context): Bitmap? {
        val bitmap =  AppCompatResources.getDrawable(context, R.drawable.ic_bus)?.toBitmap()
        return Bitmap.createScaledBitmap(bitmap!!, 70, 70, false)
    }

    fun getTrailsBitmap(context: Context): Bitmap? {
        val bitmap =  AppCompatResources.getDrawable(context, R.drawable.drawable_trails)?.toBitmap()
        return Bitmap.createScaledBitmap(bitmap!!, 30, 30, false)
    }

    fun getSourceBitmap(context: Context): Bitmap? {
        val bitmap =  AppCompatResources.getDrawable(context, R.drawable.drawable_source)?.toBitmap()
        return Bitmap.createScaledBitmap(bitmap!!, 30, 30, false)
    }

    fun getDestBitmap(context: Context): Bitmap? {
        val bitmap =  AppCompatResources.getDrawable(context, R.drawable.drawable_dest)?.toBitmap()
        return Bitmap.createScaledBitmap(bitmap!!, 30, 30, false)
    }
}
