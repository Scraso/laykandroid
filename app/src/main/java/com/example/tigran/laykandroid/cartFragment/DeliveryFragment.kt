package com.example.tigran.laykandroid.cartFragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.tigran.laykandroid.R

class DeliveryFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.delivery_details_view, container, false)
        activity?.title = "Доставка"
        // Call this option to re-draw Option Menu
        // Once it's called, onCreateOptionMenu will be called with the new parameters
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val cartItem = menu.findItem(R.id.action_cart)
        cartItem.isVisible = false
    }
}