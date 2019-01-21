package com.example.tigran.laykandroid.models

import java.io.Serializable

data class Product(
    var name: String = "",
    var price: Int = 0,
    var avatarImageUrl: String = "",
    var code: String = "",
    var documentId: String = "",
    var itemDetails: ArrayList<String> = arrayListOf(),
    var size: Map<String, Int> = emptyMap(),
    var images: ArrayList<String> = arrayListOf()
) : Serializable




