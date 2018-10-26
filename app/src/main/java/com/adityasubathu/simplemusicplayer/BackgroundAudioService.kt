package com.adityasubathu.simplemusicplayer

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import java.io.IOException
import android.media.AudioAttributes
import android.os.Build


class BackgroundAudioService : Service(), MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnInfoListener, AudioManager.OnAudioFocusChangeListener {

    lateinit var player: MediaPlayer
    val iBinder: IBinder = LocalBinder()

    lateinit var mediaFile: String

    override fun onBind(intent: Intent?): IBinder? {

        //mediaFile = intent.getStringExtra()

        return iBinder
    }

    override fun onCompletion(mp: MediaPlayer?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPrepared(mp: MediaPlayer?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSeekComplete(mp: MediaPlayer?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAudioFocusChange(focusChange: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class LocalBinder : Binder() {

        fun getService(): BackgroundAudioService {

            return this@BackgroundAudioService
        }
    }

    private fun initMediaPlayer() {
        player = MediaPlayer()
        //Set up MediaPlayer event listeners
        player.setOnCompletionListener(this)
        player.setOnErrorListener(this)
        player.setOnPreparedListener(this)
        player.setOnSeekCompleteListener(this)
        player.setOnInfoListener(this)
        //player so that the MediaPlayer is not pointing to another data source
        player.reset()

        val mAudioManager = getSystemService(Context.AUDIO_SERVICE)

        val mPlaybackAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val mFocusRequest: AudioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(mPlaybackAttributes)
                    .setAcceptsDelayedFocusGain(true)
                    .setOnAudioFocusChangeListener(this)
                    .build()
        } else {
            player.setAudioStreamType(AudioManager.STREAM_MUSIC)
        }

        try {
            // Set the data source to the mediaFile location
            player.setDataSource(mediaFile)
        } catch (e: IOException) {
            e.printStackTrace()
            stopSelf()
        }

        player.prepareAsync()
    }


}