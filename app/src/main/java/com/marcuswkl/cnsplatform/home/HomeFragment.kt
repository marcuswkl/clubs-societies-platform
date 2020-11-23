package com.marcuswkl.cnsplatform.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.marcuswkl.cnsplatform.R
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.DateFormat

class HomeFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val db = Firebase.firestore

        val postsRef = db.collectionGroup("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .orderBy("name", Query.Direction.ASCENDING)

        postsRef.get()
            .addOnSuccessListener { querySnapshot ->

                val clubIds: MutableList<String> = mutableListOf()
                val postIds: MutableList<String> = mutableListOf()
                val postClubLogos: MutableList<String> = mutableListOf()
                val postClubNameTitles: MutableList<String> = mutableListOf()
                val postDates: MutableList<String> = mutableListOf()

                val postTypes: MutableList<String> = mutableListOf()
                val postTexts: MutableList<String> = mutableListOf()
                val postImages: MutableList<String> = mutableListOf()

                val dateFormat = DateFormat.getDateInstance()

                for (queryDocumentSnapshot in querySnapshot) {

                    queryDocumentSnapshot.getString("club_id")?.let { clubIds.add(it) }
                    queryDocumentSnapshot.id.let { postIds.add(it) }
                    queryDocumentSnapshot.getString("logo")?.let { postClubLogos.add(it) }
                    queryDocumentSnapshot.getString("name")?.let { postClubNameTitles.add(it) }

                    val dateData = queryDocumentSnapshot.getTimestamp("timestamp")?.toDate()
                    postDates.add(dateFormat.format(dateData))

                    val postType = queryDocumentSnapshot.getString("type")
                    postType?.let { postTypes.add(it) }

                    // Check if post type is content
                    if (postType.equals("content")) {
                        queryDocumentSnapshot.getString("text")?.let { postTexts.add(it) }
                    }
                    // Post type is event
                    else {
                        val textList = queryDocumentSnapshot.get("text") as List<*>
                        postTexts.add(listToString(textList))
                    }

                    queryDocumentSnapshot.getString("image")?.let { postImages.add(it) }

                }

                val postsRecyclerView = root.posts_recycler_view
                linearLayoutManager = LinearLayoutManager(activity)
                postsRecyclerView.layoutManager = linearLayoutManager

                homeAdapter = HomeAdapter(
                    clubIds,
                    postIds,
                    postClubLogos,
                    postClubNameTitles,
                    postDates,
                    postTypes,
                    postTexts,
                    postImages
                )
                postsRecyclerView.adapter = homeAdapter

            }

        return root
    }

    // Convert retrieved Lists from db to strings for TextView display
    private fun listToString(list: List<*>): String {
        val listText = StringBuilder()
        list.forEach {listItem ->
            listText.appendLine(listItem)
        }
        return listText.toString()
    }

}