package com.moneyminions.data.service

import android.Manifest
import android.app.PendingIntent
import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.moneyminions.data.R
import com.moneyminions.data.utils.Constants

private const val TAG = "FCMService D210"
class FCMService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            sendNotification(remoteMessage)
        }

        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }

    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        var messageTitle = ""
        var messageBody = ""
//        var articleId = ""
//        var type = ""

        // background 에 있을경우 혹은 foreground에 있을경우 두 경우 모두
        val notification = remoteMessage.notification
        val data = remoteMessage.data
        messageTitle = data["title"].toString()
        messageBody = data["message"].toString()
//        type = data[Constants.TYPE] ?: TYPE_REPORT
//        articleId = if (type == TYPE_REPORT) data[Constants.REPORT_ID].toString() else data[Constants.ARTICLE_ID].toString()
        val mainIntent = Intent(this, Class.forName("com.moneyminions.presentation.MainActivity")).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            putExtra(Constants.TYPE, type)
//            putExtra(Constants.ARTICLE_ID, articleId)
        }

        val mainPendingIntent: PendingIntent =
            PendingIntent.getActivity(
                this,
                101,
                mainIntent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        // 확인 버튼을 눌렀을 때의 이벤트 처리
        val confirmIntent = Intent(this, Class.forName("com.moneyminions.data.service.ConfirmReceiver")).apply {
            putExtra("ACTION_TYPE", "CONFIRM")
        }
        val confirmPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(
                this,
                0,
                confirmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )


        // 취소 버튼을 눌렀을 때 아무 일도 일어나지 않게 처리
        val cancelPendingIntent: PendingIntent? = null


        // 알림에 대한 UI 정보, 작업
        val notificationBuilder = NotificationCompat.Builder(this, Constants.CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
            .setSmallIcon(androidx.core.R.drawable.notification_bg)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setContentIntent(mainPendingIntent)
            .setGroupSummary(true)
            .setAutoCancel(true)
            .setFullScreenIntent(mainPendingIntent, true)
            .addAction(
                com.google.android.material.R.drawable.ic_search_black_24,
                "확인", // 버튼 텍스트
                confirmPendingIntent // 확인 버튼 클릭 시 PendingIntent
            )
            .addAction(
                com.google.android.material.R.drawable.ic_arrow_back_black_24, // 취소 버튼 아이콘
                "취소", // 버튼 텍스트
                cancelPendingIntent // 취소 버튼 클릭 시 PendingIntent
            )

        NotificationManagerCompat.from(this).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
//                    Log.d(TAG, "sendNotification permission not allowed")
                    return
                }
            }
            Log.d(TAG, "notify...")
            notify(101, notificationBuilder.build())
        }
    }


}

class ConfirmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive... ${intent?.extras?.getString("ACTION_TYPE")}")
        if (intent?.extras?.getString("ACTION_TYPE") == "CONFIRM") {
            // "확인" 버튼을 눌렀을 때 원하는 동작을 수행합니다.
            Log.d(TAG, "확인 버튼을 눌렀습니다")
            // 여기에 원하는 동작을 추가하세요.
        }
    }
}