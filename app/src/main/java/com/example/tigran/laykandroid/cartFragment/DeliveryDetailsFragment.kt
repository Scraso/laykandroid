package com.example.tigran.laykandroid.cartFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.TAG
import com.example.tigran.laykandroid.models.CartItem
import com.example.tigran.laykandroid.services.DataService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_delivery_details.*
import java.util.*

class DeliveryDetailsFragment: Fragment(), OnClickListenerInterface {

    override fun dismissClicked() {
        Log.d(TAG, "Button from Dialog fragment was pressed")
    }


    private var menu: Menu? = null
    private val editTextArray = ArrayList<EditText>()
    private val deliveryMutableMap = mutableMapOf<String, Any>()
    private lateinit var auth: FirebaseAuth
    private var itemSizeCount: Number? = null


    // Items from the cart
    private var cartItems = mutableListOf<CartItem>()

    // Extension for edit Text setText
    var EditText.value
        get() = this.text.toString()
        set(value) {
            this.setText(value)
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_delivery_details, container, false)
        activity?.title = "Доставка"
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val bundle = this.arguments
        if (bundle != null) {
            val testArray = bundle.getSerializable("cartItemsArrayList") as Array<CartItem>
            cartItems = testArray.toMutableList()
        }

        // Call this option to re-draw Option Menu
        // Once it's called, onCreateOptionMenu will be called with the new parameters
        setHasOptionsMenu(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        editTextArray.add(nameInputText)
        editTextArray.add(phoneInputText)
        editTextArray.add(cityInputText)
        editTextArray.add(addressInputText)

        nameInputText.onChange {
            deliveryMutableMap["name"] = it
        }
        phoneInputText.onChange {
            deliveryMutableMap["phone"] = it
        }
        cityInputText.onChange {
            deliveryMutableMap["city"] = it
        }
        addressInputText.onChange {
            deliveryMutableMap["address"] = it
        }
        commentInputText.onChange {
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
                    val doneAction = menu?.findItem(R.id.action_done)
                    for (item in editTextArray) {
                        doneAction?.isVisible = item.value != ""
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val cartItem = menu.findItem(R.id.action_cart)
        this.menu = menu
        cartItem.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_done -> {

                val currentUserUid = auth.currentUser?.uid
                val timestamp = Date().apply { java.lang.String.format("dd.MM.yyyy HH:mm") }
                val itemCount = cartItems.size
                var uploadCount = 0

                for (cartItem in cartItems) {
                    val documentId = DataService.REF_ORDERS.document()
                    val orderDetails = HashMap<String, Any>()
                    orderDetails["name"] = cartItem.name
                    orderDetails["size"] = cartItem.size
                    orderDetails["price"] = cartItem.price
                    orderDetails["itemDocumentId"] = cartItem.documentId
                    orderDetails["ref"] = cartItem.ref
                    orderDetails["count"] = cartItem.count
                    orderDetails["userId"] = currentUserUid!!
                    orderDetails["avatarImageUrl"] = cartItem.avatarImageUrl
                    orderDetails["timestamp"] = timestamp
                    orderDetails["orderId"] = documentId.id
                    orderDetails["status"] = "none"

                    val orderStatus = HashMap<String, Any>()
                    orderStatus["isProcessed"] = false
                    orderStatus["isDelivered"] = false
                    orderStatus["isSent"] = false
                    orderStatus["orderId"] = documentId.id
                    orderStatus["userId"] = currentUserUid

                    // Write orderDetails to Firestore
                    documentId.set(orderDetails)

                    // Write delivery details to Firestore
                    DataService.REF_ORDER_DELIVERY_DETAILS.document().set(deliveryMutableMap)

                    // Reference to item document
                    val itemDocumentRef = DataService.REF_ITEMS.document(cartItem.documentId)

                    if (auth.currentUser != null) {

                        // Write order status only for the registered users and display it later in History Fragment
                        DataService.REF_ORDER_STATUS.document().set(orderStatus)

                        // Run transaction to update item count

                        FirebaseFirestore.getInstance().runTransaction { transaction ->

                            // Should be history and total count document update

                            val snapshot = transaction.get(itemDocumentRef)

                            val nestedData = snapshot.data?.get("size") as Map<String, Any>
                            Log.d(TAG, "Nested data is $nestedData")
                            val sizeCount = nestedData[cartItem.size] as Number
                            Log.d(TAG, "Size count is $sizeCount")
                            itemSizeCount = sizeCount

                            itemSizeCount?.let {
                                val newItemSizeCount = it.toInt() - cartItem.count
                                transaction.update(itemDocumentRef, "size.${cartItem.size}", newItemSizeCount)
                            }
                        }.addOnSuccessListener {
                            Log.d(TAG, "Transaction success!")

                            uploadCount += 1
                            if (uploadCount == itemCount) {
                                val pop = ProcessingFragment()
                                val fm = this@DeliveryDetailsFragment.fragmentManager
                                if (fm != null) {
                                    pop.show(fm, "name")
                                }
                            }

                        }.addOnFailureListener { e ->
                            Log.w(TAG, "Transaction failure.", e)
                        }
                    } else {

                        FirebaseFirestore.getInstance().runTransaction { transaction ->
                            val snapshot = transaction.get(itemDocumentRef)

                            val nestedData = snapshot.data?.get("size") as Map<String, Any>
                            Log.d(TAG, "Nested data is $nestedData")
                            val sizeCount = nestedData[cartItem.size] as Number
                            Log.d(TAG, "Size count is $sizeCount")
                            itemSizeCount = sizeCount

                            itemSizeCount?.let {
                                val newItemSizeCount = it.toInt() - cartItem.count
                                transaction.update(itemDocumentRef, "size.${cartItem.size}", newItemSizeCount)
                            }
                        }.addOnSuccessListener {
                            Log.d(TAG, "Transaction success!")

                            uploadCount += 1
                            if (uploadCount == itemCount) {

                            }

                        }.addOnFailureListener { e ->
                            Log.w(TAG, "Transaction failure.", e)
                        }
                    }
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}