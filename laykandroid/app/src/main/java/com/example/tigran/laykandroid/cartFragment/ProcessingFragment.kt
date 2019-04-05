package com.example.tigran.laykandroid.cartFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.processing_view.*
import com.example.tigran.laykandroid.R


class ProcessingFragment: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onAttachFragment(parentFragment!!)
        return inflater.inflate(R.layout.processing_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        doneBtn.setOnClickListener {

            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width,height)
        }

    }
}