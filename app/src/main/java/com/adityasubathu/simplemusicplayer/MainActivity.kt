package com.adityasubathu.simplemusicplayer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

class MainActivity : AppCompatActivity() {

    lateinit var mainToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainToolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(mainToolbar)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), SongListFragment.READ_STORAGE_PERMISSION_REQUEST_FLAG)
        }

        val manager: FragmentManager = supportFragmentManager
        val transaction = manager.beginTransaction()

        val songListFragment = SongListFragment()

        transaction.add(R.id.mainFragmentHolder, songListFragment, "songListFragment")
        transaction.commit()
    }
}