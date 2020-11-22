package com.marcuswkl.cnsplatform.profile.followlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.marcuswkl.cnsplatform.R
import com.squareup.picasso.Picasso

class FollowListAdapter(
    private val clubIds: MutableList<String>,
    private val clubLogos: MutableList<String>,
    private val clubNames: MutableList<String>
) :
    RecyclerView.Adapter<FollowListAdapter.PostViewHolder>() {

    // Reference to custom ViewHolder
    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val clubLogo: ImageView
        val clubName: TextView
        val unfollowButton: Button

        init {
            // Define ViewHolder views
            clubLogo = view.findViewById(R.id.follow_list_club_logo)
            clubName = view.findViewById(R.id.follow_list_club_name_title)
            unfollowButton = view.findViewById(R.id.follow_list_unfollow_button)
        }
    }

    // Creates ViewHolder and its associated View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        // Create View (list item) for ViewHolder
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.follow_list_club, parent, false)

        return PostViewHolder(view)
    }

    // Associates ViewHolder with data (replace view contents)
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        // Get data and replace view content
        Picasso.get().load(clubLogos[position]).into(holder.clubLogo)
        holder.clubName.text = clubNames[position]

        holder.unfollowButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                // Set Button behaviour
                val db = Firebase.firestore

                val user = Firebase.auth.currentUser
                val iMail = user?.email
                val studentId = iMail?.substringBefore("@")

                val followListRef =
                    db.collection("students").document("$studentId").collection("follow_list")

                followListRef.document(clubIds[position]).delete()

                val followString = "FOLLOW"
                holder.unfollowButton.text = followString

                setFollowListener(
                    holder.unfollowButton,
                    followListRef,
                    clubIds[position],
                    clubLogos[position],
                    clubNames[position]
                )

            }
        })

    }

    // Get the size of the data set
    override fun getItemCount() = clubIds.size

    private fun setFollowListener(
        unfollowButton: Button,
        followListRef: CollectionReference,
        clubId: String?,
        clubLogo: String,
        clubName: String
    ) {

        unfollowButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val unfollowString = "UNFOLLOW"
                unfollowButton.text = unfollowString

                val club = hashMapOf(
                    "name" to clubName,
                    "logo" to clubLogo
                )

                followListRef.document(clubId!!).set(club)
                setUnfollowListener(unfollowButton, followListRef, clubId, clubLogo, clubName)

            }
        })

    }

    private fun setUnfollowListener(
        unfollowButton: Button,
        followListRef: CollectionReference,
        clubId: String?,
        clubLogo: String,
        clubName: String
    ) {

        unfollowButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val followString = "FOLLOW"
                unfollowButton.text = followString

                followListRef.document(clubId!!).delete()
                setFollowListener(unfollowButton, followListRef, clubId, clubLogo, clubName)

            }
        })

    }

}