package com.example.tigran.laykandroid.shopFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.adapters.CategoryCustomViewAdapter
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tigran.laykandroid.models.Category
import com.example.tigran.laykandroid.models.SharedViewModel
import com.google.firebase.firestore.QuerySnapshot


class ShopFragment : Fragment() {

    var recyclerView: androidx.recyclerview.widget.RecyclerView? = null
    private val categories = ArrayList<Category>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_shop, container, false)
        activity?.title = "Магазин"
//        fetchShopCategory()
        recyclerView = view.findViewById(R.id.categoryListView)
        recyclerView?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        recyclerView?.adapter =
                CategoryCustomViewAdapter(categories)

        // Add divider
        val decoration = androidx.recyclerview.widget.DividerItemDecoration(
            context,
            androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
        )
        recyclerView?.addItemDecoration(decoration)


        val model = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        val categoryLiveData = model.getShopCategorySnapshotLiveData()

        // Get News Data
        categoryLiveData.observe(this, Observer<QuerySnapshot> { snapshot ->
            // Clear array to avoid duplicates
            categories.clear()

            snapshot?.documents?.forEach { doc ->
                if (doc != null && doc.exists()) {
                    val category = doc.toObject(Category::class.java)
                    if (category != null) categories += category
                    recyclerView?.adapter?.notifyDataSetChanged()
                }
            }
        })

        return view

    }

}
