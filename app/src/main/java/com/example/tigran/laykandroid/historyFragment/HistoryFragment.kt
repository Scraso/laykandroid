package com.example.tigran.laykandroid.historyFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.models.Product

class HistoryFragment: Fragment() {

    private var onProcessing = ArrayList<Product>()
    private var

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_history, container, false)
        activity?.title = "История"
        return rootView
    }
}