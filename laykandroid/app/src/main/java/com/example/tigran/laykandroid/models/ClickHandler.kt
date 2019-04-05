package com.example.tigran.laykandroid.models

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

class ClickHandler(private val handler: () -> Unit, private val drawUnderline: Boolean) : ClickableSpan() {

    override fun onClick(widget: View?) {
        handler()
    }

    override fun updateDrawState(ds: TextPaint?) {
        if (drawUnderline) {
            super.updateDrawState(ds)
        } else {
            ds?.isUnderlineText = false
        }
    }
}