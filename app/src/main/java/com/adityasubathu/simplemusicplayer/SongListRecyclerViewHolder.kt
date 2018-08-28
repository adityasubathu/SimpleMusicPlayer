package com.adityasubathu.simplemusicplayer

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class SongListRecyclerViewHolder(itemView: View, recyclerViewClickListener : RecyclerViewClickListener) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

    private val listener = recyclerViewClickListener

    override fun onClick(v: View?) {

        val a = this.oldPosition
        val b = this.adapterPosition
        val c = this.layoutPosition
        val d = this.position
        listener.recyclerViewListClicked(v!!, this.adapterPosition)
    }


    var songInfoLinearLayout : LinearLayout = itemView.findViewById(R.id.songInfoLinearLayout)
    var songNameTextView : TextView = itemView.findViewById(R.id.song)
    var artistNameTextView : TextView = itemView.findViewById(R.id.artist)
    var albumNameTextView : TextView = itemView.findViewById(R.id.album)

    init {

        itemView.setOnClickListener(this)

    }

}
