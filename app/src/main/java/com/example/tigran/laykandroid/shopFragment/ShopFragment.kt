package com.example.tigran.laykandroid.shopFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.adapters.CategoryCustomViewAdapter
import android.util.Log
import android.view.View
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.example.tigran.laykandroid.TAG
import com.example.tigran.laykandroid.models.Category
import com.example.tigran.laykandroid.services.DataService


class ShopFragment : Fragment() {

    var recyclerview: androidx.recyclerview.widget.RecyclerView? = null
    private val categories = ArrayList<Category>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_shop, container, false)
        activity?.title = "Магазин"
        fetchShopCategory()
        recyclerview = view.findViewById(R.id.categoryListView)
        recyclerview?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        recyclerview?.adapter =
                CategoryCustomViewAdapter(categories, this.activity)

        // Add divider
        val decoration = androidx.recyclerview.widget.DividerItemDecoration(
            context,
            androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
        )
        recyclerview?.addItemDecoration(decoration)

        return view

    }

    private fun fetchShopCategory() {
        DataService.REF_SHOP_CATEGORY.whereEqualTo("isEnabled", true).addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.d(TAG, "Something went wrong, fetching categories failed $error")
            }

            // Clear array to avoid duplicates when back to this Fragment
            categories.clear()

            snapshot?.documents?.forEach { doc ->
                if (doc != null && doc.exists()) {
                    val category = doc.toObject(Category::class.java)
                    if (category != null) categories += category
                }
            }
            // Reload RecyclerView
            recyclerview?.adapter?.notifyDataSetChanged()

        }
    }

}
