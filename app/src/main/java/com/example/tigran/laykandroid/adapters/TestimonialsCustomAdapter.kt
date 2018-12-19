package com.example.tigran.laykandroid.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.models.Testimonials
import kotlinx.android.synthetic.main.home_testimonials_item.view.*

class TestimonialsCustomAdapter(private val testimonials: ArrayList<Testimonials>, private val context: Context): androidx.recyclerview.widget.RecyclerView.Adapter<TestimonialsCustomAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.home_testimonials_item, parent, false)
        return TestimonialsCustomAdapter.ViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return testimonials.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val testimonial = testimonials[position]
        holder.nameTextView.text = testimonial.name
        holder.commentTextView.text = testimonial.text

        Glide
            .with(context)
            .load(testimonial.imageUrl)
            .into(holder.avatarImageView)

    }

    // The same as UITableViewCell to declare variables and then pass the data
    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val commentTextView: TextView = itemView.commentTextView
        val avatarImageView: ImageView = itemView.avatarImageView
        val nameTextView: TextView = itemView.nameTextView
    }

}