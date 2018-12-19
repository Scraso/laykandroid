package com.example.tigran.laykandroid.cartFragment

import android.content.ClipData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.adapters.CartCustomViewAdapter
import com.example.tigran.laykandroid.models.CartItem
import com.example.tigran.laykandroid.models.SharedViewModel
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : Fragment() {

    private lateinit var model: SharedViewModel
    val listOfItems = mutableListOf<CartItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_cart, container, false)
        activity?.title = "Корзина"
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()

        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        model.selected.observe(this, Observer {
            listOfItems.add(it)
        })
    }

    private fun initView() {
        recyclerCartView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)

        val cartCustomAdapter = CartCustomViewAdapter()
        recyclerCartView.adapter = cartCustomAdapter
        cartCustomAdapter.setItemList(listOfItems)
    }

    private fun generateItemData(): List<CartItem> {
        val listOfItems = mutableListOf<CartItem>()

        var itemModel = CartItem(1250, "Сlassic Black", "00006", "L", 5, "", "")
        listOfItems.add(itemModel)

        itemModel = CartItem(1250, "Jobox Lux", "00004", "M", 5, "", "")
        listOfItems.add(itemModel)

        itemModel = CartItem(1250, "Yosimitto Yellow Sport", "00003", "L", 5, "", "")
        listOfItems.add(itemModel)

        itemModel = CartItem(1250, "Yosimitto Yellow Sport", "00003", "L", 5, "", "")
        listOfItems.add(itemModel)

        itemModel = CartItem(1250, "Yosimitto Yellow Sport", "00003", "L", 5, "", "")
        listOfItems.add(itemModel)

        itemModel = CartItem(1250, "Yosimitto Yellow Sport", "00003", "L", 5, "", "")
        listOfItems.add(itemModel)


        return listOfItems

    }
}