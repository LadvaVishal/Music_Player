package com.vishal.music_player

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class PlaylistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)
//        val backButton = findViewById<ImageButton>(R.id.back_btn_playlist)
        val backButton= findViewById<ImageButton>(R.id.back_btn_playlist)
        backButton.setOnClickListener{
            Intent(this@PlaylistActivity,MainActivity:: class.java).also { startActivity(it) }
        }
    }
}