package com.example.tigran.laykandroid.models

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*

class FirebaseQueryLiveData(private var query: Query) : LiveData<QuerySnapshot>() {

    companion object {
        const val LOG_TAG = "FirebaseQueryLiveData"
    }
    private lateinit var documentReference: DocumentReference
    private val listener = MyValueEventListener()
    private lateinit var listenerRegistration: ListenerRegistration

    override fun onActive() {
        super.onActive()
        Log.d(LOG_TAG, "onActive")
        listenerRegistration = query.addSnapshotListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        Log.d(LOG_TAG, "onInactive: ")
        listenerRegistration.remove()
    }

    class MyValueEventListener : EventListener<QuerySnapshot> {
        override fun onEvent(querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {
           if (e != null) {
               Log.e(LOG_TAG, "Can't listen to query snapshots: " + querySnapshot + ":::" + e.localizedMessage)
               return
           }

            setOf(querySnapshot)

//            querySnapshot?.documents?.forEach { doc ->
//                if (doc != null && doc.exists()) {
//                    setOf(doc)
//                }
//            }
        }

//        override fun onEvent(documentSnapshot: DocumentSnapshot?, e: FirebaseFirestoreException?) {
//            if (e != null) {
//                Log.e(LOG_TAG, "Can't listen to doc snapshots: " + documentSnapshot + ":::" + e.localizedMessage)
//                return
//            }
//            setOf(documentSnapshot)
//        }
    }

}
