package com.example.tigran.laykandroid.models

import java.io.Serializable

data class CartItem (
    val price: Int = 0,
    val name: String = "",
    val ref: String = "",
    val size: String = "",
    var count: Int = 0,
    val documentId: String = "",
    val avatarImageUrl: String = ""
) : Serializable