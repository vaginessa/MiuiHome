package com.yuk.miuihome.module

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.github.kyuubiran.ezxhelper.utils.*
import com.yuk.miuihome.XposedInit
import com.yuk.miuihome.utils.OwnSP

class EnableBlurWhenOpenFolder {

    fun init() {
        val blurClass = loadClass("com.miui.home.launcher.common.BlurUtils")
        val folderInfoClass = loadClass("com.miui.home.launcher.FolderInfo")
        val launcherClass = loadClass("com.miui.home.launcher.Launcher")
            if (OwnSP.ownSP.getBoolean("simpleAnimation", false)) {
                if (XposedInit().checkIsAlpha()) {
                    findMethod(blurClass) {
                        name == "isUserBlurWhenOpenFolder"
                    }.hookReturnConstant(false)
                }
            }
            else {
                if (OwnSP.ownSP.getBoolean("blurWhenOpenFolder", false)) {
                    if (XposedInit().checkIsAlpha()) {
                        findMethod(blurClass) {
                            name == "isUserBlurWhenOpenFolder"
                        }.hookReturnConstant(true)
                    }
                    else {
                        findMethod("com.miui.home.launcher.Launcher") {
                            name == "onCreate" && parameterTypes[0] == Bundle::class.java //TODO
                        }.hookAfter {
                            val activity = it.thisObject as Activity
                            findMethod(launcherClass) {
                                name == "openFolder" && parameterTypes[0] == folderInfoClass && parameterTypes[1] == View::class.java && parameterCount == 2
                            }.hookAfter {
                                blurClass.invokeStaticMethodAuto("fastBlur", 1.0f, activity.window, true)
                            }
                            findMethod(launcherClass) {
                                name == "closeFolder" && parameterTypes[0] == Boolean::class.java
                            }.hookAfter {
                                blurClass.invokeStaticMethodAuto("fastBlur", 0.0f, activity.window, true)
                            }
                        }
                    }
                } else {
                    if (XposedInit().checkIsAlpha()) {
                        findMethod(blurClass) {
                            name == "isUserBlurWhenOpenFolder"
                        }.hookReturnConstant(false)
                    }
                }
            }
    }
}