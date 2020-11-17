package com.marcuswkl.cnsplatform.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.marcuswkl.cnsplatform.R
import com.marcuswkl.cnsplatform.ui.search.leadership.LeadershipFragment
import kotlinx.android.synthetic.main.fragment_search.view.*
import java.util.*

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)

        root.search_field.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {

                if (event != null) {
                    // When Enter key is pressed
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                        // Perform action

                        val query = root.search_field.text.toString().capitalize(Locale.ROOT)
                        val db = Firebase.firestore

                        val clubsRef = db.collection("clubs")
                        clubsRef.whereGreaterThanOrEqualTo("name", query)
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    Toast.makeText(activity, document.getString("name"), Toast.LENGTH_SHORT).show()
                                }
                            }
                            .addOnFailureListener { exception ->
                                Toast.makeText(activity, "Error Getting Documents", Toast.LENGTH_SHORT).show()
                            }

                        return true

                    }
                }

                return false

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
