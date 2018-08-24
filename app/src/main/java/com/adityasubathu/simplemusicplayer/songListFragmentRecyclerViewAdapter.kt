package com.adityasubathu.simplemusicplayer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class songListFragmentRecyclerViewAdapter : RecyclerView.Adapter<SongListRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListRecyclerViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.songs_list_adapter_layout, parent, false)

        return SongListRecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: SongListRecyclerViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 0
    }
}
