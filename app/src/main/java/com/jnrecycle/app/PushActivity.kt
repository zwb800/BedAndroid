package com.jnrecycle.app

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.xiaomi.mipush.sdk.MiPushMessage
import java.util.*

class PushActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        if(intent.data !=null)
        {
            val message = intent.getSerializableExtra("key_message") as MiPushMessage
            savePush(this,message)
            val url = intent.dataString!!.split(';')[0]
            val intentNew =  Intent(Intent.ACTION_VIEW)
            intentNew.setData(Uri.parse(url))
            startActivity(intentNew)//应限定微信打开
            finish()
        }

    }



    companion object{
        val TAG = "PushActivity"
        fun savePush(context: Context?,message: MiPushMessage) {
            if(context!=null)
            {
                val url = message.content
                val pref = context.getSharedPreferences("pushlog", MODE_PRIVATE)
                pref.edit()
                    .putString(
                        url,
                        message.title+";"+message.description
                                + ";" + (Date().time / 1000)
                    )
                    .apply()
            }
            else
                Log.w(TAG,"context is null")
        }


    }
}