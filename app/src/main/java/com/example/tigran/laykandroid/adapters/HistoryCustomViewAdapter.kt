package com.example.tigran.laykandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.adapters.HistoryCustomViewAdapter.CellType.*
import com.example.tigran.laykandroid.models.Order
import java.util.ArrayList

class HistoryCustomViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var historyOrders = mutableListOf<Order>()
        set(value) {
            field = value
            headers = mutableListOf()
            value.forEach {

            }
        }
    private var headers = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= when(CellType.getType(viewType)){
            HEADER -> SectionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.history_section_item, parent, false))
            ORDER -> ChildViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.history_child_item, parent, false))
        }

    override fun getItemCount(): Int {
        var itemsCount = 0

        historyOrders.forEach{
            itemsCount += (it.orders.size + 1)
        }

        return itemCount
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemViewType(position: Int) = if(position == 0){
            HEADER.type
        }else{
            ORDER.type
        }

    fun setOrderList(lifOfOrders: ArrayList<Order>) {
        this.historyOrders = lifOfOrders
        notifyDataSetChanged()
    }

    enum class CellType(val type:Int) {
        HEADER(0),
        ORDER(1);

        companion object {
            fun getType(type:Int) = if(HEADER.type == 0) HEADER else ORDER
        }
    }


    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


    }