package com.example.tigran.laykandroid.informationFragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.TAG
import com.example.tigran.laykandroid.models.ClickHandler
import kotlinx.android.synthetic.main.cooperation_information_view.*
import kotlinx.android.synthetic.main.delivery_information_view.*

class CooperationFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.cooperation_information_view, container, false)
        activity?.title = "Сотрудничество"

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setClickable(cooperationEmailTextView, "laykwear@gmail.com", handleEmailClick())
        setClickable(cooperationPhoneNumberTextView, "+380633720429", handlePhoneClick())
    }

    private fun handleEmailClick(): () -> Unit {
        return {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "plain/text"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("laykwear@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Оплата и доставка")
            intent.putExtra(Intent.EXTRA_TEXT, "Комментарий...")
            startActivity(Intent.createChooser(intent, ""))
            Log.d(TAG, "Email was pressed")
        }
    }

    private fun handlePhoneClick(): () -> Unit {
        return {
            checkPermission()
            Log.d(TAG, "Phone was pressed")
        }
    }

    private fun callPhone() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+380633720429"))
        startActivity(intent)
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(context!!,
                Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,
                    Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity!!,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42)
            }
        } else {
            // Permission has already been granted
            callPhone()
        }
    }

    private fun setClickable(textView: TextView, subString: String, handler: () -> Unit, drawUnderline: Boolean = true) {
        val text = textView.text
        val start = text.indexOf(subString)
        val end = start + subString.length

        val span = SpannableString(text)
        span.setSpan(ClickHandler(handler, drawUnderline), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.linksClickable = true
        textView.isClickable = true
        textView.movementMethod = LinkMovementMethod.getInstance()

        textView.text = span
    }


}