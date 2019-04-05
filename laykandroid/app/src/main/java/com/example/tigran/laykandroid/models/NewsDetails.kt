package com.example.tigran.laykandroid.models

import java.io.Serializable

data class NewsDetails(
    val content: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val timestamp: Long = -1
)