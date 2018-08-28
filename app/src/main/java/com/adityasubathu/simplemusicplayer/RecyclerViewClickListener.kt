package com.adityasubathu.simplemusicplayer

import android.view.View

interface RecyclerViewClickListener {

    fun recyclerViewListClicked(v: View, position: Int)
}