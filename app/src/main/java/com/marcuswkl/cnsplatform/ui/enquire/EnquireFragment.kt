package com.marcuswkl.cnsplatform.ui.enquire

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.marcuswkl.cnsplatform.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_enquire.view.*
import java.util.*

class EnquireFragment : Fragment() {

    private lateinit var enquireViewModel: EnquireViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        enquireViewModel =
            ViewModelProvider(this).get(EnquireViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_enquire, container, false)

        val db = Firebase.firestore

        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.setFragmentResultListener(
            "enquireClubName", this, { key, bundle ->

                val enquireClubName = bundle.getString("name")

                val clubRef = db.collection("clubs").document("$enquireClubName")
                clubRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {

                            Picasso.get().load(document.getString("logo")).into(root.enquire_club_logo)
                            root.enquire_club_name_title.text = document.getString("name")

                            root.enquire_submit_button.setOnClickListener {

                                val user = Firebase.auth.currentUser
                                val iMail = user?.email
                                val message = root.enquire_field.text.toString()

                                if (message.isEmpty()) {
                                    Toast.makeText(activity, "Invalid Message", Toast.LENGTH_SHORT).show()
                                } else {

                                    val enquiryData = hashMapOf(
                                        "imail" to iMail,
                                        "message" to message,
                                        "timestamp" to Timestamp(Date())
                                    )

                                    clubRef.collection("enquiries")
                                        .add(enquiryData)
                                        .addOnSuccessListener {
                                            Toast.makeText(activity, "Submission Success", Toast.LENGTH_SHORT).show()
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(activity, "Submission Fail", Toast.LENGTH_SHORT).show()
                                        }

                                }

                            }

                        } else {
                            Toast.makeText(activity, "Document Does Not Exist.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(activity, "Read Failed. $exception", Toast.LENGTH_SHORT).show()
                    }

            })

        return root
    }

}
