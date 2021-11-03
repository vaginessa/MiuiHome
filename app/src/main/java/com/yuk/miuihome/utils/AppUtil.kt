package com.yuk.miuihome.utils

import android.content.res.Configuration
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext

fun dp2px(dpValue: Float): Int =
    (dpValue * appContext.resources.displayMetrics.density + 0.5f).toInt()

fun dip2px(dpValue: Int): Int =
    (dpValue * appContext.resources.displayMetrics.density + 0.5f).toInt()

fun sp2px(spValue: Float): Float =
    (spValue * appContext.resources.displayMetrics.scaledDensity + 0.5f)

fun px2dip(pxValue: Int): Int =
    (pxValue / appContext.resources.displayMetrics.density + 0.5f).toInt()

fun isNightMode(): Boolean =
    (appContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES