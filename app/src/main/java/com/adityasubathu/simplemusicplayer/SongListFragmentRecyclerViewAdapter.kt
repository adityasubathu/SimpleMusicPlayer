package com.adityasubathu.simplemusicplayer

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SongListFragmentRecyclerViewAdapter (c: Context, list : MutableList<SongInfo>, fragmentManager : FragmentManager):
        RecyclerView.Adapter<SongListRecyclerViewHolder>(), RecyclerViewClickListener{

    private val songInfoObjectsList = list
    private val contexts: Context = c
    private val manager = fragmentManager

    override fun recyclerViewListClicked(v: View, position: Int) {

        val snackbar = Snackbar.make(v, Lists.getSongsList(contexts)[position], Snackbar.LENGTH_SHORT)
        snackbar.show()

        val transaction = manager.beginTransaction()

        val nowPlayingFragment = NowPlayingFragment(Lists.getSongsList(contexts)[position], Lists.getArtistsList(contexts)[position], Lists.getAlbumsList(contexts)[position],
                Lists.getSongsDurationList(contexts)[position], MusicListsManager.getAlbumIDList(contexts)[position])

        transaction.replace(R.id.mainFragmentHolder, nowPlayingFragment, "NowPlayingFragment")
        transaction.addToBackStack("songListFragment")
        transaction.commit()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListRecyclerViewHolder {

        val inflater : LayoutInflater  = contexts.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val v : View = inflater.inflate(R.layout.songs_list_adapter_layout, parent, false)

        return SongListRecyclerViewHolder(v, this)
    }

    override fun onBindViewHolder(holder: SongListRecyclerViewHolder, position: Int) {

        val songInfoObject = songInfoObjectsList[position]

        holder.songNameTextView.text = songInfoObject.songTitle
        holder.albumNameTextView.text = songInfoObject.songAlbum
        holder.artistNameTextView.text = songInfoObject.songArtist


    }

    override fun getItemCount(): Int {
        return songInfoObjectsList.size
    }

}
