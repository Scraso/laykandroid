package com.example.tigran.laykandroid.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tigran.laykandroid.services.DataService
import com.google.firebase.firestore.QuerySnapshot


class SharedViewModel : ViewModel() {

    private val newsQuery = DataService.REF_NEWS_HEADER.whereEqualTo("published", true)
    private val testimonials = DataService.REF_TESTIMONIALS.whereEqualTo("published", true)
    private val shopCategory =  DataService.REF_SHOP_CATEGORY.whereEqualTo("isEnabled", true)

    private val newsLiveData = FirebaseQueryLiveData(newsQuery)
    private val testimonialsLiveData = FirebaseQueryLiveData(testimonials)
    private val shopCategoryLiveData = FirebaseQueryLiveData(shopCategory)

    fun getNewsDataSnapshotLiveData(): LiveData<QuerySnapshot> {
        return newsLiveData
    }

    fun getTestimonialsDataSnapshotLiveData(): LiveData<QuerySnapshot> {
        return testimonialsLiveData
    }

    fun getShopCategorySnapshotLiveData(): LiveData<QuerySnapshot> {
        return shopCategoryLiveData
    }

    fun getProductListDataSnapshotLiveData(category: String): LiveData<QuerySnapshot>? {
        val productListQuery = DataService.REF_ITEMS.whereEqualTo("category", category).whereEqualTo("isAvailable", true)
        return FirebaseQueryLiveData(productListQuery)
    }

    fun getCurrentUserOnProcessingOrdersLiveData(currentUserUid: String): LiveData<QuerySnapshot> {
        val onProcessingOrdersQuery = DataService.REF_ORDERS.whereEqualTo("status", "none").whereEqualTo("userId", currentUserUid)
        return FirebaseQueryLiveData(onProcessingOrdersQuery)
    }

    fun getCurrentUserOnProcessOfSendingOrdersLiveData(currentUserUid: String): LiveData<QuerySnapshot> {
        val onProcessingOrdersQuery = DataService.REF_ORDERS.whereEqualTo("status", "processed").whereEqualTo("userId", currentUserUid)
        return FirebaseQueryLiveData(onProcessingOrdersQuery)
    }

    fun getCurrentUserSentOrdersLiveData(currentUserUid: String): LiveData<QuerySnapshot> {
        val onProcessingOrdersQuery = DataService.REF_ORDERS.whereEqualTo("status", "sent").whereEqualTo("userId", currentUserUid)
        return FirebaseQueryLiveData(onProcessingOrdersQuery)
    }

    fun getCurrentUserCompletedOrdersLiveData(currentUserUid: String): LiveData<QuerySnapshot> {
        val onProcessingOrdersQuery = DataService.REF_ORDERS.whereEqualTo("status", "delivered").whereEqualTo("userId", currentUserUid)
        return FirebaseQueryLiveData(onProcessingOrdersQuery)
    }

    fun getNewsSectionParagraphs(documentId: String): LiveData<QuerySnapshot> {
        val newsSectionParagraphsQuery = DataService.REF_NEWS_PARAGRAPHS.whereEqualTo("documentId", documentId)
        return FirebaseQueryLiveData(newsSectionParagraphsQuery)
    }

    var cartItems = ArrayList<CartItem>()
    var cartItemLiveData = MutableLiveData<List<CartItem>>()

    fun select(item: CartItem) {

        // Check if there is already the same item in array
        val isUniqueItem = cartItems.contains(cartItems.find { it.name == item.name && it.size == item.size })

        // If item is already there, update count + 1 but if not then add new item in the array
        if (isUniqueItem) {
            cartItems = ArrayList(cartItems.map {
                val mutableItem = it
                if (mutableItem.name == item.name && mutableItem.size == item.size) {
                    mutableItem.count += 1
                }
                mutableItem
            })
            cartItemLiveData.value = cartItems
        } else {
            cartItems.add(item)
            cartItemLiveData.value = cartItems
        }
    }

//    companion object {
//        private lateinit var sInstance: SharedViewModel
//
//        fun get() : SharedViewModel {
//            sInstance = if (::sInstance.isInitialized) sInstance else SharedViewModel()
//            return sInstance
//        }
//    }

}