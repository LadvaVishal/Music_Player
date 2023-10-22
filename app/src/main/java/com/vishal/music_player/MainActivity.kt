package com.vishal.music_player

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import java.io.File
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var toggle:ActionBarDrawerToggle
    private lateinit var musicAdapter: MusicAdapter
    companion object{
        //ma=music list
        var MusicListMA: ArrayList<Music> = ArrayList()

    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        requestRuntimePermission()
        setContentView(R.layout.activity_main)


        //for nav drawer
//        toggle=ActionBarDrawerToggle(this,binding.root,R.string.open,R.string.close)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//set music adpater by set recycler view
        MusicListMA=getAllAudio()

        val musicRv=findViewById<RecyclerView>(R.id.musicRV)
        var musicList=ArrayList<String>()

        musicRv.setHasFixedSize(true)
        musicRv.setItemViewCacheSize(13)
        musicRv.layoutManager=LinearLayoutManager(this@MainActivity)
        musicAdapter=MusicAdapter(this@MainActivity, MusicListMA)
        musicRv.adapter=musicAdapter

        val totalSong=findViewById<TextView>(R.id.totalSong)
        totalSong.text="Total Songs: "+musicAdapter.itemCount

//        musicRv.setOnClickListener()





        val shuffle = findViewById<Button>(R.id.shuffle_btn)
        shuffle.setOnClickListener {
            Toast.makeText(this@MainActivity,"Button Clicked",Toast.LENGTH_SHORT).show()
            Intent(this@MainActivity,PlayerActivity:: class.java).also { startActivity(it) }

        }


        val favourite: Button =findViewById(R.id.favourite_btn)
        favourite.setOnClickListener{

            Intent(this@MainActivity,FavouriteActivity:: class.java).also { startActivity(it) }
        }

        val playlist:Button=findViewById(R.id.playlist_btn)
        playlist.setOnClickListener{
            Intent(this@MainActivity,PlaylistActivity:: class.java).also { startActivity(it) }
        }

        // for navigation menu
        val navView = findViewById<NavigationView>(R.id.navView)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navFeedback -> {
                    Toast.makeText(baseContext, "Feedback", Toast.LENGTH_SHORT).show()
                }
                R.id.navSetting -> {
                    Toast.makeText(baseContext, "Settings", Toast.LENGTH_SHORT).show()
                }
                R.id.navAbout -> {
                    Toast.makeText(baseContext, "About", Toast.LENGTH_SHORT).show()
                }
                R.id.navExit -> {
                    exitProcess(1)
                }
            }
            true // Indicate that the item selection has been consumed
        }

    }

    //requesting permission
   private fun requestRuntimePermission(){
       if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
       {
           ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),13)

       }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==13){
            if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show()
            else
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),13)

        }
    }
//for navigation bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

   // access music file
   @SuppressLint("Range")
   private fun getAllAudio(): ArrayList<Music> {
       val tempList = ArrayList<Music>()
       val selection = MediaStore.Audio.Media.IS_MUSIC + "!=0"
       val projection = arrayOf(
           MediaStore.Audio.Media._ID,
           MediaStore.Audio.Media.TITLE,
           MediaStore.Audio.Media.ALBUM,
           MediaStore.Audio.Media.ARTIST,
           MediaStore.Audio.Media.DURATION,
           MediaStore.Audio.Media.DATE_ADDED,
           MediaStore.Audio.Media.DATA
       )

       val cursor = this.contentResolver.query(
           MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
           projection,
           selection,
           null,
           MediaStore.Audio.Media.DATE_ADDED + " DESC",
           null
       )

       if (cursor != null) {
           if (cursor.moveToFirst()) {
               do {
                   val titleC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                   val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                   val albumC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                   val artistC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                   val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                   val durationC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                   val music=Music(id =idC, title =titleC, album =albumC, artist =artistC, path =pathC, duration = durationC)
                   val file=File(music.path)
                   if (file.exists())
                       tempList.add(music)


               } while (cursor.moveToNext())
           }
           cursor.close()
       }
       return tempList
   }


}