package com.marcuswkl.cnsplatform.ui.profile.followlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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

            }
        })

    }

    // Get the size of the data set
    override fun getItemCount() = clubIds.size

}