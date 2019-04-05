package com.example.tigran.laykandroid.models

data class Order(val sectionName: String = "", val orders: List<HistoryProduct> = emptyList())