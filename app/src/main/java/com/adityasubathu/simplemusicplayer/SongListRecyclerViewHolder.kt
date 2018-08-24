package com.adityasubathu.simplemusicplayer

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class SongListRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    internal var songNameTextView: TextView? = null
    internal var artistNameTextView: TextView? = null
    internal var albumNameTextView: TextView? = null

    init {

        songNameTextView = itemView.findViewById(R.id.song)
        artistNameTextView = itemView.findViewById(R.id.artist)
        albumNameTextView = itemView.findViewById(R.id.album)
    }
}
