package com.app.musicplayer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MusicBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val action = intent?.action
            val musicIntent = Intent(it, MusicService::class.java).apply {
                this.action = action
            }
            it.startService(musicIntent)
        }
    }
}
