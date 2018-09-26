package com.adityasubathu.simplemusicplayer

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.*

class SongListFragmentAdapter
(private val context: Context, private val songsList: List<String>, private val artistList: List<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return songsList.size
    }

    override fun getItem(position: Int): Any {
        return songsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    @Suppress("NAME_SHADOWING")
    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = Objects.requireNonNull(inflater).inflate(R.layout.songs_list_adapter_layout, parent, false)

        val index = convertView.findViewById<TextView>(R.id.index)
        val song = convertView.findViewById<TextView>(R.id.song)
        val artist = convertView.findViewById<TextView>(R.id.artist)

        index.text = Integer.toString(position + 1)
        song.text = songsList[position]
        artist.text = artistList[position]

        return convertView
    }
}