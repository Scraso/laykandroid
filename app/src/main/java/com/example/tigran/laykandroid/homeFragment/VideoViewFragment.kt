package com.example.tigran.laykandroid.homeFragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tigran.laykandroid.R
import kotlinx.android.synthetic.main.viewplayer_home.*

class VideoViewFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.viewplayer_home, container, false)

        initializePlayer()

        return view
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