package com.marcuswkl.cnsplatform.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marcuswkl.cnsplatform.R

class SearchAdapter(private val searchResults: Array<String>) :
        RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    // Reference to custom ViewHolder
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.textView)
        }
    }

    // Creates ViewHolder and its associated View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create View (list item) for ViewHolder
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout_text_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Associates ViewHolder with data (replace view contents)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get data and replace view content
        viewHolder.textView.text = searchResults[position]
    }

    // Get the size of the data set
    override fun getItemCount() = searchResults.size

}