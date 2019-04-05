package com.example.tigran.laykandroid.informationFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tigran.laykandroid.R

class ReturnFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.return_information_view, container, false)
        activity?.title = "Обмен и возврат"

        return rootView
    }
}