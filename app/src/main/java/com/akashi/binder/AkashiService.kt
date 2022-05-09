package com.akashi.binder

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class AkashiService : Service() {

    private val mBinder: IAkashiServiceManager.Stub by lazy {
        object : IAkashiServiceManager.Stub() {
            override fun request(data: String?): String {
                Log.i("server", "request: $data")
                return "$data ----- received"
            }
        }
    }

    override fun onBind(intent: Intent): IBinder = mBinder
}