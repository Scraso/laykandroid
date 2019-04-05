package com.example.tigran.laykandroid.informationFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tigran.laykandroid.R
import com.example.tigran.laykandroid.adapters.InformationCustomViewAdapter

class InformationFragment: Fragment() {

    private var informationList = mutableListOf<String>("О магазине", "Доставка и оплата", "Обмен и возврат", "Сотрудничество")
    var recyclerView: androidx.recyclerview.widget.RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_information, container, false)
        activity?.title = "Информация"

//        addInformationItem()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.informationRecyclerView)
        recyclerView?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        recyclerView?.adapter = InformationCustomViewAdapter(informationList)

        // Add divider
        val decoration = androidx.recyclerview.widget.DividerItemDecoration(
            context,
            androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
        )
        recyclerView?.addItemDecoration(decoration)
    }

    private fun addInformationItem() {
        informationList.add("О магазине")
        informationList.add("Доставка и оплата")
        informationList.add("Обмен и возврат")
        informationList.add("Сотрудничество")

    }

}