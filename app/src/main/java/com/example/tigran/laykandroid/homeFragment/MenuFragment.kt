package com.example.tigran.laykandroid.homeFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.tigran.laykandroid.adapters.NewsCustomViewAdapter
import com.example.tigran.laykandroid.adapters.TestimonialsCustomAdapter
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.TAG
import com.example.tigran.laykandroid.models.News
import com.example.tigran.laykandroid.models.SharedViewModel
import com.example.tigran.laykandroid.models.Testimonials
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_menu.*

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
        val newsLiveData = model.getNewsDataSnapshotLiveData()
        val testimonialsLiveData = model.getTestimonialsDataSnapshotLiveData()

        // Get News Data
        newsLiveData.observe(this, Observer<QuerySnapshot> { snapshot ->
            // Clear array to avoid duplicates
            newsList.clear()

            snapshot?.documents?.forEach { doc ->
                if (doc != null && doc.exists()) {
                    val news = doc.toObject(News::class.java)
                    if (news != null) newsList += news
                    newsRecyclerview?.adapter?.notifyDataSetChanged()
                }
            }
        })

        // Get Testimonials data
        testimonialsLiveData.observe(this, Observer<QuerySnapshot> { snapshot ->
            // Clear array to avoid dublicates
            testimonials.clear()

            snapshot?.documents?.forEach { doc ->
                if (doc != null && doc.exists()) {
                    val testimonial = doc.toObject(Testimonials::class.java)
                    if (testimonial != null) testimonials += testimonial
                    testimonialRecyclerView?.adapter?.notifyDataSetChanged()
                }
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        playBtn.setOnClickListener {
            Log.d(TAG,"Play button pressed")
            it.findNavController().navigate(R.id.nav_videoView)
        }
    }

}

