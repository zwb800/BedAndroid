package com.jnrecycle.app

import android.appwidget.AppWidgetManager
import android.content.*
import android.os.Build
import android.text.TextUtils
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.xiaomi.mipush.sdk.*


class PushReceiver : PushMessageReceiver() {

    private var mRegId: String? = null
    private var mMessage: String? = null
    private var mTopic: String? = null
    private var mAlias: String? = null
    private var mUserAccount: String? = null
    private var mStartTime: String? = null
    private var mEndTime: String? = null


    override fun onReceivePassThroughMessage(context: Context?, message: MiPushMessage) {
        mMessage = message.content
        if (!TextUtils.isEmpty(message.topic)) {
            mTopic = message.topic
        } else if (!TextUtils.isEmpty(message.alias)) {
            mAlias = message.alias
        } else if (!TextUtils.isEmpty(message.userAccount)) {
            mUserAccount = message.userAccount
        }
    }

    override fun onNotificationMessageClicked(context: Context?, message: MiPushMessage) {
        mMessage = message.content
        if (!TextUtils.isEmpty(message.topic)) {
            mTopic = message.topic
        } else if (!TextUtils.isEmpty(message.alias)) {
            mAlias = message.alias
        } else if (!TextUtils.isEmpty(message.userAccount)) {
            mUserAccount = message.userAccount
        }



    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onNotificationMessageArrived(context: Context?, message: MiPushMessage) {
        mMessage = message.content
        if (!TextUtils.isEmpty(message.topic)) {
            mTopic = message.topic
        } else if (!TextUtils.isEmpty(message.alias)) {
            mAlias = message.alias
        } else if (!TextUtils.isEmpty(message.userAccount)) {
            mUserAccount = message.userAccount
        }
        PushActivity.savePush(context,message)

        val bedno =  message.notifyId
        var txt_time_id = R.id.txt_bed1_time
        var txt_duration_id = R.id.txt_bed1_duration
        var layout_id = R.id.bed1
        var txt_bed_title = R.id.txt_bed1_title

        if(bedno == 1)
        {
            txt_time_id = R.id.txt_bed1_time
            txt_duration_id =  R.id.txt_bed1_duration
            layout_id = R.id.bed1
            txt_bed_title = R.id.txt_bed1_title
        }
        else if(bedno == 2)
        {
            txt_time_id = R.id.txt_bed2_time
            txt_duration_id =  R.id.txt_bed2_duration
            layout_id = R.id.bed2
            txt_bed_title = R.id.txt_bed2_title
        }
        else if(bedno == 3)
        {
            txt_time_id = R.id.txt_bed3_time
            txt_duration_id =  R.id.txt_bed3_duration
            layout_id = R.id.bed3
            txt_bed_title = R.id.txt_bed3_title
        }
        else if(bedno == 4)
        {
            txt_time_id = R.id.txt_bed4_time
            txt_duration_id =  R.id.txt_bed4_duration
            layout_id = R.id.bed4
            txt_bed_title = R.id.txt_bed4_title
        }
        else if(bedno == 5)
        {
            txt_time_id = R.id.txt_bed5_time
            txt_duration_id =  R.id.txt_bed5_duration
            layout_id = R.id.bed5
            txt_bed_title = R.id.txt_bed5_title
        }
        else if(bedno == 6)
        {
            txt_time_id = R.id.txt_bed6_time
            txt_duration_id =  R.id.txt_bed6_duration
            layout_id = R.id.bed6
            txt_bed_title = R.id.txt_bed6_title
        }

        val time = message.description
        val liedown = message.title.contains("躺下")

        val appWidgetManager = AppWidgetManager.getInstance(context)
        // Construct the RemoteViews object
        val views = RemoteViews(context!!.packageName, R.layout.bed_widget)
        views.setTextViewText(if (liedown) txt_time_id else txt_duration_id,
            time.substring(0,time.lastIndexOf(':')))
        views.setInt(layout_id,"setBackgroundResource",
            if(liedown) R.drawable.shape_black else R.drawable.shape_white)

        val txtColor = context.getColor(if(liedown) R.color.white else R.color.black)
        views.setTextColor(txt_time_id, txtColor)
        views.setTextColor(txt_duration_id,txtColor)
        views.setTextColor(txt_bed_title,txtColor)

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(
            ComponentName(context,BedWidget::class.java), views)
    }

    override fun onCommandResult(context: Context?, message: MiPushCommandMessage) {
        val command = message.command
        val arguments = message.commandArguments
        val cmdArg1 = if (arguments != null && arguments.size > 0) arguments[0] else null
        val cmdArg2 = if (arguments != null && arguments.size > 1) arguments[1] else null
        if (MiPushClient.COMMAND_REGISTER == command) {
            if (message.resultCode == ErrorCode.SUCCESS.toLong()) {
                mRegId = cmdArg1
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS == command) {
            if (message.resultCode == ErrorCode.SUCCESS.toLong()) {
                mAlias = cmdArg1
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS == command) {
            if (message.resultCode == ErrorCode.SUCCESS.toLong()) {
                mAlias = cmdArg1
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC == command) {
            if (message.resultCode == ErrorCode.SUCCESS.toLong()) {
                mTopic = cmdArg1
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC == command) {
            if (message.resultCode == ErrorCode.SUCCESS.toLong()) {
                mTopic = cmdArg1
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME == command) {
            if (message.resultCode == ErrorCode.SUCCESS.toLong()) {
                mStartTime = cmdArg1
                mEndTime = cmdArg2
            }
        }
    }

    override fun onReceiveRegisterResult(context: Context?, message: MiPushCommandMessage) {
        val command = message.command
        val arguments = message.commandArguments
        val cmdArg1 = if (arguments != null && arguments.size > 0) arguments[0] else null
        val cmdArg2 = if (arguments != null && arguments.size > 1) arguments[1] else null
        if (MiPushClient.COMMAND_REGISTER == command) {
            if (message.resultCode == ErrorCode.SUCCESS.toLong()) {
                mRegId = cmdArg1
            }
        }
    }
}