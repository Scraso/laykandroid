package com.example.tigran.laykandroid.cartFragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.TAG
import com.example.tigran.laykandroid.adapters.DeliveryCustomViewAdapter
import com.example.tigran.laykandroid.models.CartItem
import com.example.tigran.laykandroid.models.SharedViewModel
import androidx.lifecycle.ViewModelProviders
import java.lang.Exception

class DeliveryFragment: Fragment() {

    private var recyclerView: androidx.recyclerview.widget.RecyclerView? = null
    private lateinit var model: SharedViewModel

    // Items from the cart
    private var cartItems = mutableListOf<CartItem>()
    private var userDetailsViewModel = mutableMapOf<String, String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.delivery_details_view, container, false)
        activity?.title = "Доставка"


        val bundle = this.arguments
        if (bundle != null) {
            val testArray = bundle.getSerializable("cartItemsArrayList") as Array<CartItem>
            cartItems = testArray.toMutableList()
            Log.d(TAG,"Cart Items array is ${cartItems.size}")
        }

        // Call this option to re-draw Option Menu
        // Once it's called, onCreateOptionMenu will be called with the new parameters
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        model.getUserDeliveryInformation().observe(this, Observer { info ->
            userDetailsViewModel = info
            recyclerView?.adapter = DeliveryCustomViewAdapter(model, userDetailsViewModel)
        })

        recyclerView = view.findViewById(R.id.deliveryDetails_list)
        recyclerView?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        recyclerView?.adapter = DeliveryCustomViewAdapter(model, userDetailsViewModel)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val cartItem = menu.findItem(R.id.action_cart)
        cartItem.isVisible = false
    }
}