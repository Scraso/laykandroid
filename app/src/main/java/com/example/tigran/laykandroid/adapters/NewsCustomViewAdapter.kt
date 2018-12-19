package com.example.tigran.laykandroid.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.models.News
import kotlinx.android.synthetic.main.home_news_item.view.*


class NewsCustomViewAdapter(private val news: ArrayList<News>, private val context: Context): androidx.recyclerview.widget.RecyclerView.Adapter<NewsCustomViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.home_news_item, parent, false)
        return NewsCustomViewAdapter.ViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
       return news.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = news[position]
        holder.title.text = news.title

        Glide
            .with(context)
            .load(news.avatarImageUrl)
            .into(holder.imageView)
    }


    // The same as UITableViewCell to declare variables and then pass the data
    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.imageView
        val title = itemView.titleTextVew
    }
}