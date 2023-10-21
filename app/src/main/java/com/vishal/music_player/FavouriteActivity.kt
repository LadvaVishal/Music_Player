package com.vishal.music_player

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class FavouriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        val backButton = findViewById<ImageButton>(R.id.back_btn)
        backButton.setOnClickListener{
            Intent(this@FavouriteActivity,MainActivity:: class.java).also { startActivity(it) }
        }

}
}