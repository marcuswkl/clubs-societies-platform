package com.marcuswkl.cnsplatform.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.marcuswkl.cnsplatform.R
import com.marcuswkl.cnsplatform.ui.club.ClubFragment

class SearchAdapter(private val searchResults: MutableList<String>, private val searchResultIds: MutableList<String>) :
        RecyclerView.Adapter<SearchAdapter.ResultViewHolder>() {

    // Reference to custom ViewHolder
    class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val resultTextView: TextView

        init {
            // Define click listener for the ViewHolder's View
            resultTextView = view.findViewById(R.id.search_result)
        }
    }

    // Creates ViewHolder and its associated View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        // Create View (list item) for ViewHolder
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_item, parent, false)

        return ResultViewHolder(view)
    }

    // Associates ViewHolder with data (replace view contents)
    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        // Get data and replace view content
        holder.resultTextView.text = searchResults[position]
        holder.resultTextView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {

                val activity = v?.context as AppCompatActivity
                val fragmentManager = activity.supportFragmentManager

                val resultId = searchResultIds[position]
                fragmentManager.setFragmentResult("resultClubId", bundleOf("id" to resultId))

                val clubFragment = ClubFragment()
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.search_fragment, clubFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()

            }
        })
    }

    // Get the size of the data set
    override fun getItemCount() = searchResults.size

}