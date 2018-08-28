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

    fun getAlbumArtPathsList(context: Context): MutableList<String> {

        val albumArtPathsList: MutableList<String> = ArrayList()
        val resolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val songsCursor = resolver.query(uri, null, null, null, MediaStore.Audio.Media.TITLE)
        val albumId = songsCursor.getInt(songsCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
        val albumArtCursor = resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART), MediaStore.Audio.Albums._ID + "=?",
                arrayOf(albumId.toString()), null)
        if (albumArtCursor != null && albumArtCursor.moveToFirst()) {
            val albumPathIndex = albumArtCursor.getInt(albumArtCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART))
            do {
                val path = albumArtCursor.getString(albumPathIndex)
                albumArtPathsList.add(path)
            } while (albumArtCursor.moveToNext())
        }
        songsCursor.close()
        albumArtCursor.close()
        return albumArtPathsList
    }
}