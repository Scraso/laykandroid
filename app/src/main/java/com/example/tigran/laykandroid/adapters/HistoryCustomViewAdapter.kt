package com.example.tigran.laykandroid.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tigran.laykandroid.models.CartItem
import com.example.tigran.laykandroid.models.Order
import java.util.ArrayList

class HistoryCustomViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var historyOrders = ArrayList<Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setOrderList(lifOfOrders: ArrayList<Order>) {
        this.historyOrders = lifOfOrders
        notifyDataSetChanged()
    }

}