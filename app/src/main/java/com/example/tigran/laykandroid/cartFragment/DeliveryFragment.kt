package com.example.tigran.laykandroid.cartFragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.TAG
import com.example.tigran.laykandroid.adapters.DeliveryCustomViewAdapter
import com.example.tigran.laykandroid.models.CartItem


class DeliveryFragment: Fragment() {

    private var recyclerView: androidx.recyclerview.widget.RecyclerView? = null
    private var menu: Menu? = null

    // Items from the cart
    private var cartItems = mutableListOf<CartItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.delivery_details_view, container, false)
        activity?.title = "Доставка"


        val bundle = this.arguments
        if (bundle != null) {
            val testArray = bundle.getSerializable("cartItemsArrayList") as Array<CartItem>
            cartItems = testArray.toMutableList()
        }

        // Call this option to re-draw Option Menu
        // Once it's called, onCreateOptionMenu will be called with the new parameters
        setHasOptionsMenu(true)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.deliveryDetails_list)
        recyclerView?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val cartItem = menu.findItem(R.id.action_cart)
        this.menu = menu
        cartItem.isVisible = false
        // Pass menu value to adapter once menu will be assigned
        recyclerView?.adapter = DeliveryCustomViewAdapter(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_done -> {
                Log.d(TAG," Done button was pressed")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}