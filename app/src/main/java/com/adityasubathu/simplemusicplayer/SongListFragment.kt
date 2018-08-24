package com.adityasubathu.simplemusicplayer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView

class SongListFragment : Fragment() {

    private lateinit var v: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        v = inflater.inflate(R.layout.song_list_fragment, container, false)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_REQUEST_FLAG)
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
        val listView: ListView = v.findViewById(R.id.songListRecyclerView)

        val songsList = MusicListsManager.getSongsList(activity!!)
        val artistList = MusicListsManager.getArtistsList(activity!!)
        val albumList = MusicListsManager.getAlbumsList(activity!!)
        val songDuration = MusicListsManager.getSongsDurationList(activity!!)
        //val albumArtPathList = MusicListsManager.getAlbumArtPathsList(activity!!)

        val adapter = SongListFragmentAdapter(context!!, songsList, artistList)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, view, position, _ ->
            val snackbar = Snackbar.make(view, songsList[position], Snackbar.LENGTH_SHORT)
            snackbar.show()

            val manager = activity!!.supportFragmentManager
            val transaction = manager.beginTransaction()

            val nowPlayingFragment = NowPlayingFragment(null, songsList[position], artistList[position],
                    albumList[position], songDuration[position])

            transaction.replace(R.id.mainFragmentHolder, nowPlayingFragment, "NowPlayingFragment")
            transaction.addToBackStack("songListFragment")
            transaction.commit()

        }
    }

    companion object {
        private const val READ_STORAGE_PERMISSION_REQUEST_FLAG = 1
    }
}

