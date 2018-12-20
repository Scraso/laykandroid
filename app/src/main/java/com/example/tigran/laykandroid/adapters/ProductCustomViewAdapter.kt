package com.example.tigran.laykandroid.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.TAG
import com.example.tigran.laykandroid.models.Product
import com.example.tigran.laykandroid.shopFragment.ItemDetailsFragment
import com.example.tigran.laykandroid.shopFragment.ProductListFragmentDirections
import kotlinx.android.synthetic.main.product_list_item.view.*


class ProductCustomViewAdapter(private val products: ArrayList<Product>, private val context: Context): androidx.recyclerview.widget.RecyclerView.Adapter<ProductCustomViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.product_list_item, parent, false)
        return  ViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.itemName.text = product.name
        holder.itemCode.text = "Код товара: ${product.code}"
        holder.itemPrice.text = "${product.price} грн"
        holder.firstItemDetails.text = product.itemDetails[0]
        holder.secondItemDetails.text = product.itemDetails[1]


        // Not working properly??

        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()


        Glide
            .with(context)
            .apply { RequestOptions()
                .placeholder(circularProgressDrawable)
                .fitCenter()}
            .load(product.avatarImageUrl)
            .into(holder.imageView)


        val totalSizeCount = product.size.map { it.value }.sum()
        if (totalSizeCount > 0) {
            holder.itemStatus.text = "Есть в наличии"
            holder.itemStatus.setTextColor(Color.rgb(76,217,100))
        } else {
            holder.itemStatus.text = "Нет в наличии"
            holder.itemStatus.setTextColor(Color.rgb(255,59,48))
        }


        // Get the clicked holder and pass the data
        holder.itemView.setOnClickListener {

            val arguments = Bundle()
            arguments.putSerializable("product", product)
            it.findNavController().navigate(R.id.toItemDetailsFragmentAction, arguments)

        }

    }


    // The same as UITableViewCell to declare variables and then pass the data
    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.item_name
        val itemCode: TextView = itemView.item_code
        val itemPrice: TextView = itemView.item_price
        val firstItemDetails: TextView = itemView.item_details_1
        val secondItemDetails: TextView = itemView.item_details_2
        var imageView: ImageView = itemView.item_imageView
        val itemStatus: TextView = itemView.item_availability
    }
}