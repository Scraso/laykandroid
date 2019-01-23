package com.example.tigran.laykandroid.cartFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.tigran.laykandroid.R
import kotlinx.android.synthetic.main.processing_view.*

class ProcessingFragment: DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.processing_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        doneBtn.setOnClickListener {
            dismiss()
        }
    }
}