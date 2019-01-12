package com.example.tigran.laykandroid.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.models.SharedViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.delivery_details_item.view.*



class DeliveryCustomViewAdapter(private val model: SharedViewModel, private val deliveryViewModel: MutableMap<String, String>): androidx.recyclerview.widget.RecyclerView.Adapter<DeliveryCustomViewAdapter.ViewHolder>() {

    // Extension for edit Text setText
    var EditText.value
        get() = this.text.toString()
        set(value) {
            this.setText(value)
        }

    private val deliveryMutableMap = mutableMapOf<String, String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.delivery_details_item, parent, false)
        return ViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (deliveryViewModel["name"] != null) {
            holder.nameInputText.value = deliveryViewModel["name"].toString()
        }
        if (deliveryViewModel["phone"] != null) {
            holder.phoneInputText.value = deliveryViewModel["phone"].toString()
        }
        if (deliveryViewModel["city"] != null) {
            holder.cityInputText.value = deliveryViewModel["city"].toString()
        }
        if (deliveryViewModel["address"] != null) {
            holder.addressInputText.value = deliveryViewModel["address"].toString()
        }
        if (deliveryViewModel["comment"] != null) {
            holder.commentInputText.value = deliveryViewModel["comment"].toString()
        }


        holder.nameInputText.onChange {
            deliveryMutableMap["name"] = it
            model.setUserDeliveryInformation(deliveryMutableMap)
        }
        holder.phoneInputText.onChange {
            deliveryMutableMap["phone"] = it
            model.setUserDeliveryInformation(deliveryMutableMap)
        }
        holder.cityInputText.onChange {
            deliveryMutableMap["city"] = it
            model.setUserDeliveryInformation(deliveryMutableMap)
        }
        holder.addressInputText.onChange {
            deliveryMutableMap["address"] = it
            model.setUserDeliveryInformation(deliveryMutableMap)
        }
        holder.commentInputText.onChange {
            deliveryMutableMap["comment"] = it
            model.setUserDeliveryInformation(deliveryMutableMap)
        }
    }

    private fun EditText.onChange(cb: (String) -> Unit) {
        this.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) { cb(s.toString()) }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val nameInputText: TextInputEditText = itemView.nameInputText
        val phoneInputText: TextInputEditText = itemView.phoneInputText
        val cityInputText: TextInputEditText = itemView.cityInputText
        val addressInputText: TextInputEditText = itemView.addressInputText
        val commentInputText: TextInputEditText = itemView.commentInputText
    }
}