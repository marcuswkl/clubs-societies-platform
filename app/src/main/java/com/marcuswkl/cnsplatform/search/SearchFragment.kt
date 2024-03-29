package com.marcuswkl.cnsplatform.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.marcuswkl.cnsplatform.R
import com.marcuswkl.cnsplatform.Utils
import com.marcuswkl.cnsplatform.search.leadership.LeadershipFragment
import com.marcuswkl.cnsplatform.search.uniformaffiliate.UniformAffiliateFragment
import kotlinx.android.synthetic.main.fragment_search.view.*
import java.util.*

class SearchFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)

        val utils = Utils()

        root.search_field.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {

                if (event != null) {
                    // When Enter key is pressed
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                        activity?.let { utils.hideKeyboard(it) }
                        root.category_tiles_scrollview.visibility = View.INVISIBLE
                        root.result_recycler_view.visibility = View.VISIBLE

                        val query = root.search_field.text.toString().toLowerCase(Locale.ROOT)

                        val db = Firebase.firestore

                        val clubsRef = db.collection("clubs")

                        // Check if search_queries array of club documents contain user input query string
                        // Get matching club documents and display club information in search results
                        clubsRef.whereArrayContains("search_queries", query)
                            .get()
                            .addOnSuccessListener { querySnapshot ->

                                val clubIds: MutableList<String> = mutableListOf()
                                val clubLogos: MutableList<String> = mutableListOf()
                                val clubNames: MutableList<String> = mutableListOf()

                                for (queryDocumentSnapshot in querySnapshot) {
                                    queryDocumentSnapshot.id.let { clubIds.add(it) }
                                    queryDocumentSnapshot.getString("logo")
                                        ?.let { clubLogos.add(it) }
                                    queryDocumentSnapshot.getString("name")
                                        ?.let { clubNames.add(it) }
                                }

                                val resultRecyclerView = root.result_recycler_view
                                linearLayoutManager = LinearLayoutManager(activity)
                                resultRecyclerView.layoutManager = linearLayoutManager

                                searchAdapter = SearchAdapter(clubIds, clubLogos, clubNames)
                                resultRecyclerView.adapter = searchAdapter

                            }
                            .addOnFailureListener { exception ->
                                Toast.makeText(activity, "Search Failed", Toast.LENGTH_SHORT).show()
                            }

                        return true

                    }
                }

                return false

            }

        })

        // If search is empty, reset to original layout with category tiles
        root.search_field.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    activity?.let { utils.hideKeyboard(it) }
                    root.result_recycler_view.visibility = View.INVISIBLE
                    root.category_tiles_scrollview.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        root.leadership_tile.setOnClickListener {
            val leadershipFragment = LeadershipFragment()
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            if (fragmentTransaction != null) {
                fragmentTransaction.replace(R.id.search_fragment, leadershipFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        root.uniform_affiliate_tile.setOnClickListener {
            val uniformAffiliateFragment = UniformAffiliateFragment()
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            if (fragmentTransaction != null) {
                fragmentTransaction.replace(R.id.search_fragment, uniformAffiliateFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        return root
    }
}
