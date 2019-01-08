package com.example.tigran.laykandroid.adapters

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.TAG
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.delivery_details_item.view.*

interface DeliveryDetails {
    val deliveryInformation: MutableMap<String, String>?
}

class DeliveryCustomViewAdapter(private val deliveryDetails: ArrayList<String>): androidx.recyclerview.widget.RecyclerView.Adapter<DeliveryCustomViewAdapter.ViewHolder>(), DeliveryDetails {

    override val deliveryInformation: MutableMap<String, String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.delivery_details_item, parent, false)

        return ViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return deliveryDetails.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hintDetails = deliveryDetails[position]
        holder.inputText.hint = hintDetails
        holder.inputText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                deliveryInformation?.put("testname", "Tigran")
                deliveryInformation?.put("testLastname", "Ambarcumyan")
                when (position) {
                    0 -> deliveryInformation?.put("name", s.toString())
                    1 -> deliveryInformation?.put("phone", s.toString())
                    2 -> deliveryInformation?.put("city", s.toString())
                    3 -> deliveryInformation?.put("address", s.toString())
                    4 -> deliveryInformation?.put("comment", s.toString())
                }

                Log.d(TAG, "Delivery name is ${deliveryInformation?.get("name")}")
                Log.d(TAG, "Delivery phone is ${deliveryInformation?.get("phone")}")
                Log.d(TAG, "Delivery city is ${deliveryInformation?.get("city")}")
                Log.d(TAG, "Delivery address is ${deliveryInformation?.get("address")}")
                Log.d(TAG, "Delivery comment is ${deliveryInformation?.get("comment")}")

                Log.d(TAG, "Delivery testname is ${deliveryInformation?.get("testname")}")
                Log.d(TAG, "Delivery testLastname is ${deliveryInformation?.get("testLastname")}")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })


//        when (position) {
//            0 -> holder.inputText.hint = "Ф.И.О"
//            1 -> holder.inputText.hint = "Номер телефона"
//            2 -> holder.inputText.hint = "Город"
//            3 -> holder.inputText.hint = "Отделение Новой Почты"
//            4 -> holder.inputText.hint = "Комментарий к заказу"
//        }
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val inputText: TextInputEditText = itemView.deliveryInputText
    }
}