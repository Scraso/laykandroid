package com.example.tigran.laykandroid.adapters

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.TAG
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.delivery_details_item.view.*



class DeliveryCustomViewAdapter(private val menu: Menu?): androidx.recyclerview.widget.RecyclerView.Adapter<DeliveryCustomViewAdapter.ViewHolder>() {

    // Extension for edit Text setText
    var EditText.value
        get() = this.text.toString()
        set(value) {
            this.setText(value)
        }

    private val editTextArray = ArrayList<EditText>()

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
        // Remove refresh the list to prevent data loose
        holder.setIsRecyclable(false)

        editTextArray.add(holder.nameInputText)
        editTextArray.add(holder.phoneInputText)
        editTextArray.add(holder.cityInputText)
        editTextArray.add(holder.addressInputText)

        holder.nameInputText.onChange {
            deliveryMutableMap["name"] = it
        }
        holder.phoneInputText.onChange {
            deliveryMutableMap["phone"] = it
        }
        holder.cityInputText.onChange {
            deliveryMutableMap["city"] = it
        }
        holder.addressInputText.onChange {
            deliveryMutableMap["address"] = it
        }
        holder.commentInputText.onChange {
            deliveryMutableMap["comment"] = it
        }

    }

    private fun EditText.onChange(cb: (String) -> Unit) {
        this.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                cb(s.toString())
                if (menu == null) {
                    Log.d(TAG, "Menu is null")
                } else {
                    val doneAction = menu.findItem(R.id.action_done)
                    for (item in editTextArray) {
                        doneAction?.isVisible = item.value != ""
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
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