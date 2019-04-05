package com.example.tigran.laykandroid.models

import java.io.Serializable

class HistoryProduct (
    var name: String = "",
    var price: Int = 0,
    var avatarImageUrl: String = "",
    var size: String = "",
    var ref: String = "",
    var status: String = "",
    var count: Int = 0,
    var userId: String? = "",
    var orderId: String? = ""
    ) : Serializable