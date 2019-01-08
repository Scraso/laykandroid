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

    var recyclerView: androidx.recyclerview.widget.RecyclerView? = null
    private val deliveryDetailsArray: ArrayList<String> = ArrayList()

    private var cartItems = mutableListOf<CartItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.delivery_details_view, container, false)
        activity?.title = "Доставка"
        addDeliveryDetails()
        recyclerView = view.findViewById(R.id.deliveryDetails_list)
        recyclerView?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        recyclerView?.adapter = DeliveryCustomViewAdapter(deliveryDetailsArray)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val cartItem = menu.findItem(R.id.action_cart)
        cartItem.isVisible = false
    }

    private fun addDeliveryDetails() {
        deliveryDetailsArray.add("Ф.И.О")
        deliveryDetailsArray.add("Номер телефона")
        deliveryDetailsArray.add("Город")
        deliveryDetailsArray.add("Отделение Новой Почты")
        deliveryDetailsArray.add("Комментарий к заказу")
    }
}