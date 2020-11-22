package com.marcuswkl.cnsplatform.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.marcuswkl.cnsplatform.R
import com.marcuswkl.cnsplatform.event.EventFragment
import com.squareup.picasso.Picasso

class HomeAdapter(
    private val clubIds: MutableList<String>,
    private val postIds: MutableList<String>,
    private val postClubLogos: MutableList<String>,
    private val postClubNameTitles: MutableList<String>,
    private val postDates: MutableList<String>,
    private val postTypes: MutableList<String>,
    private val postTexts: MutableList<String>,
    private val postImages: MutableList<String>
) :
    RecyclerView.Adapter<HomeAdapter.PostViewHolder>() {

    // Reference to custom ViewHolder
    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val postClubLogo: ImageView
        val postClubNameTitle: TextView
        val postDate: TextView

        // Declare content post variables
        val postContentText: TextView
        val postContentImage: ImageView

        // Declare event post variables
        val postEventImage: ImageView
        val postEventText: TextView
        val postViewEventButton: Button
        val postEventLayout: ConstraintLayout

        init {
            // Define ViewHolder views
            postClubLogo = view.findViewById(R.id.post_club_logo)
            postClubNameTitle = view.findViewById(R.id.post_club_name_title)
            postDate = view.findViewById(R.id.post_date)

            // Assign content post variables
            postContentText = view.findViewById(R.id.post_content_text)
            postContentImage = view.findViewById(R.id.post_content_image)

            // Assign event post variables
            postEventImage = view.findViewById(R.id.post_event_image)
            postEventText = view.findViewById(R.id.post_event_text)
            postViewEventButton = view.findViewById(R.id.post_view_event_button)
            postEventLayout = view.findViewById(R.id.post_event_layout)
        }
    }

    // Creates ViewHolder and its associated View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        // Create View (list item) for ViewHolder
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_post, parent, false)

        return PostViewHolder(view)
    }

    // Associates ViewHolder with data (replace view contents)
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        // Get data and replace view content
        Picasso.get().load(postClubLogos[position]).into(holder.postClubLogo)
        holder.postClubNameTitle.text = postClubNameTitles[position]
        holder.postDate.text = postDates[position]

        // Check post type
        if (postTypes[position] == "content") {

            holder.postContentText.text = postTexts[position]
            if (postImages[position].isEmpty()) {
                holder.postContentImage.visibility = View.GONE
            } else {
                Picasso.get().load(postImages[position]).into(holder.postContentImage)
            }

        }
        else {

            holder.postContentText.visibility = View.GONE
            if (holder.postContentImage.visibility == View.VISIBLE) {
                holder.postContentImage.visibility = View.GONE
            }

            Picasso.get().load(postImages[position]).into(holder.postEventImage)
            holder.postEventText.text = postTexts[position]
            holder.postViewEventButton.setOnClickListener(object: View.OnClickListener {
                override fun onClick(v: View?) {

                    val activity = v?.context as AppCompatActivity
                    val fragmentManager = activity.supportFragmentManager

                    val postClubId = clubIds[position]
                    val postEventId = postIds[position]
                    fragmentManager.setFragmentResult("postClubEventIds", bundleOf("clubId" to postClubId, "eventId" to postEventId))

                    val eventFragment = EventFragment()
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.home_fragment, eventFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()

                }
            })

            holder.postEventLayout.visibility = View.VISIBLE

        }

    }

    // Get the size of the data set
    override fun getItemCount() = postIds.size

}