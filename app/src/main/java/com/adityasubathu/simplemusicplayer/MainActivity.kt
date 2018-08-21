package com.adityasubathu.simplemusicplayer

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val manager: FragmentManager = supportFragmentManager
        val transaction = manager.beginTransaction()

        val songListFragment = SongListFragment()

        transaction.add(R.id.mainFragmentHolder, songListFragment, "songListFragment")
        transaction.commit()
    }
}