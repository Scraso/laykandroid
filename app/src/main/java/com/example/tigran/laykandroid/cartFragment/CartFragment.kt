package com.example.tigran.laykandroid.cartFragment

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

    private var listOfItems = mutableListOf<CartItem>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_cart, container, false)
        activity?.title = "Корзина"


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val cartCustomAdapter = CartCustomViewAdapter()
        initView(cartCustomAdapter)
        // Set observer for the live data. as soon as data changed, we set the data to setItemList which calls
        // notifier to update recyclerView with the newest data
        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        model.cartItemLiveData.observe(this, Observer { item ->
            listOfItems = item.toMutableList()
            if (context != null) {
                cartCustomAdapter.setItemList(listOfItems)
                cartCustomAdapter.model = model
            }
        })
    }

    private fun initView(cartCustomAdapter: CartCustomViewAdapter) {
        recyclerCartView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        recyclerCartView.adapter = cartCustomAdapter
        // Add divider
        val decoration = androidx.recyclerview.widget.DividerItemDecoration(
            context,
            androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
        )
        recyclerCartView.addItemDecoration(decoration)
        if (context != null) cartCustomAdapter.context = context!!
    }

}