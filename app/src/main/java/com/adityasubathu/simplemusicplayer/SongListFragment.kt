package com.adityasubathu.simplemusicplayer

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import java.util.*
import kotlin.collections.ArrayList

class SongListFragment : Fragment() {

    internal lateinit var listView: ListView
    private lateinit var adapter: SongListFragmentAdapter

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

    private fun getMusicInfo() {
        val resolver = activity!!.contentResolver
        val songsUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val songsCursor = resolver.query(songsUri, null, null, null, MediaStore.Audio.Media.TITLE)

        if (songsCursor != null && songsCursor.moveToFirst()) {
            val songTitleIndex = songsCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val songArtistIndex = songsCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val songAlbumIndex = songsCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
            val songDurationIndex = songsCursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            var i = 0
            lateinit var albumArtCursor : Cursor
            do {
                val title = songsCursor.getString(songTitleIndex)
                val artist = songsCursor.getString(songArtistIndex)
                val album = songsCursor.getString(songAlbumIndex)
                val duration = songsCursor.getInt(songDurationIndex)
                val albumId = songsCursor.getInt(songsCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
                albumArtCursor = activity!!.contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        arrayOf(MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART),MediaStore.Audio.Albums._ID + "=?",
                        arrayOf(albumId.toString()), null)
                if (albumArtCursor.moveToFirst()){
                    var albumArtPath = albumArtCursor.getString(albumArtCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART))
                    if(albumArtPath == null){
                        albumArtPath = ""
                    }
                    albumArtPathList.add(albumArtPath)
                }
                ++i
                Log.e("valueofi", i.toString())
                songsList.add(title)
                artistList.add(artist)
                albumList.add(album)
                songDuration.add(duration)

                albumArtCursor.close()
            } while (songsCursor.moveToNext())
        }
        Objects.requireNonNull(songsCursor).close()
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
        listView = v.findViewById(R.id.listView)
        getMusicInfo()
        adapter = SongListFragmentAdapter(context!!, songsList, artistList)

        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, view, position, _ ->
            val snackbar = Snackbar.make(view, songsList[position], Snackbar.LENGTH_SHORT)
            snackbar.show()

            val manager = activity!!.supportFragmentManager
            val transaction = manager.beginTransaction()

            val nowPlayingFragment = NowPlayingFragment(albumArtPathList[position], songsList[position], artistList[position],
                    albumList[position], songDuration[position])

            transaction.replace(R.id.mainFragmentHolder, nowPlayingFragment, "NowPlayingFragment")
            transaction.addToBackStack("songListFragment")
            transaction.commit()

        }
    }

    companion object {

        private const val READ_STORAGE_PERMISSION_REQUEST_FLAG = 1
        private var songsList: MutableList<String> = ArrayList()
        private var artistList: MutableList<String> = ArrayList()
        private var albumList: MutableList<String> = ArrayList()
        private var songDuration: MutableList<Int> = ArrayList()
        private var albumArtPathList: MutableList<String> = ArrayList()
    }
}

