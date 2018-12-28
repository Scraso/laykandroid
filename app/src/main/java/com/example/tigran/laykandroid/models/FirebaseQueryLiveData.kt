package com.example.tigran.laykandroid.models

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*

class FirebaseQueryLiveData (private var query: Query) : LiveData<QuerySnapshot>() {

    companion object {
        const val LOG_TAG = "FirebaseQueryLiveData"
    }
    private val listener = MyValueEventListener()
    private lateinit var listenerRegistration: ListenerRegistration

    override fun onActive() {
        super.onActive()
        listenerRegistration = query.addSnapshotListener(listener)

    }

    override fun onInactive() {
        super.onInactive()
        listenerRegistration.remove()

    }

    inner class MyValueEventListener : EventListener<QuerySnapshot> {
        override fun onEvent(querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {
           if (e != null) {
               Log.e(LOG_TAG, "Can't listen to query snapshots: " + querySnapshot + ":::" + e.localizedMessage)
               return
           }
            // Set querySnapshot to LiveData
            value = querySnapshot

        }
    }

}
