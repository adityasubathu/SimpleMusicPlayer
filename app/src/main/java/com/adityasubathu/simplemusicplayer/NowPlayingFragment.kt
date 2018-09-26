@file:Suppress("DEPRECATION")

package com.adityasubathu.simplemusicplayer

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import java.util.*

@SuppressLint("ValidFragment")

class NowPlayingFragment(private val songTitle: String, private val artistName: String,
                         private val albumName: String, private val songDuration: String, private val albumID: Int) : Fragment() {

    private lateinit var v: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        v = inflater.inflate(R.layout.now_playing_fragment_layout, container, false)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val albumArtImageView = v.findViewById<ImageView>(R.id.albumArtImageView)
        val songTitleTextView = v.findViewById<TextView>(R.id.songTitleTextView)
        val albumArtistTitleTextView = v.findViewById<TextView>(R.id.albumArtistTitleTextView)
        val songTimeSeekBar = v.findViewById<SeekBar>(R.id.songTimeSeekBar)

        val albumArtPath = MusicListsManager.getArtFromAlbumID(activity!!, albumID)

        Log.d("AlbumArtPath", albumArtPath)

        val uri = Uri.parse(albumArtPath)
        val b = BitmapFactory.decodeStream(activity!!.contentResolver.openInputStream(uri))
        albumArtImageView.setImageBitmap(b)

        songTitleTextView.text = songTitle
        albumArtistTitleTextView.text = String.format(Locale.getDefault(), "%s - %s", artistName, albumName)
        songTimeSeekBar.max = songDuration.toInt()

    }

    fun TextViewToastGenerator(v: TextView) {

        val id = v.text.toString()

        Toast.makeText(activity!!, id, Toast.LENGTH_SHORT).show()

    }
}