package com.adityasubathu.simplemusicplayer

import android.content.Context
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat

@Suppress("UNCHECKED_CAST")

object Lists {

    private lateinit var songsList: ArrayList<String>
    private lateinit var albumsList: ArrayList<String>
    private lateinit var artistsList: ArrayList<String>
    private lateinit var songsDurationList: ArrayList<String>
    private lateinit var albumArtPathsList: List<String>

    fun getSongsList(context: Context): List<String> {

        val songsListPrefs = context.getSharedPreferences("songsList", Context.MODE_PRIVATE)
        val serializedSongsList = songsListPrefs.getString("songs", null)

        if (serializedSongsList != null) {
            songsList = SerializeLists.deserialize(serializedSongsList) as ArrayList<String>
            return songsList
        } else {
            try {
                val serialized : String = SerializeLists.serialize(MusicListsManager.getSongsList(context) as ArrayList)
                val e = songsListPrefs.edit()
                e.putString("songs", serialized)
                e.apply()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return MusicListsManager.getSongsList(context)
    }

    fun getAlbumsList(context: Context): List<String> {

        val albumsListPrefs = context.getSharedPreferences("albumsList", Context.MODE_PRIVATE)
        val serializedSongsList = albumsListPrefs.getString("albums", null)

        if (serializedSongsList != null) {
            albumsList = SerializeLists.deserialize(serializedSongsList) as ArrayList<String>
            return albumsList
        } else {
            try {
                val serialized : String = SerializeLists.serialize(MusicListsManager.getAlbumsList(context) as ArrayList)
                val e = albumsListPrefs.edit()
                e.putString("albums", serialized)
                e.apply()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return MusicListsManager.getAlbumsList(context)
    }

    fun getArtistsList(context: Context): List<String> {

        val artistsListPrefs = context.getSharedPreferences("artistsList", Context.MODE_PRIVATE)
        val serializedSongsList = artistsListPrefs.getString("artists", null)

        if (serializedSongsList != null) {
            artistsList = SerializeLists.deserialize(serializedSongsList) as ArrayList<String>
            return artistsList
        } else {
            try {
                val serialized : String = SerializeLists.serialize(MusicListsManager.getArtistsList(context) as ArrayList)
                val e = artistsListPrefs.edit()
                e.putString("artists", serialized)
                e.apply()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return MusicListsManager.getArtistsList(context)
    }

    fun getSongsDurationList(context: Context): List<String> {

        val songsDurationListPrefs = context.getSharedPreferences("songsDurationList", Context.MODE_PRIVATE)
        val serializedSongsList = songsDurationListPrefs.getString("songsDuration", null)

        if (serializedSongsList != null) {
            songsDurationList = SerializeLists.deserialize(serializedSongsList) as ArrayList<String>
            return songsDurationList
        } else {
            try {
                val serialized : String = SerializeLists.serialize(MusicListsManager.getSongsDurationList(context) as ArrayList)
                val e = songsDurationListPrefs.edit()
                e.putString("songsDuration", serialized)
                e.apply()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return MusicListsManager.getSongsDurationList(context)
    }

    /*fun getAlbumArtPathsList(c: Context) {

        val albumArtPathsListPrefs = c.getSharedPreferences("AlbumArtPathsList", Context.MODE_PRIVATE)
        val serializedList = albumArtPathsListPrefs.getString("AlbumArtPaths", null)

        if (serializedList != null) {
            albumArtPathsList = SerializeLists.deserialize(serializedList) as ArrayList<String>
        } else {
            try {
                albumArtPathsList = MusicListsManager.getAlbumArtPathsList(c)

                val serialized : String = SerializeLists.serialize(albumArtPathsList as ArrayList)
                val e = albumArtPathsListPrefs.edit()
                e.putString("songsDuration", serialized)
                e.apply()

                val builder = NotificationCompat.Builder(c, "AlbumArtPathsList Size")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("AlbumArtPathsList Size")
                        .setContentText(albumArtPathsList.size.toString())

                val n = builder.build()
                val notificationManager = NotificationManagerCompat.from(c)

                notificationManager.notify(0, n)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }*/
}