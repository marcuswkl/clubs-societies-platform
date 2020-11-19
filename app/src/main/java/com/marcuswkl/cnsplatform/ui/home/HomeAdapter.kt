package com.marcuswkl.cnsplatform.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.marcuswkl.cnsplatform.R
import com.marcuswkl.cnsplatform.ui.club.ClubFragment
import com.squareup.picasso.Picasso
import java.util.*

class HomeAdapter(
    private val postIds: MutableList<String>,
    private val postClubLogos: MutableList<String>,
    private val postClubNameTitles: MutableList<String>,
    private val postDates: MutableList<String>,
    private val postTextContents: MutableList<String>) :
    RecyclerView.Adapter<HomeAdapter.PostViewHolder>() {

    // Reference to custom ViewHolder
    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val postClubLogo: ImageView
        val postClubNameTitle: TextView
        val postDate: TextView
        val postTextContent: TextView

        init {
            // Define ViewHolder views
            postClubLogo = view.findViewById(R.id.post_club_logo)
            postClubNameTitle = view.findViewById(R.id.post_club_name_title)
            postDate = view.findViewById(R.id.post_date)
            postTextContent = view.findViewById(R.id.post_text_content)
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
        Picasso.get().load(postClubLogos[position]).into(holder.postClubLogo);
        holder.postClubNameTitle.text = postClubNameTitles[position]
        holder.postDate.text = postDates[position].toString()
        holder.postTextContent.text = postTextContents[position]
    }

    // Get the size of the data set
    override fun getItemCount() = postIds.size

}