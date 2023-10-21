package com.vishal.music_player

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val backButton = findViewById<ImageButton>(R.id.back_btn_player)
        backButton.setOnClickListener{
            Intent(this@PlayerActivity,MainActivity:: class.java).also { startActivity(it) }
        }
    }
}