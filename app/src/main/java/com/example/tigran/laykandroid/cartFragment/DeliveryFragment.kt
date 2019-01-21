package com.example.tigran.laykandroid.cartFragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.TAG
import com.example.tigran.laykandroid.adapters.DeliveryCustomViewAdapter
import com.example.tigran.laykandroid.models.CartItem
import com.example.tigran.laykandroid.services.DataService
import com.google.firebase.auth.FirebaseAuth
import java.lang.String.format
import java.util.*
import kotlin.collections.HashMap


class DeliveryFragment: Fragment() {

    private var recyclerView: androidx.recyclerview.widget.RecyclerView? = null
    private var menu: Menu? = null
    private lateinit var auth: FirebaseAuth

    // Items from the cart
    private var cartItems = mutableListOf<CartItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.delivery_details_view, container, false)
        activity?.title = "Доставка"
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

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

                val currentUserUid = auth.currentUser?.uid
                val timestamp = Date().apply { format("dd.MM.yyyy HH:mm") }
                Log.d(TAG, "Timestamp is $timestamp")
                val itemCount = cartItems.size
                val uploadCount = 0

                for (item in cartItems) {
                    val documentId = DataService.REF_ORDERS.document()
                    val orderDetails = HashMap<String, Any>()
                    orderDetails["name"] = item.name
                    orderDetails["size"] = item.size
                    orderDetails["price"] = item.price
                    orderDetails["itemDocumentId"] = item.documentId
                }



                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}