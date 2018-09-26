package com.adityasubathu.simplemusicplayer

import android.content.Context
import android.provider.MediaStore

object MusicListsManager {

    fun getSongsList(context: Context): MutableList<String> {

        val songsList: MutableList<String> = ArrayList()
        val resolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = resolver.query(uri, null, null, null, MediaStore.Audio.Media.TITLE)

        if (cursor != null && cursor.moveToFirst()) {
            val songTitleIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            do {
                val title = cursor.getString(songTitleIndex)
                songsList.add(title)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return songsList
    }

    fun getArtistsList(context: Context): MutableList<String> {

        val artistsList: MutableList<String> = ArrayList()
        val resolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = resolver.query(uri, null, null, null, MediaStore.Audio.Media.TITLE)

        if (cursor != null && cursor.moveToFirst()) {
            val artistTitleIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            do {
                val artist = cursor.getString(artistTitleIndex)
                artistsList.add(artist)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return artistsList
    }

    fun getAlbumsList(context: Context): MutableList<String> {

        val albumsList: MutableList<String> = ArrayList()
        val resolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = resolver.query(uri, null, null, null, MediaStore.Audio.Media.TITLE)

        if (cursor != null && cursor.moveToFirst()) {
            val songTitleIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
            do {
                val album = cursor.getString(songTitleIndex)
                albumsList.add(album)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return albumsList
    }

    fun getSongsDurationList(context: Context): MutableList<String> {

        val songsDurationList: MutableList<String> = ArrayList()
        val resolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = resolver.query(uri, null, null, null, MediaStore.Audio.Media.TITLE)

        if (cursor != null && cursor.moveToFirst()) {
            val songDurationIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            do {
                val duration = cursor.getInt(songDurationIndex)
                songsDurationList.add(duration.toString())
            } while (cursor.moveToNext())
        }
        cursor.close()
        return songsDurationList
    }

    fun getArtFromAlbumID(context: Context, ALBUM_ID: Int): String {

        lateinit var path: String
        val resolver = context.contentResolver
        val uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI

        val albumArtCursor = resolver.query(uri, arrayOf(MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART),MediaStore.Audio.Albums._ID + "=?",
                arrayOf(ALBUM_ID.toString()), null)

        if (albumArtCursor != null && albumArtCursor.moveToFirst()) {
            path = albumArtCursor.getString(albumArtCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART))

        }

        albumArtCursor.close()

        return path

    }

    /*fun getAlbumArtPathsList(context: Context): MutableList<String> {

        val albumArtPathsList: MutableList<String> = ArrayList()
        val resolver = context.contentResolver
        val uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
        val albumArtCursor = resolver.query(uri, arrayOf(MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART), MediaStore.Audio.Albums._ID + "=?",
                null, MediaStore.Audio.Media.TITLE)
        if (albumArtCursor != null && albumArtCursor.moveToFirst()) {
            do {
                val path = albumArtCursor.getString(albumArtCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART))
                albumArtPathsList.add(path)
            } while (albumArtCursor.moveToNext())
        }
        albumArtCursor.close()
        return albumArtPathsList
    }*/

    fun getAlbumIDList(context: Context): MutableList<Int> {
        val albumIDList: MutableList<Int> = ArrayList()
        val resolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = resolver.query(uri, null, null, null, MediaStore.Audio.Media.TITLE)

        if (cursor != null && cursor.moveToFirst()) {
            val songDurationIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
            do {
                val id = cursor.getInt(songDurationIndex)
                albumIDList.add(id)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return albumIDList
    }
}