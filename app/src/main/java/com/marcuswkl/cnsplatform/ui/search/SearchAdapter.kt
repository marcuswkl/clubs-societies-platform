package com.marcuswkl.cnsplatform.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marcuswkl.cnsplatform.R

class SearchAdapter(private val searchResults: MutableList<String>) :
        RecyclerView.Adapter<SearchAdapter.ResultViewHolder>() {

    // Reference to custom ViewHolder
    class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.textView)
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
        holder.textView.text = searchResults[position]
    }

    // Get the size of the data set
    override fun getItemCount() = searchResults.size

}