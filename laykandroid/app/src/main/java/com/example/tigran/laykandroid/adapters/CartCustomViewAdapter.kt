package com.example.tigran.laykandroid.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.models.CartItem
import com.example.tigran.laykandroid.models.SharedViewModel
import kotlinx.android.synthetic.main.cart_item_amount_view.view.*
import kotlinx.android.synthetic.main.cart_item_view.view.*


class CartCustomViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listOfItems = mutableListOf<CartItem>()
    lateinit var context: Context
    var model: SharedViewModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CellType.ITEM.ordinal -> CartItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.cart_item_view,
                    parent,
                    false
                )
            )
            CellType.TOTAL.ordinal -> TotalAmountViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.cart_item_amount_view,
                    parent,
                    false
                )
            )
            else -> EmptyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.cart_item_empty_view,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return if (listOfItems.isEmpty()) {
            1
        } else {
            listOfItems.size + 1
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            CellType.ITEM.ordinal -> {
                val itemViewHolder = holder as CartItemViewHolder
                itemViewHolder.configureView(listOfItems[position])
            }
            CellType.TOTAL.ordinal -> {
                val totalAmountHolder = holder as TotalAmountViewHolder
                totalAmountHolder.configureView()
            }
        }
    }

    /***
     * This method will return cell type base on position
     */

    override fun getItemViewType(position: Int): Int {
        return if (listOfItems.isEmpty()) {
            CellType.EMPTY.ordinal
        } else {
            when (position) {
                listOfItems.size -> CellType.TOTAL.ordinal
                else -> CellType.ITEM.ordinal
            }
        }
    }

    /***
     * Enum class for recyclerview Cell type
     */
    enum class CellType {
        ITEM,
        TOTAL,
        EMPTY
    }

    fun setItemList(listOfItems: List<CartItem>) {
        this.listOfItems = listOfItems.toMutableList()
        notifyDataSetChanged()
    }

    private fun removeItem(position: Int) {
        // Remove from the ShareViewModel array and update LiveData/
        // Once LiveData will be updated, observer will trigger and update listOfItems array size.
        model?.cartItems?.removeAt(position)
        model?.cartItemLiveData?.value = model?.cartItems
    }


    inner class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun configureView(cartItemData: CartItem) {
            itemView.itemNameTextView.text = "${cartItemData.name} ${cartItemData.size}"
            itemView.itemRefTextView.text = "Код товара: ${cartItemData.ref}"
            itemView.itemPriceTextView.text = "${cartItemData.price} грн"
            itemView.itemCountTextView.text = "${cartItemData.count}"

            itemView.plusBtn.setOnClickListener {
                listOfItems.map { item ->
                    if (item.avatarImageUrl == cartItemData.avatarImageUrl && item.size == cartItemData.size) {
                        item.count += 1
                    }
                    itemView.itemCountTextView.text = item.count.toString()
                    notifyDataSetChanged()
                }
            }

            itemView.minusBtn.setOnClickListener {
                listOfItems.map { item ->
                    if (item.avatarImageUrl == cartItemData.avatarImageUrl && item.size == cartItemData.size) {
                        if (item.count == 1) {
                            item.count = 1
                        } else {
                            item.count -= 1
                        }
                    }
                    itemView.itemCountTextView.text = item.count.toString()
                    notifyDataSetChanged()
                }
            }

            itemView.deleteItemBtn.setOnClickListener {
                removeItem(adapterPosition)
            }

            Glide
                .with(context)
                .apply {
                    RequestOptions()
                        .fitCenter()
                }
                .load(cartItemData.avatarImageUrl)
                .into(itemView.itemImageView)

        }

    }

    inner class TotalAmountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun configureView() {

            val cost = listOfItems.map {
                it.count * it.price
            }.sum()

            itemView.totalAmountTextView.text = "$cost грн"
        }
    }

    inner class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}