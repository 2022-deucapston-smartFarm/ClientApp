package com.android.smartfarm.data.firebase

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.smartfarm.ui.activity.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCM : FirebaseMessagingService() {
    /* 토큰 생성 메서드 */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    /* 메세지 수신 메서드 */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification!!.title.toString()
        val text = remoteMessage.notification!!.body.toString()
        val Channel_ID = "HEADS_UP_NOTIFICATION"
        var channel = NotificationChannel(Channel_ID,"notification", NotificationManager.IMPORTANCE_HIGH)
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        val intent = Intent(applicationContext,MainActivity::class.java).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            putExtra("header",title)
            putExtra("info",text)
        }
        val pendingIntent=PendingIntent.getActivity(applicationContext,1,intent,PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(this, Channel_ID)
            .setSmallIcon(R.mipmap.sym_def_app_icon) // 아이콘 설정
            .setContentTitle(title) // 제목
            .setContentIntent(pendingIntent)
            .setContentText(text) // 메시지 내용
            .setAutoCancel(true) // 알람클릭시 삭제여부
            .setSound(soundUri)  // 알림 소리

        NotificationManagerCompat.from(this).notify(1,notificationBuilder.build())

        super.onMessageReceived(remoteMessage)
    }
}

