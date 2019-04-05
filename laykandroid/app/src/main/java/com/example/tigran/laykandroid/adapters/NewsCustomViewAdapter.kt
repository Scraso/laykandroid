package com.example.tigran.laykandroid.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.homeFragment.MenuFragmentDirections
import com.example.tigran.laykandroid.models.News
import kotlinx.android.synthetic.main.news_home_item.view.*

class NewsCustomViewAdapter(private val news: ArrayList<News>, private val context: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<NewsCustomViewAdapter.ViewHolder>() {

    private var avatarImageUrl: String = ""
    private var bodyText: String = ""
    private var documentId: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.news_home_item, parent, false)
        val holder = ViewHolder(cellForRow)

        holder.itemView.setOnClickListener {

            val position = holder.adapterPosition
            val news = news[position]
            this.avatarImageUrl = news.avatarImageUrl
            this.bodyText = news.body_text
            this.documentId = news.documentId

            val action = MenuFragmentDirections.ToNewsDetails().setAvatarImageUrl(avatarImageUrl).setBodyText(bodyText)
                .setDocumentId(documentId)
            it.findNavController().navigate(action)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news1 = news[position]
        holder.title.text = news1.title

        Glide
            .with(context)
            .load(news1.avatarImageUrl)
            .into(holder.imageView)
    }


    // The same as UITableViewCell to declare variables and then pass the data
    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.newsAvatarImageView
        val title: TextView = itemView.newsTitleTextView
    }
}