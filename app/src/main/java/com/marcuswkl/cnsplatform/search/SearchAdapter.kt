package com.marcuswkl.cnsplatform.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.marcuswkl.cnsplatform.R
import com.marcuswkl.cnsplatform.club.ClubFragment
import com.squareup.picasso.Picasso

class SearchAdapter(
    private val clubIds: MutableList<String>,
    private val clubLogos: MutableList<String>,
    private val clubNames: MutableList<String>
) :
    RecyclerView.Adapter<SearchAdapter.ResultViewHolder>() {

    // Reference to custom ViewHolder
    class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val clubLogo: ImageView
        val clubName: TextView
        val clubLayout: ConstraintLayout

        init {
            // Define click listener for the ViewHolder's View
            clubLogo = view.findViewById(R.id.search_result_club_logo)
            clubName = view.findViewById(R.id.search_result_club_name_title)
            clubLayout = view.findViewById(R.id.search_result_club_layout)
        }
    }

    // Creates ViewHolder and its associated View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        // Create View (list item) for ViewHolder
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_result_club, parent, false)

        return ResultViewHolder(view)
    }

    // Associates ViewHolder with data (replace view contents)
    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {

        // Get data and replace view content
        Picasso.get().load(clubLogos[position]).into(holder.clubLogo)
        holder.clubName.text = clubNames[position]

        holder.clubLayout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val activity = v?.context as AppCompatActivity
                val fragmentManager = activity.supportFragmentManager

                val clubId = clubIds[position]
                fragmentManager.setFragmentResult("resultClubId", bundleOf("clubId" to clubId))

                val clubFragment = ClubFragment()
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.search_fragment, clubFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()

            }
        })

    }

    // Get the size of the data set
    override fun getItemCount() = clubNames.size

}