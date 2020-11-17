package com.marcuswkl.cnsplatform.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.marcuswkl.cnsplatform.MainActivity
import com.marcuswkl.cnsplatform.R
import com.marcuswkl.cnsplatform.Utils
import com.marcuswkl.cnsplatform.ui.search.leadership.LeadershipFragment
import kotlinx.android.synthetic.main.fragment_search.view.*
import java.util.*

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)
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

                        val query = root.search_field.text.toString().capitalize(Locale.ROOT)
                        val db = Firebase.firestore

                        val clubsRef = db.collection("clubs")
                        clubsRef.whereGreaterThanOrEqualTo("name", query)
                            .get()
                            .addOnSuccessListener { documents ->

                                val searchResults: MutableList<String> = mutableListOf()
                                val searchResultIds: MutableList<String> = mutableListOf()

                                for (document in documents) {
                                    document.getString("name")?.let { searchResults.add(it) }
                                    document.id.let { searchResultIds.add(it) }
                                }

                                val resultRecyclerView = root.result_recycler_view
                                linearLayoutManager = LinearLayoutManager(activity)
                                resultRecyclerView.layoutManager = linearLayoutManager

                                searchAdapter = SearchAdapter(searchResults, searchResultIds)
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
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        return root
    }
}
