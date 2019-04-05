package com.example.tigran.laykandroid.models

import java.io.Serializable

data class News(
    val avatarImageUrl: String = "",
    val title: String = "",
    val published: Boolean = false,
    val timestamp: Long = -1,
    val body_text: String = "",
    val documentId: String = ""
) : Serializable
