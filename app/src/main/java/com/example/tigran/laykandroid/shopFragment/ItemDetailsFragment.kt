package com.example.tigran.laykandroid.shopFragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.TAG
import com.example.tigran.laykandroid.adapters.ImagePageAdapter
import com.example.tigran.laykandroid.models.CartItem
import com.example.tigran.laykandroid.models.SharedViewModel
import com.example.tigran.laykandroid.models.Product
import kotlinx.android.synthetic.main.fragment_item_details.*
import java.nio.channels.Selector

class ItemDetailsFragment : Fragment(), View.OnTouchListener {

    private var product = Product()
    val buttons = ArrayList<Button>()
    private var selectedBtnSizeTitle: String? = ""

    private lateinit var itemSelector: Selector

    private lateinit var model: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_details, container, false)

        val bundle = this.arguments
        if (bundle != null) {
            product = bundle.getSerializable("product") as Product

        }

        // Set title based on the item name
        activity?.title = product.name

        // Create adapter for ImagePage
        val viewPager = view.findViewById<ViewPager>(R.id.imagePager)
        val adapter = ImagePageAdapter(this.context, product.images)
        viewPager.adapter = adapter

        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Set UI
        itemName.text = product.name.toUpperCase()
        itemPrice.text = "${product.price} грн"
        itemDetails_1.text = product.itemDetails[0]
        itemDetails_2.text = product.itemDetails[1]
        itemDetails_3.text = product.itemDetails[2]

        // Get the max value of the available size
        val maxValue = product.size.maxBy { it.value }

        // Create array of buttons

        buttons.add(XSBtn)
        buttons.add(SBtn)
        buttons.add(MBtn)
        buttons.add(LBtn)
        buttons.add(XLBtn)

        // Set default button with the max value is there is no value is greater the 0,
        // set cart button to disable state
        for (button in buttons) {
            if (button.text == maxValue?.key) {
                if (button.isEnabled && maxValue?.value!! > 0) {
                    button.isPressed = true
                    button.setTextColor(Color.WHITE)
                } else {
                    addToCartBtn.isEnabled = false
                    addToCartBtn.alpha = 0.5f
                }

            }
        }

        // Register buttons on onClickListener event
        SBtn.setOnTouchListener(this)
        XSBtn.setOnTouchListener(this)
        MBtn.setOnTouchListener(this)
        LBtn.setOnTouchListener(this)
        XLBtn.setOnTouchListener(this)

        // Check which size is available
        for (button in buttons) {
            for ((key, value) in product.size) {
                if (button.text == key) {
                    if (value > 0) {
                        buttonStatus(button, true)
                    } else {
                        buttonStatus(button, false)
                    }
                }
            }
        }

        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        // Handle Add to Cart Btn Press
        addToCartBtn.setOnClickListener {

            // Get the title of the selected item
            for (button in buttons) {
                if (button.isPressed) {
                    selectedBtnSizeTitle = button.text.toString()
                }
            }

            val item = CartItem(
                product.price,
                product.name,
                "${randomString(3)}${(1..999).shuffled().last()}",
                selectedBtnSizeTitle!!,
                1,
                "",
                product.avatarImageUrl
            )

            model.select(item)

            Toast.makeText(
                activity?.baseContext, "Товар добавлен в корзину.",
                Toast.LENGTH_SHORT
            ).show()

        }


    }

    // Generate random characters based on the gives size
    private fun randomString(size: Int): String {
        val source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        return (source).map { it }.shuffled().subList(0, size).joinToString("").toUpperCase()
    }

    private fun buttonStatus(button: Button, isEnabled: Boolean) {
        button.isEnabled = isEnabled
        val enableColor = Color.parseColor("#FF4A90E2")
        val disableColor = Color.parseColor("#FFE8E8E8")
        if (button.isEnabled && !button.isPressed) {
            button.setTextColor(enableColor)
        } else {
            button.setTextColor(disableColor)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        for (button in buttons) {
            if (button.id == v?.id) {
                if (event?.action == MotionEvent.ACTION_DOWN) {
                    button.isPressed = true
                    button.setTextColor(Color.WHITE)
                } else {
                    if (button.isEnabled) {
                        button.isPressed = false
                        Log.d(TAG, "Button background is ${button.id}")
                        button.setTextColor(Color.parseColor("#007aff"))
                    }
                }
            }
        }
        return true
    }


}