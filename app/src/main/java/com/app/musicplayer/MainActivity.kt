package com.app.musicplayer

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private lateinit var musicReceiver: MusicBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialiser le BroadcastReceiver
        musicReceiver = MusicBroadcastReceiver()
        val filter = IntentFilter().apply {
            addAction("PLAY")
            addAction("PAUSE")
            addAction("STOP")
        }
        registerReceiver(musicReceiver, filter)

        // Boutons pour contrôler la musique
        findViewById<Button>(R.id.btnPlay).setOnClickListener {
            createNotificationChannel(MainActivity@this)
            showNotification(MainActivity@this,"My notif","PLAY")
            sendBroadcast(Intent("PLAY"))
        }

        findViewById<Button>(R.id.btnPause).setOnClickListener {
            createNotificationChannel(MainActivity@this)
            showNotification(MainActivity@this,"My notif","PAUSE")

            sendBroadcast(Intent("PAUSE"))
        }

        findViewById<Button>(R.id.btnStop).setOnClickListener {
            sendBroadcast(Intent("STOP"))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(musicReceiver) // Désenregistrer le BroadcastReceiver
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "your_channel_id"
            val channelName = "Your Channel Name"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Description of your channel"
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(context: Context, title : String, content : String) {
        val channelId = "your_channel_id"
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)  // Priorité de la notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true).build()

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                  applicationContext   ,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(1, notification)
        }
    }



}
