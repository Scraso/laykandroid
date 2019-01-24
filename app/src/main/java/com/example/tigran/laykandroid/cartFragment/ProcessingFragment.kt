package com.example.tigran.laykandroid.cartFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.tigran.laykandroid.R
import kotlinx.android.synthetic.main.processing_view.*


interface OnClickListenerInterface {
    fun dismissClicked()
}


class ProcessingFragment: DialogFragment() {

    private var listener: OnClickListenerInterface? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.processing_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        doneBtn.setOnClickListener {
            listener?.dismissClicked()
//            dismiss()
        }
    }

    fun setOnClickListener(listener: OnClickListenerInterface) {
        this.listener = listener
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