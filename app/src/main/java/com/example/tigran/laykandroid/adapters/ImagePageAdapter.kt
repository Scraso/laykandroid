package com.example.tigran.laykandroid.adapters

import android.content.Context
import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImagePageAdapter(val context: Context? = null , private val imageUrls: List<String>? = emptyList()) : PagerAdapter() {


    override fun isViewFromObject(view: View, p1: Any): Boolean {
        return view == p1
    }

    override fun getCount(): Int {
        return imageUrls!!.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)

        Glide
            .with(context!!)
            .load(imageUrls!![position])
            .into(imageView)

        container.addView(imageView)

        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}