package com.adityasubathu.simplemusicplayer

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

class MainActivity : AppCompatActivity() {

    lateinit var mainToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainToolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(mainToolbar)

        val manager: FragmentManager = supportFragmentManager
        val transaction = manager.beginTransaction()

        val songListFragment = SongListFragment()

        transaction.add(R.id.mainFragmentHolder, songListFragment, "songListFragment")
        transaction.commit()
    }
}