package com.example.tigran.laykandroid.historyFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.TAG
import com.example.tigran.laykandroid.models.Order
import com.example.tigran.laykandroid.models.Product
import com.example.tigran.laykandroid.models.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QuerySnapshot

class HistoryFragment: Fragment() {

    private val onProcessing = ArrayList<Product>()
    private val onProcessingOfSending = ArrayList<Product>()
    private val sentItem = ArrayList<Product>()
    private val completed = ArrayList<Product>()
    private val historyOrders = ArrayList<Order>()

    private lateinit var auth: FirebaseAuth
    var recyclerview: androidx.recyclerview.widget.RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_history, container, false)
        activity?.title = "История"

        auth = FirebaseAuth.getInstance()

        fetchOrders {
            historyOrders += Order("В обработке", onProcessing)
            historyOrders += Order("В ожидании отправки", onProcessingOfSending)
            historyOrders += Order("Отправлен", sentItem)
            historyOrders += Order("Получен", completed)
            recyclerview?.adapter?.notifyDataSetChanged()
            Log.d(TAG, "History orders size is ${historyOrders.size}")
            Log.d(TAG, "Data is fetched")
        }

        return rootView
    }


    private fun fetchOrders(callback: () -> Unit) {
        val currentUserUid = auth.currentUser?.uid ?: ""
        val model = ViewModelProviders.of(this).get(SharedViewModel::class.java)

        val onProcessingLiveData = model.getCurrentUserOnProcessingOrdersLiveData(currentUserUid)
        val onProcessingOfSendingLiveData = model.getCurrentUserOnProcessingOrdersLiveData(currentUserUid)
        val onSentItemLiveData = model.getCurrentUserSentOrdersLiveData(currentUserUid)
        val completedLiveData = model.getCurrentUserCompletedOrdersLiveData(currentUserUid)


        // Fetch orders which are still under review

        onProcessingLiveData.observe(this, Observer<QuerySnapshot> { snapshot ->

            // Clear array to avoid duplicates
            onProcessing.clear()

            snapshot?.documents?.forEach { doc ->
                if (doc != null && doc.exists()) {
                    val product = doc.toObject(Product::class.java)
                    if (product != null) onProcessing += product
                }
            }

        })

        // Fetch orders which were processed and about to be send to the customer

        onProcessingOfSendingLiveData.observe(this, Observer<QuerySnapshot> { snapshot ->

            // Clear array to avoid duplicates
            onProcessingOfSending.clear()

            snapshot?.documents?.forEach { doc ->
                if (doc != null && doc.exists()) {
                    val product = doc.toObject(Product::class.java)
                    if (product != null) onProcessingOfSending += product

                }
            }

            callback()

        })

        // Fetch orders which were sent to the customer

        onSentItemLiveData.observe(this, Observer<QuerySnapshot> { snapshot ->

            // Clear array to avoid duplicates
            sentItem.clear()

            snapshot?.documents?.forEach { doc ->
                if (doc != null && doc.exists()) {
                    val product = doc.toObject(Product::class.java)
                    if (product != null) sentItem += product
                }
            }
        })



        // Fetch orders which were delivered and accepted by customer

        completedLiveData.observe(this, Observer<QuerySnapshot> { snapshot ->

            // Clear array to avoid duplicates
            completed.clear()

            snapshot?.documents?.forEach { doc ->
                if (doc != null && doc.exists()) {
                    val product = doc.toObject(Product::class.java)
                    if (product != null) completed += product
                }
            }

        })
    }
}