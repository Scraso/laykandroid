package com.example.tigran.laykandroid.shopFragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tigran.laykandroid.adapters.ProductCustomViewAdapter
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.TAG
import com.example.tigran.laykandroid.models.Product
import com.example.tigran.laykandroid.models.SharedViewModel
import com.example.tigran.laykandroid.services.DataService
import com.google.firebase.firestore.QuerySnapshot


class ProductListFragment: Fragment()  {
    var recyclerview: androidx.recyclerview.widget.RecyclerView? = null
    private var category: String? = null
    private val products = ArrayList<Product>()
    private val sharedViewModel = SharedViewModel()

    @SuppressLint("PrivateResource")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_product_list, container,false)

        recyclerview = view.findViewById(R.id.productListView)
        recyclerview?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        recyclerview?.adapter = ProductCustomViewAdapter(products, context!!)

        // Add divider
        val decoration = androidx.recyclerview.widget.DividerItemDecoration(
            context,
            androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
        )
        recyclerview?.addItemDecoration(decoration)

        // Retrieve the bundle that was pass from the previous fragment
        category = ProductListFragmentArgs.fromBundle(arguments).category
        activity?.title = category
        // Put it after bundle fetch so category variable will be filled with the category name
        // and passed to data query.

        sharedViewModel.category.value = category
//
        Log.d(TAG,"SharedViewModel for product list is ${sharedViewModel.category.value}")


//        fetchProductList()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val model = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        val productListLiveData = model.getProductListDataSnapshotLiveData()

        // Get News Data
        productListLiveData.observe(this, Observer<QuerySnapshot> { snapshot ->
            // Clear array to avoid duplicates
            products.clear()

            snapshot?.documents?.forEach { doc ->
                if (doc != null && doc.exists()) {
                    val product = doc.toObject(Product::class.java)
                    if (product != null) products += product
                    recyclerview?.adapter?.notifyDataSetChanged()
                }
            }
        })

    }

    private fun fetchProductList() {
        DataService.REF_ITEMS.whereEqualTo("category", category).whereEqualTo("isAvailable", true)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.w(TAG, "Something went wrong, fetching categories failed $error")

                }

                // Clear array to avoid duplicates when back to this Fragment
                products.clear()

                snapshot?.documents?.forEach { doc ->
                    if (doc != null && doc.exists()) {
                        val product = doc.toObject(Product::class.java)
                        if (product != null) products += product
                    }
                }
                // Reload RecyclerView
                recyclerview?.adapter?.notifyDataSetChanged()
            }
    }



}
