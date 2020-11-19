package com.marcuswkl.cnsplatform.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.marcuswkl.cnsplatform.R
import com.squareup.picasso.Picasso

class HomeAdapter(
    private val postIds: MutableList<String>,
    private val postClubLogos: MutableList<String>,
    private val postClubNameTitles: MutableList<String>,
    private val postDates: MutableList<String>,
    private val postContentTexts: MutableList<String>,
    private val postContentImages: MutableList<String>) :
    RecyclerView.Adapter<HomeAdapter.PostViewHolder>() {

    // Reference to custom ViewHolder
    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val postClubLogo: ImageView
        val postClubNameTitle: TextView
        val postDate: TextView
        val postContentText: TextView
        val postContentImage: ImageView

        init {
            // Define ViewHolder views
            postClubLogo = view.findViewById(R.id.post_club_logo)
            postClubNameTitle = view.findViewById(R.id.post_club_name_title)
            postDate = view.findViewById(R.id.post_date)
            postContentText = view.findViewById(R.id.post_content_text)
            postContentImage = view.findViewById(R.id.post_content_image)
        }
    }

    // Creates ViewHolder and its associated View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        // Create View (list item) for ViewHolder
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_content_post, parent, false)

        return PostViewHolder(view)
    }

    // Associates ViewHolder with data (replace view contents)
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        // Get data and replace view content
        Picasso.get().load(postClubLogos[position]).into(holder.postClubLogo)
        holder.postClubNameTitle.text = postClubNameTitles[position]
        holder.postDate.text = postDates[position]
        holder.postContentText.text = postContentTexts[position]
        if (postContentImages[position].isEmpty()) {
            holder.postContentImage.visibility = View.GONE
        } else {
            holder.postContentText.marginBottom
            Picasso.get().load(postContentImages[position]).into(holder.postContentImage)
        }
    }

    // Get the size of the data set
    override fun getItemCount() = postIds.size

}