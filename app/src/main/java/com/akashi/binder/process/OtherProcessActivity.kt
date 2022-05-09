package com.akashi.binder.process

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.akashi.binder.AkashiService
import com.akashi.binder.IAkashiServiceManager
import com.akashi.binder.R

class OtherProcessActivity : AppCompatActivity() {

    private var mProxy: IAkashiServiceManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_process)

        initService()

        findViewById<AppCompatButton>(R.id.btn_connect).setOnClickListener {
            mProxy?.request("connect!").let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val mServiceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceConnected(p0: ComponentName?, binder: IBinder?) {
                Log.i("client", "onServiceConnected: ${System.currentTimeMillis()}")
                // 获取代理对象
                mProxy = IAkashiServiceManager.Stub.asInterface(binder)
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                Log.i("client", "onServiceDisconnected: ${System.currentTimeMillis()}")
            }

        }
    }

    private fun initService() {
        Intent(this, AkashiService::class.java).run {
            bindService(this, mServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }
}