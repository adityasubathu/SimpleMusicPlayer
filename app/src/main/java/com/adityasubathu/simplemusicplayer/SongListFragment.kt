package com.adityasubathu.simplemusicplayer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import needle.Needle

class SongListFragment : Fragment(){


    private lateinit var v: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        v = inflater.inflate(R.layout.song_list_fragment, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_STORAGE_PERMISSION_REQUEST_FLAG)
        } else {
            prepareAdapter()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {

            READ_STORAGE_PERMISSION_REQUEST_FLAG -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    prepareAdapter()
                }
            }
        }
    }

    private fun prepareAdapter() {

        val songInfoObjectsList : MutableList<SongInfo> = ArrayList()

        val recyclerView : RecyclerView = v.findViewById(R.id.songListRecyclerView)

        val songsList : MutableList<String> = Lists.getSongsList(activity!!) as MutableList<String>
        val artistList : MutableList<String> = Lists.getArtistsList(activity!!) as MutableList<String>
        val albumList : MutableList<String> = Lists.getAlbumsList(activity!!) as MutableList<String>
        val songDuration : MutableList<String> = Lists.getSongsDurationList(activity!!) as MutableList<String>
        /*Needle.onBackgroundThread().execute{
            Lists.getAlbumArtPathsList(activity!!)
        }*/


        var i = 0

        while (i < songsList.size) {

            val songInfo = SongInfo()

            songInfo.songTitle = songsList[i]
            songInfo.songAlbum = albumList[i]
            songInfo.songArtist = artistList[i]
            songInfo.songDuration = songDuration[i]

            songInfoObjectsList.add(songInfo)

            ++i
        }

        val llm = LinearLayoutManager(activity!!)

        recyclerView.layoutManager = llm

        val adapter = SongListFragmentRecyclerViewAdapter(activity!!, songInfoObjectsList, activity!!.supportFragmentManager)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    companion object {
        const val READ_STORAGE_PERMISSION_REQUEST_FLAG = 1
    }
}

