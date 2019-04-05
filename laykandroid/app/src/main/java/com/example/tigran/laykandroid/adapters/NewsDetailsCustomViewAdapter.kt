package com.example.tigran.laykandroid.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.TAG
import com.example.tigran.laykandroid.models.NewsDetails
import kotlinx.android.synthetic.main.home_news_item_details_body.view.*
import kotlinx.android.synthetic.main.home_news_item_details_media.view.*

class NewsDetailsCustomViewAdapter(private val items: ArrayList<NewsDetails>, private val bodyText: String, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CellType.BODY.ordinal -> BodyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.home_news_item_details_body, parent, false)
            )
            else -> MediaViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.home_news_item_details_media,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return if (items.isEmpty()) {
            1
        } else {
            items.size + 1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            CellType.BODY.ordinal -> {
                val bodyViewHolder = holder as BodyViewHolder
                bodyViewHolder.configureView(bodyText)
            }
            CellType.MEDIA.ordinal -> {
                val mediaViewHolder = holder as MediaViewHolder
                mediaViewHolder.configureView(items[position - 1])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> CellType.BODY.ordinal
            else -> CellType.MEDIA.ordinal
        }
    }

    enum class CellType {
        BODY,
        MEDIA
    }

    inner class BodyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun configureView(bodyText: String) {
            itemView.bodyTextTextView.text = bodyText
        }
    }

    inner class MediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun configureView(item: NewsDetails) {
            itemView.mediaTitleTextView.text = item.title
            itemView.mediaBodyTextView.text = item.content

            Glide
                .with(context)
                .load(item.imageUrl)
                .into(itemView.mediaImageView)

            if (item.imageUrl.isEmpty()) {
                Log.e(TAG, "Image URL is empty")
                itemView.mediaCardImageView.visibility = View.GONE
            } else {
                Log.e(TAG, "Image URL is not empty")
                itemView.mediaCardImageView.visibility = View.VISIBLE
            }


        }
    }
}