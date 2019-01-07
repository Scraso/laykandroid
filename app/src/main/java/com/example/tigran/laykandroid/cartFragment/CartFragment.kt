package com.example.tigran.laykandroid.cartFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.TAG
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
        // Call this option to re-draw Option Menu
        // Once it's called, onCreateOptionMenu will be called with the new parameters
        setHasOptionsMenu(true)
        return rootView
    }

    @SuppressLint("RestrictedApi")
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
                if (listOfItems.isEmpty()) {
                    shippingBtn.visibility = View.INVISIBLE
                } else {
                    shippingBtn.visibility = View.VISIBLE
                }
            }
        })

        shippingBtn.setOnClickListener {
            it.findNavController().navigate(R.id.nav_delivery)
        }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val cartItem = menu.findItem(R.id.action_cart)
        cartItem.isVisible = false
    }

}