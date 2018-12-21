package com.example.tigran.laykandroid.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tigran.laykandroid.services.DataService
import com.google.firebase.firestore.QuerySnapshot


class SharedViewModel: ViewModel() {

    private val newsQuery = DataService.REF_NEWS_HEADER.whereEqualTo("isPublished", true)
    private val testimonials = DataService.REF_TESTIMONIALS.whereEqualTo("isPublished", true)
    private val newsLiveData = FirebaseQueryLiveData(newsQuery)
    private val testimonialsLiveData = FirebaseQueryLiveData(testimonials)

    fun getNewsDataSnapshotLiveData(): LiveData<QuerySnapshot> {
        return newsLiveData
    }

    fun getTestimonialsDataSnapshotLiveData(): LiveData<QuerySnapshot> {
        return testimonialsLiveData
    }

    private var cartItems = ArrayList<CartItem>()
    var cartItemLiveData = MutableLiveData<List<CartItem>>()

    fun select(item: CartItem) {
        cartItems.add(item)
        cartItemLiveData.value = cartItems
    }


}