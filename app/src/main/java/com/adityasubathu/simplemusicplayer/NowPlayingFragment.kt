@file:Suppress("DEPRECATION")

package com.adityasubathu.simplemusicplayer

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import java.util.*
import java.util.concurrent.TimeUnit

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
        val durationTextView = v.findViewById<TextView>(R.id.durationTextView)

        val albumArtPath = MusicListsManager.getArtFromAlbumID(activity!!, albumID)

        Log.d("AlbumArtPath", albumArtPath)

        val uri = Uri.parse("file://$albumArtPath")
        val resolver = context!!.contentResolver
        val b = BitmapFactory.decodeStream(resolver.openInputStream(uri))
        albumArtImageView.setImageBitmap(b)

        songTitleTextView.text = songTitle
        albumArtistTitleTextView.text = String.format(Locale.getDefault(), "%s - %s", artistName, albumName)
        val dur = songDuration.toInt()
        songTimeSeekBar.max = dur
        durationTextView.text = String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(songDuration.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(songDuration.toLong()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(songDuration.toLong())))

    }
}