package com.example.tigran.laykandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.tigran.laykandroid.R
import kotlinx.android.synthetic.main.fragment_information_item.view.*

class InformationCustomViewAdapter(private val listOfItems: List<String>) : androidx.recyclerview.widget.RecyclerView.Adapter<InformationCustomViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.fragment_information_item, parent, false)
        return ViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = listOfItems[position]
        holder.informationName.text = name

        holder.itemView.setOnClickListener {
            when (position) {
                0 -> it.findNavController().navigate(R.id.toAboutInformationView)
                1 -> it.findNavController().navigate(R.id.toDeliveryInformationView)
                2 -> it.findNavController().navigate(R.id.toReturnInformationView)
                3 -> it.findNavController().navigate(R.id.toCorInformationView)
            }
        }

    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val informationName: TextView = itemView.information_item_textView
        val itemImageView: ImageView = itemView.information_item_imageView
    }
}