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
import com.example.tigran.laykandroid.adapters.HistoryCustomViewAdapter
import com.example.tigran.laykandroid.models.HistoryProduct
import com.example.tigran.laykandroid.models.Order
import com.example.tigran.laykandroid.models.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {

    private val onProcessing = mutableListOf<HistoryProduct>()
    private val onProcessingOfSending = mutableListOf<HistoryProduct>()
    private val sentItem = mutableListOf<HistoryProduct>()
    private val completed = mutableListOf<HistoryProduct>()

    private var listOfOrders = ArrayList<Order>()

    private lateinit var auth: FirebaseAuth
    var isUserLoggedIn = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_history, container, false)
        activity?.title = "История"

        auth = FirebaseAuth.getInstance()
//        checkUserActivity()

        return rootView
    }

    private var count = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        auth.addAuthStateListener {
            val user = auth.currentUser
            if (user != null) {
                isUserLoggedIn = true
                Log.e(TAG, "User is NOT null in Login History Fragment $isUserLoggedIn")

                fetchOrders {
                    val onProcessingOrder = Order("В обработке", onProcessing)
                    val onProcessingOfSendingOrder = Order("В ожидании отправки", onProcessingOfSending)
                    val onSentOrder = Order("Отправлен", sentItem)
                    val onCompletedOrder = Order("Получен", completed)
                    listOfOrders = arrayListOf(onProcessingOrder, onProcessingOfSendingOrder, onSentOrder, onCompletedOrder)

                    count += 1

                    if (count == 4) {
                        historyRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)

                        historyRecyclerView.adapter = HistoryCustomViewAdapter(
                            arrayListOf(
                                onProcessingOrder,
                                onProcessingOfSendingOrder,
                                onSentOrder,
                                onCompletedOrder
                            ), context!!, isUserLoggedIn
                        )
                        count = 1
                    }
                }

            } else {
                isUserLoggedIn = false

                Log.e(TAG, "User is null in Login History Fragment $isUserLoggedIn")

                fetchOrders {
                    val onProcessingOrder = Order("В обработке", onProcessing)
                    val onProcessingOfSendingOrder = Order("В ожидании отправки", onProcessingOfSending)
                    val onSentOrder = Order("Отправлен", sentItem)
                    val onCompletedOrder = Order("Получен", completed)
                    listOfOrders = arrayListOf(onProcessingOrder, onProcessingOfSendingOrder, onSentOrder, onCompletedOrder)

                    count += 1

                    if (count == 4) {
                        historyRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)

                        historyRecyclerView.adapter = HistoryCustomViewAdapter(
                            arrayListOf(
                                onProcessingOrder,
                                onProcessingOfSendingOrder,
                                onSentOrder,
                                onCompletedOrder
                            ), context!!, isUserLoggedIn
                        )
                        count = 1
                    }
                }
            }
        }

    }

    private fun fetchOrders(callback: () -> Unit) {
        val currentUserUid = auth.currentUser?.uid ?: ""
        val model = ViewModelProviders.of(this).get(SharedViewModel::class.java)

        val onProcessingLiveData = model.getCurrentUserOnProcessingOrdersLiveData(currentUserUid)
        val onProcessingOfSendingLiveData = model.getCurrentUserOnProcessOfSendingOrdersLiveData(currentUserUid)
        val onSentItemLiveData = model.getCurrentUserSentOrdersLiveData(currentUserUid)
        val completedLiveData = model.getCurrentUserCompletedOrdersLiveData(currentUserUid)


        // Fetch orders which are still under review

        onProcessingLiveData.observe(this, Observer<QuerySnapshot> { snapshot ->

            // Clear array to avoid duplicates
            onProcessing.clear()

            snapshot?.documents?.forEach { doc ->
                if (doc != null && doc.exists()) {
                    doc.toObject(HistoryProduct::class.java)?.let {
                        onProcessing += it
                    }
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
                    doc.toObject(HistoryProduct::class.java)?.let {
                        onProcessingOfSending += it
                    }
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
                    doc.toObject(HistoryProduct::class.java)?.let {
                        sentItem += it
                    }
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
                    doc.toObject(HistoryProduct::class.java)?.let {
                        completed += it
                    }
                }
            }
            callback()
        })
    }


    private fun checkUserActivity() {
        auth.addAuthStateListener {
            val user = auth.currentUser
            if (user != null) {
                isUserLoggedIn = true
                Log.e(TAG, "User is NOT null in Login History Fragment $isUserLoggedIn")
            } else {
                isUserLoggedIn = false
                historyRecyclerView.adapter?.notifyDataSetChanged()
                Log.e(TAG, "User is null in Login History Fragment $isUserLoggedIn")

            }

        }
    }


}