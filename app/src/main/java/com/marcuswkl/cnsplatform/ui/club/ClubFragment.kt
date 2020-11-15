package com.marcuswkl.cnsplatform.ui.club

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

        val clubRef = db.collection("clubs").document("sunwayuniversitystudentcouncil")
        clubRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Toast.makeText(activity, "Read Document Successful.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Document Does Not Exist.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(activity, "Read Failed. $exception", Toast.LENGTH_SHORT).show()
            }

        root.club_enquire_button.setOnClickListener {
            val enquireFragment = EnquireFragment()
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            if (fragmentTransaction != null) {
                fragmentTransaction.replace(R.id.club_fragment, enquireFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        return root
    }

}
