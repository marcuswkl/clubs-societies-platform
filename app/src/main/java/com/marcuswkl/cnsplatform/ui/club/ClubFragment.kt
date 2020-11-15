package com.marcuswkl.cnsplatform.ui.club

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.marcuswkl.cnsplatform.R
import com.marcuswkl.cnsplatform.ui.enquire.EnquireFragment
import kotlinx.android.synthetic.main.fragment_club.view.*

class ClubFragment : Fragment() {

    private lateinit var clubViewModel: ClubViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        clubViewModel =
            ViewModelProvider(this).get(ClubViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_club, container, false)

        val db = Firebase.firestore

        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.setFragmentResultListener(
            "clubName", this, { key, bundle ->

                val clubName = bundle.getString("name")

                val clubRef = db.collection("clubs").document("$clubName")
                clubRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {

                            root.club_name_title.text = document.getString("name")
                            root.club_category.text = document.getString("category")
                            root.club_info.text = document.getString("info")
                            root.club_past_events.text = document.getString("past_events")
                            root.club_advisor.text = document.getString("advisor")
                            root.club_membership_info.text = document.getDouble("membership_fee")
                                ?.toInt()
                                .toString()
                            root.club_email.text = document.getString("email")
                            root.club_tags.text = document.getString("tags")

                        } else {
                            Toast.makeText(activity, "Document Does Not Exist.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(activity, "Read Failed. $exception", Toast.LENGTH_SHORT).show()
                    }

        })

        root.club_enquire_button.setOnClickListener {
            val enquireFragment = EnquireFragment()
            val fragmentTransaction = fragmentManager?.beginTransaction()
            if (fragmentTransaction != null) {
                fragmentTransaction.replace(R.id.club_fragment, enquireFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        return root
    }

    private fun listToString(list: List<*>): String {
        val listText = StringBuilder()
        list.forEach {listItem ->
            listText.appendLine(listItem)
        }
        return listText.toString()
    }

}
