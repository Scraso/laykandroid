package com.example.tigran.laykandroid.services

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

val DB_BASE: FirebaseFirestore
    get() = FirebaseFirestore.getInstance()


object DataService {
    init {
        println("This $this is singleton")
    }

    // Firestore reference
    private val REF_SHOP_CATEGORY_ = DB_BASE.collection("shop_category")
    private val REF_ITEMS_ = DB_BASE.collection("items")

    private val REF_NEWS_HEADER_ = DB_BASE.collection("news_section_header")
    private val REF_NEWS_PARAGRAPHS_ = DB_BASE.collection("news_section_paragraphs")

    private val REF_TESTIMONIALS_ = DB_BASE.collection("testimonials")


    val REF_SHOP_CATEGORY: CollectionReference
        get() = REF_SHOP_CATEGORY_

    val REF_ITEMS: CollectionReference
        get() = REF_ITEMS_

    val REF_NEWS_HEADER: CollectionReference
        get() = REF_NEWS_HEADER_

    val REF_NEWS_PARAGRAPHS: CollectionReference
        get() = REF_NEWS_PARAGRAPHS_

    val REF_TESTIMONIALS: CollectionReference
        get() = REF_TESTIMONIALS_

}
