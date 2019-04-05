package com.example.tigran.laykandroid.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.TAG
import com.example.tigran.laykandroid.adapters.HistoryCustomViewAdapter.CellType.*
import com.example.tigran.laykandroid.models.HistoryProduct
import com.example.tigran.laykandroid.models.Order
import kotlinx.android.synthetic.main.history_child_item.view.*
import kotlinx.android.synthetic.main.history_section_item.view.*
import java.util.ArrayList

class HistoryCustomViewAdapter(listOfOrders: ArrayList<Order>?, val context: Context, var isUserLoggedIn: Boolean) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var historyOrders = mutableListOf<ItemHistory>()

    init {

        listOfOrders?.let {
            it.forEach { order ->
                historyOrders.add(ItemHistory(order.sectionName))
                order.orders.forEach { history ->
                    historyOrders.add(ItemHistory(historyProduct = history))
                }
            }
        }


//        listOfOrders.forEach {
//            //Log.e("Adapter", "Title: ${it.sectionName}")
//            historyOrders.add(ItemHistory(it.sectionName))
//            it.orders.forEach { history ->
//                //Log.e("Adapter", "Product: ${it.toString()}")
//                historyOrders.add(ItemHistory(historyProduct = history))
//            }
//        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (CellType.getType(viewType)) {
        HEADER -> SectionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.history_section_item,
                parent,
                false
            )
        )
        ORDER -> ChildViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.history_child_item,
                parent,
                false
            )
        )
        EMPTY -> EmptyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_history_empty_view,
                parent,
                false
            )
        )
        else -> LoginViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_history_login_view,
                parent,
                false
            )
        )
    }

//    override fun getItemCount() = historyOrders.size

    override fun getItemCount(): Int {
        return if (historyOrders.size == 4) {
            return 1
        } else {
            historyOrders.size
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = historyOrders[position]
        when (holder) {
            is SectionViewHolder -> holder.bind(item)
            is ChildViewHolder -> holder.bind(item)
        }
    }

//    override fun getItemViewType(position: Int) = if (historyOrders[position].title != null) {
//        HEADER.type
//    } else {
//        ORDER.type
//    }

//    private var isUserLoggedIn = false

    override fun getItemViewType(position: Int): Int {

        return if (!isUserLoggedIn) {
            LOGIN.type
        } else if (historyOrders.size == 4 && isUserLoggedIn) {
            return EMPTY.type
        } else {
            if (historyOrders[position].title != null) {
                HEADER.type
            } else {
                ORDER.type
            }
        }

//        return if (historyOrders.size == 4) {
//            EMPTY.type
//        } else {
//            if (historyOrders[position].title != null) {
//                HEADER.type
//            } else {
//                ORDER.type
//            }
//        }
    }

    enum class CellType(val type: Int) {
        HEADER(0),
        ORDER(1),
        EMPTY(2),
        LOGIN(3);

        companion object {
            //            fun getType(type: Int) = if (HEADER.type == type) HEADER  else ORDER
            fun getType(type: Int) = when (type) {
                HEADER.type -> HEADER
                ORDER.type -> ORDER
                EMPTY.type -> EMPTY
                else -> LOGIN
            }
        }
    }


    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(itemHistory: ItemHistory) {
            Log.e("ITEM", "$itemHistory")
            itemView.sectionName.text = itemHistory.title
        }
    }

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(itemHistory: ItemHistory) {
            itemView.historyItemNameTextView.text = itemHistory.historyProduct?.name
            itemView.historyItemRefTextView.text = "Код товара: ${itemHistory.historyProduct?.ref}"
            itemView.historyItemCountTextView.text = "Количество ${itemHistory.historyProduct?.count}"
            itemView.historyItemPriceTextView.text = "${itemHistory.historyProduct?.price} грн"


            if (itemHistory.historyProduct?.status == "none") {
                itemView.historyItemStatusView.setBackgroundColor(Color.parseColor("#FF9500"))
            }
            if (itemHistory.historyProduct?.status == "processed") {
                itemView.historyItemStatusView.setBackgroundColor(Color.parseColor("#FFB6350A"))
            }
            if (itemHistory.historyProduct?.status == "sent") {
                itemView.historyItemStatusView.setBackgroundColor(Color.parseColor("#FF007AFF"))
            }
            if (itemHistory.historyProduct?.status == "delivered") {
                itemView.historyItemStatusView.setBackgroundColor(Color.parseColor("#6200EE"))
            }



            Glide
                .with(context)
                .apply {
                    RequestOptions()
                        .fitCenter()
                }
                .load(itemHistory.historyProduct?.avatarImageUrl)
                .into(itemView.historyItemAvatarImageView)
        }
    }

    inner class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class LoginViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    data class ItemHistory(val title: String? = null, val historyProduct: HistoryProduct? = null)

}




