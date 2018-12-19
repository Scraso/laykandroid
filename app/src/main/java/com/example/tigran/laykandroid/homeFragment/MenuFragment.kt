package com.example.tigran.laykandroid.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.tigran.laykandroid.adapters.NewsCustomViewAdapter
import com.example.tigran.laykandroid.adapters.TestimonialsCustomAdapter
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.R.id.testimonialRecyclerView
import com.example.tigran.laykandroid.TAG
import com.example.tigran.laykandroid.models.News
import com.example.tigran.laykandroid.models.SharedViewModel
import com.example.tigran.laykandroid.models.Testimonials
import com.example.tigran.laykandroid.services.DataService
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.auth.User
import org.w3c.dom.Document

class MenuFragment: Fragment() {

    private var newsRecyclerview: androidx.recyclerview.widget.RecyclerView? = null
    private var testimonialRecyclerView: androidx.recyclerview.widget.RecyclerView? = null
    private val newsList = ArrayList<News>()
    private val testimonials = ArrayList<Testimonials>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        activity?.title = "Меню"

        newsRecyclerview = view.findViewById(R.id.newsRecyclerView)
        newsRecyclerview?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            activity,
            androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
            false
        )
        newsRecyclerview?.adapter = NewsCustomViewAdapter(newsList, context!!)


        testimonialRecyclerView = view.findViewById(R.id.testimonialRecyclerView)
        testimonialRecyclerView?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            activity,
            androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
            false
        )
        testimonialRecyclerView?.adapter = TestimonialsCustomAdapter(testimonials, context!!)


        val model = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        val liveData = model.getDataSnapshotLiveData()

        liveData.observe(this, Observer<QuerySnapshot> { snapshot ->
            snapshot?.documents?.forEach { doc ->
                Log.d(TAG, "DOCUMENTS DATA IS ${doc.data}")
                if (doc != null && doc.exists()) {
                    val news = doc.toObject(News::class.java)
                    if (news != null) newsList += news
        //                        newsRecyclerview?.adapter?.notifyDataSetChanged()
                }
            }
        })



//        liveData.value?.documents?.forEach { doc ->
//            Log.d(TAG, "DATA FROM QUERY SNAPSHOT IS ${doc.data}")
//            if (doc != null && doc.exists()) {
//                val news = doc.toObject(News::class.java)
//                if (news != null) newsList += news
//                newsRecyclerview?.adapter?.notifyDataSetChanged()
//            }
//        }

        fetchTestimonialsData()

        return view
    }

//    private fun fetchNewsData() {
//        DataService.REF_NEWS_HEADER.whereEqualTo("isPublished", true).addSnapshotListener { snapshot, error ->
//            if (error != null) {
//                Log.w(TAG, "Something went wrong, fetching categories failed $error")
//            }
//
//            // Clear array to avoid dublicates
//            newsList.clear()
//
//            snapshot?.documents?.forEach { doc ->
//                if (doc != null && doc.exists()) {
//                    val news = doc.toObject(News::class.java)
//                    if (news != null) newsList += news
//                }
//            }
//            // Reload RecyclerView
//            newsRecyclerview?.adapter?.notifyDataSetChanged()
//        }
//    }

    private fun fetchTestimonialsData() {
        DataService.REF_TESTIMONIALS.whereEqualTo("isPublished", true).addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.w(TAG, "Something went wrong, fetching categories failed $error")
            }

            // Clear array to avoid dublicates
            testimonials.clear()

            snapshot?.documents?.forEach { doc ->
                if (doc != null && doc.exists()) {
                    val testimonial = doc.toObject(Testimonials::class.java)
                    if (testimonial != null) testimonials += testimonial
                }
            }
            // Reload RecyclerView
            testimonialRecyclerView?.adapter?.notifyDataSetChanged()
        }
    }




}

