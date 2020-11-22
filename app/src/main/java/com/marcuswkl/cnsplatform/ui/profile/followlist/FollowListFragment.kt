package com.marcuswkl.cnsplatform.ui.profile.followlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.marcuswkl.cnsplatform.R
import kotlinx.android.synthetic.main.fragment_follow_list.view.*

class FollowListFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var followListAdapter: FollowListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_follow_list, container, false)

        val db = Firebase.firestore

        val user = Firebase.auth.currentUser
        val iMail = user?.email
        val studentId = iMail?.substringBefore("@")

        val userRef = db.collection("students").document("$studentId").collection("follow_list")
        userRef.get()
            .addOnSuccessListener { querySnapshot ->

                val clubIds: MutableList<String> = mutableListOf()
                val clubLogos: MutableList<String> = mutableListOf()
                val clubNames: MutableList<String> = mutableListOf()

                for (queryDocumentSnapshot in querySnapshot) {

                    queryDocumentSnapshot.id.let { clubIds.add(it) }
                    queryDocumentSnapshot.getString("logo")?.let { clubLogos.add(it) }
                    queryDocumentSnapshot.getString("name")?.let { clubNames.add(it) }

                }

                val followListRecyclerView = root.follow_list_recycler_view
                linearLayoutManager = LinearLayoutManager(activity)
                followListRecyclerView.layoutManager = linearLayoutManager

                followListAdapter = FollowListAdapter(clubIds, clubLogos, clubNames)
                followListRecyclerView.adapter = followListAdapter

            }
            .addOnFailureListener {
                Toast.makeText(activity, "Read Failed", Toast.LENGTH_SHORT).show()
            }

        return root
    }

}
