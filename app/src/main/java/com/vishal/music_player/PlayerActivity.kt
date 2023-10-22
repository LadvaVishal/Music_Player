package com.vishal.music_player

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class PlayerActivity : AppCompatActivity() {
    companion object{
        //pa= player activity
        lateinit var musicListPA:ArrayList<Music>
        var songPosition:Int=0
        var mediaPlayer: MediaPlayer?= null
        var isPlaying:Boolean=false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val backButton = findViewById<ImageButton>(R.id.back_btn_player)
        backButton.setOnClickListener {
            Intent(this@PlayerActivity, MainActivity::class.java).also { startActivity(it) }


        }
            songPosition=intent.getIntExtra("index",0)
            when(intent.getStringExtra("class")){
                "MusicAdapter"->{
                    musicListPA= ArrayList()
                    musicListPA.addAll(MainActivity.MusicListMA)
                    setLayout()
                    createMediaPlayer()
                    val playPause=findViewById<ExtendedFloatingActionButton>(R.id.playPauseBtnPA)
                    playPause.setOnClickListener {
                        if (isPlaying) pauseMusic()
                        else playMusic()
                    }

                }
            }

    }
    private fun setLayout(){
        val songNamePA=findViewById<TextView>(R.id.songNamePA)
        songNamePA.text= musicListPA[songPosition].title
    }
    private fun createMediaPlayer()
    {
        try {
            if(mediaPlayer== null) mediaPlayer= MediaPlayer()
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
            isPlaying=true

            val playPause = findViewById<ExtendedFloatingActionButton>(R.id.playPauseBtnPA)
            playPause.setIconResource(R.drawable.pause_icon)

        }
        catch (e:Exception) {
            return
        }
    }
    private fun playMusic(){
        val playPause = findViewById<ExtendedFloatingActionButton>(R.id.playPauseBtnPA)
        playPause.setIconResource(R.drawable.pause_icon)
        isPlaying=true
        mediaPlayer!!.start()
    }
    private fun pauseMusic(){

        val playPause = findViewById<ExtendedFloatingActionButton>(R.id.playPauseBtnPA)
        playPause.setIconResource(R.drawable.play_icon)
        isPlaying=false
        mediaPlayer!!.pause()
    }
}