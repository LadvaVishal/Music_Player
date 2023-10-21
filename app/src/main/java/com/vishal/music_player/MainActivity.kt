package com.vishal.music_player

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var toggle:ActionBarDrawerToggle
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

}