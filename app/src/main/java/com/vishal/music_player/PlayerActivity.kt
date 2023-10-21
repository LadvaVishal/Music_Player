package com.vishal.music_player

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class PlayerActivity : AppCompatActivity() {
    companion object{
        //pa= player activity
       lateinit var musicListPA:ArrayList<Music>
       var songPosition:Int=0
        var mediaPlayer: MediaPlayer?= null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val backButton = findViewById<ImageButton>(R.id.back_btn_player)
        backButton.setOnClickListener{
            Intent(this@PlayerActivity,MainActivity:: class.java).also { startActivity(it) }


            songPosition=intent.getIntExtra("index",0)
            when(intent.getStringExtra("class")){
                "MusicAdapter"->{
                    musicListPA= ArrayList()
                    musicListPA.addAll(MainActivity.MusicListMA)
                    if(mediaPlayer== null) mediaPlayer= MediaPlayer()
                    mediaPlayer!!.reset()
                    mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
                    mediaPlayer!!.prepare()
                    mediaPlayer!!.start()
                }
            }
        }
    }
}