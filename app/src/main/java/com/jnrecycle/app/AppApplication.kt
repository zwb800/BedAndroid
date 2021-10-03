package com.jnrecycle.app

import android.app.ActivityManager
import android.app.Application
import android.util.Log
import com.xiaomi.channel.commonutils.logger.LoggerInterface
import com.xiaomi.mipush.sdk.Logger
import com.xiaomi.mipush.sdk.MiPushClient

class AppApplication: Application() {

    private val APP_ID = "2882303761519932947"

    // user your appid the key.
    private val APP_KEY = "5771993240947"

    // 此TAG在adb logcat中检索自己所需要的信息， 只需在命令行终端输入 adb logcat | grep
    // com.xiaomi.mipushdemo
    val TAG = "com.jnrecycle.app"


    override fun onCreate() {
        super.onCreate()
        //初始化push推送服务
        if (shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY)
        }
        //打开Log
        val newLogger: LoggerInterface = object : LoggerInterface {
            override fun setTag(tag: String) {
                // ignore
            }

            override fun log(content: String, t: Throwable) {
                Log.d(TAG, content, t)
            }

            override fun log(content: String) {
                Log.d(TAG, content)
            }
        }
        Logger.setLogger(this, newLogger)
    }

    private fun shouldInit(): Boolean {
        val am = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val processInfos = am.runningAppProcesses
        val mainProcessName = applicationInfo.processName
        val myPid: Int = android.os.Process.myPid()
        for (info in processInfos) {
            if (info.pid == myPid && mainProcessName == info.processName) {
                return true
            }
        }
        return false
    }
}