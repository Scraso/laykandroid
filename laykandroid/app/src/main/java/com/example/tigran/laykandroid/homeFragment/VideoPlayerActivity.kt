package com.example.tigran.laykandroid.homeFragment

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tigran.laykandroid.R
import kotlinx.android.synthetic.main.viewplayer_home.*

class VideoPlayerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewplayer_home)

        initializePlayer()
    }

    private fun getMedia() : Uri {
        return Uri.parse("https://res.cloudinary.com/layk-com-ua/video/upload/v1541034087/layk_promo/layk_promo_low.mp4")
    }

    private fun initializePlayer() {
        val videoUrl: Uri = getMedia()
        viewPlayer.setVideoURI(videoUrl)
        viewPlayer.start()
    }
}