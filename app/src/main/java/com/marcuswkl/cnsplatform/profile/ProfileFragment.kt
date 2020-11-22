package com.marcuswkl.cnsplatform.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.marcuswkl.cnsplatform.R
import com.marcuswkl.cnsplatform.login.LoginActivity
import com.marcuswkl.cnsplatform.profile.followlist.FollowListFragment
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val db = Firebase.firestore

        auth = Firebase.auth

        val user = Firebase.auth.currentUser
        val iMail = user?.email
        val studentId = iMail?.substringBefore("@")

        val userRef = db.collection("students").document("$studentId")
        userRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    root.student_id_field.text = document.getString("student_id")
                    root.full_name_field.text = document.getString("full_name")

                    val gender = document.getString("gender")
                    if (gender.equals("male")) {
                        root.gender_radio_group.check(R.id.male_radio_button)
                    } else {
                        root.gender_radio_group.check(R.id.female_radio_button)
                    }

                    root.ic_number_field.text = document.getString("ic_number")
                    root.phone_number_field.text = document.getString("phone_number")
                    root.programme_field.text = document.getString("programme")
                    root.intake_field.text = document.getString("intake")

                } else {
                    // Toast.makeText(activity, "Document Does Not Exist", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                // Toast.makeText(activity, "$exception", Toast.LENGTH_SHORT).show()
            }

        root.follow_list_button.setOnClickListener {
            val fragmentManager = activity?.supportFragmentManager
            val followListFragment = FollowListFragment()
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.profile_fragment, followListFragment)
                ?.addToBackStack(null)
                ?.commit()
        }

        root.logout_button.setOnClickListener {
            logOut()
        }

        return root
    }

    private fun logOut() {
        auth.signOut()
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
