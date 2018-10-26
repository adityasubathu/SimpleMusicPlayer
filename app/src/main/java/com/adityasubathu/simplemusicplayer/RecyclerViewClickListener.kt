package com.adityasubathu.simplemusicplayer

import android.view.View

interface RecyclerViewClickListener {

    fun recyclerViewCustomOnClick(v: View, position: Int)
}