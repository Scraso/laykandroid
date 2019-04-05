package com.example.tigran.laykandroid.homeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.adapters.NewsDetailsCustomViewAdapter
import com.example.tigran.laykandroid.models.NewsDetails
import com.example.tigran.laykandroid.models.SharedViewModel
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.home_news_item_details.*

class NewsDetailsFragment : Fragment() {

    private var avatarImageUrl: String = ""
    private var bodyText: String = ""
    private var documentId: String = ""

    private var newsDetailsList = ArrayList<NewsDetails>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.home_news_item_details, container, false)
        activity?.title = "Новости"

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        avatarImageUrl = NewsDetailsFragmentArgs.fromBundle(arguments).avatarImageUrl
        bodyText = NewsDetailsFragmentArgs.fromBundle(arguments).bodyText
        documentId = NewsDetailsFragmentArgs.fromBundle(arguments).documentId

        initView()

        Glide
            .with(context!!)
            .load(avatarImageUrl)
            .into(newsDetailsCoverImage)

        val model = ViewModelProviders.of(this).get(
            SharedViewModel::class.java)
        val newsDetailsLiveData = model.getNewsSectionParagraphs(documentId)

        // Get News Paragraph Data
        newsDetailsLiveData.observe(this, Observer<QuerySnapshot> { snapshot ->

            // Clear array to avoid duplicates
            newsDetailsList.clear()

            snapshot?.documents?.forEach { doc ->
                if (doc != null && doc.exists()) {
                    doc.toObject(NewsDetails::class.java)?.let {
                        newsDetailsList.add(it)
                        newsDetailsRecyclerView.adapter?.notifyDataSetChanged()
                    }

                }
            }
        })
    }

    private fun initView() {
        newsDetailsRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        newsDetailsRecyclerView.isNestedScrollingEnabled = false
        newsDetailsRecyclerView.adapter = NewsDetailsCustomViewAdapter(newsDetailsList, bodyText, context!!)
    }

}