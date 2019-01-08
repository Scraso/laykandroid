package com.example.tigran.laykandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.models.Category
import com.example.tigran.laykandroid.shopFragment.ShopFragmentDirections
import kotlinx.android.synthetic.main.category_list_item.view.*


class CategoryCustomViewAdapter(private val categoryList: ArrayList<Category>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<CategoryCustomViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.category_list_item, parent, false)
        val holder = ViewHolder(cellForRow)

        // Get the clicked holder
        holder.itemView.setOnClickListener {

            // Navigate and pass category name as an argument
            val categoryName = cellForRow.category_type.text.toString()
            val action = ShopFragmentDirections.NextAction().setCategory(categoryName)
            it.findNavController().navigate(action)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category: Category = categoryList[position]
        holder.categoryName.text = category.name
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.category_type
    }

}

