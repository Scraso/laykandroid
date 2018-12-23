package com.example.tigran.laykandroid.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tigran.laykandroid.TAG
import com.example.tigran.laykandroid.services.DataService
import com.google.firebase.firestore.QuerySnapshot


class SharedViewModel : ViewModel() {

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

        // Because each object has it's own ID, so we need to compare values
        // the same way as in IOS using something similar to contains(where:)

//        val isUniqueItem = cartItems.contains(item)
//        Log.d(TAG, "Cart Items size is ${cartItems.size}")
//
//        if (isUniqueItem) {
//            Log.d(TAG, "Item is unique")
////            cartItems.map {
////                if (it.avatarImageUrl == item.avatarImageUrl && it.size == item.size) {
////                    it.count += 1
////                }
////            }
////            cartItemLiveData.value = cartItems
//        } else {
//            Log.d(TAG, "Item is not unique")
////            cartItems.add(item)
////            cartItemLiveData.value = cartItems
//        }

        if (cartItems.isEmpty()) {
            cartItems.add(item)
            cartItemLiveData.value = cartItems
        } else {
            cartItems.map {
                if (item.avatarImageUrl == it.avatarImageUrl) {
                    it.count += 1
                    cartItemLiveData.value = cartItems
                    Log.d(TAG, "Cart item was changed")
                } else {
                    cartItems.add(item)
                    cartItemLiveData.value = cartItems
                    Log.d(TAG, "Cart item wasn't changed")
                }
            }
        }
    }


}