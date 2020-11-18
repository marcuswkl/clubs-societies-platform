package com.marcuswkl.cnsplatform.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.marcuswkl.cnsplatform.R
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var homeAdapter: HomeAdapter

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val db = Firebase.firestore

        val postsRef = db.collection("clubs").document("sunwayathleticsclub").collection("posts")
        postsRef.get()
            .addOnSuccessListener { querySnapshot ->

                val postClubLogos: MutableList<String> = mutableListOf()
                val postClubNameTitles: MutableList<String> = mutableListOf()
                val postIds: MutableList<String> = mutableListOf()
                val postDates: MutableList<Date> = mutableListOf()
                val postTextContents: MutableList<String> = mutableListOf()

                for (queryDocumentSnapshot in querySnapshot) {

                    queryDocumentSnapshot.id.let { postIds.add(it) }
                    queryDocumentSnapshot.getString("logo")?.let { postClubLogos.add(it) }
                    queryDocumentSnapshot.getString("name")?.let { postClubNameTitles.add(it) }
                    queryDocumentSnapshot.getTimestamp("timestamp")?.toDate().let { postDates.add(it!!) }
                    queryDocumentSnapshot.getString("text")?.let { postTextContents.add(it) }

                }

                val postsRecyclerView = root.posts_recycler_view
                linearLayoutManager = LinearLayoutManager(activity)
                postsRecyclerView.layoutManager = linearLayoutManager

                homeAdapter = HomeAdapter(
                    postIds,
                    postClubLogos,
                    postClubNameTitles,
                    postDates,
                    postTextContents
                )
                postsRecyclerView.adapter = homeAdapter

            }

        return root
    }
}
