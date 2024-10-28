package com.app.musicplayer

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

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
            sendBroadcast(Intent("PLAY"))
        }

        findViewById<Button>(R.id.btnPause).setOnClickListener {
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
}
