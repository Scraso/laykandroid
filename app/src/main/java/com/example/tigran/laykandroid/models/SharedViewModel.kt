package com.example.tigran.laykandroid.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tigran.laykandroid.services.DataService
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot


class SharedViewModel: ViewModel() {

    private val newQuery = DataService.REF_NEWS_HEADER.whereEqualTo("isPublished", true)
    private val liveData = FirebaseQueryLiveData(newQuery)

    fun getDataSnapshotLiveData(): LiveData<QuerySnapshot> {
        return liveData
    }

    val selected = MutableLiveData<CartItem>()

    fun select(item: CartItem) {
        selected.value = item
    }

    //    private lateinit var news: MutableLiveData<News>
//
//    fun getNews(): LiveData<News> {
//        if (!:: news.isInitialized) {
//            news = MutableLiveData()
//            loadNews()
//        }
//        return news
//    }
//
//
//    private fun loadNews() {
//        DataService.REF_NEWS_HEADER.whereEqualTo("isPublished", true).addSnapshotListener { snapshot, error ->
//            if (error != null) {
//                Log.w(TAG, "Something went wrong, fetching categories failed $error")
//            }
//
//            snapshot?.documents?.forEach { doc ->
//                if (doc != null && doc.exists()) {
//                    val newsData = doc.toObject(News::class.java)
//                    if (newsData != null) news.value = newsData
//                }
//            }
//        }
//    }

}