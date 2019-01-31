package com.example.tigran.laykandroid.historyFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.adapters.HistoryCustomViewAdapter
import com.example.tigran.laykandroid.models.HistoryProduct
import com.example.tigran.laykandroid.models.Order
import com.example.tigran.laykandroid.models.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_cart.*

class HistoryFragment: Fragment() {

    private val onProcessing = ArrayList<HistoryProduct>()
    private val onProcessingOfSending = ArrayList<HistoryProduct>()
    private val sentItem = ArrayList<HistoryProduct>()
    private val completed = ArrayList<HistoryProduct>()
    private var historyOrders = arrayListOf<Order>()

    private lateinit var auth: FirebaseAuth
    var recyclerview: androidx.recyclerview.widget.RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_history, container, false)
        activity?.title = "История"

        auth = FirebaseAuth.getInstance()

        fetchOrders {
            val onProcessingOrder = Order("В обработке", onProcessing)
            val onProcessingOfSendingOrder = Order("В ожидании отправки", onProcessingOfSending)
            val onSentOrder = Order("Отправлен", sentItem)
            val onCompletedOrder = Order("Получен", completed)
            historyOrders = arrayListOf(onProcessingOrder, onProcessingOfSendingOrder, onSentOrder, onCompletedOrder)
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val historyOrderAdapter = HistoryCustomViewAdapter()

        recyclerview = view.findViewById(R.id.historyRecyclerView)
        recyclerview?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        recyclerview?.adapter = HistoryCustomViewAdapter()
        historyOrderAdapter.setOrderList(historyOrders)
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
                    val product = doc.toObject(HistoryProduct::class.java)
                    if (product != null) onProcessing += product
                }
            }

            callback()

        })

        // Fetch orders which were processed and about to be send to the customer

        onProcessingOfSendingLiveData.observe(this, Observer<QuerySnapshot> { snapshot ->

            // Clear array to avoid duplicates
            onProcessingOfSending.clear()

            snapshot?.documents?.forEach { doc ->
                if (doc != null && doc.exists()) {
                    val product = doc.toObject(HistoryProduct::class.java)
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
                    val product = doc.toObject(HistoryProduct::class.java)
                    if (product != null) sentItem += product
                }
            }

            callback()

        })



        // Fetch orders which were delivered and accepted by customer

        completedLiveData.observe(this, Observer<QuerySnapshot> { snapshot ->

            // Clear array to avoid duplicates
            completed.clear()

            snapshot?.documents?.forEach { doc ->
                if (doc != null && doc.exists()) {
                    val product = doc.toObject(HistoryProduct::class.java)
                    if (product != null) completed += product
                }
            }

            callback()

        })
    }
}