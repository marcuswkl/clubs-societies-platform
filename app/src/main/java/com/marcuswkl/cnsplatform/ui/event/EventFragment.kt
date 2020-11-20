package com.marcuswkl.cnsplatform.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.marcuswkl.cnsplatform.R
import com.marcuswkl.cnsplatform.ui.enquire.EnquireFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_club.view.*
import kotlinx.android.synthetic.main.fragment_event.view.*

class EventFragment : Fragment() {

    private lateinit var eventViewModel: EventViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventViewModel =
            ViewModelProvider(this).get(EventViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_event, container, false)

        val db = Firebase.firestore

        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.setFragmentResultListener(
            "postClubEventIds", this, { key, bundle ->

                val clubId = bundle.getString("clubId")
                val eventId = bundle.getString("eventId")
                val eventRef = db.collection("clubs").document("$clubId")
                    .collection("events").document("$eventId")

                eventRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {

                            Picasso.get().load(document.getString("club_logo")).into(root.event_club_logo)
                            root.event_club_name_title.text = document.getString("club_name")
                            root.event_name_title.text = document.getString("event_name")
                            Picasso.get().load(document.getString("poster")).into(root.event_poster)
                            root.event_date.text = document.getString("date")
                            root.event_time.text = document.getString("time")
                            root.event_venue.text = document.getString("venue")

                            val memberPrice = document.getDouble("member_price")?.toInt()
                            val memberPriceText = getString(R.string.member_price_text, memberPrice)
                            val priceText = StringBuilder()
                            priceText.appendLine(memberPriceText)

                            val isMemberOnly = document.getBoolean("is_member_only")

                            if (!isMemberOnly!!) {
                                val nonMemberPrice = document.getDouble("non_member_price")?.toInt()
                                val nonMemberPriceText = getString(R.string.non_member_price_text, nonMemberPrice)
                                priceText.appendLine(nonMemberPriceText)
                            }

                            root.event_price.text = priceText.toString()

                            root.event_info.text = document.getString("info")

                            /*val user = Firebase.auth.currentUser
                            val iMail = user?.email
                            val studentId = iMail?.substringBefore("@")
                            val memberList = document.get("member_list") as List<*>

                            // Student is not a member
                            if (studentId !in memberList) {
                                setJoinListener(root.club_join_button, clubRef, studentId)
                            }
                            // Student is a member
                            else {
                                setLeaveListener(root.club_join_button, clubRef, studentId)
                            }*/

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

    /*private fun setJoinListener(clubJoinButton: Button, clubRef: DocumentReference, studentId: String?) {

        clubJoinButton.text = getString(R.string.join)

        clubJoinButton.club_join_button.setOnClickListener {

            clubRef.update("member_list", FieldValue.arrayUnion(studentId))
                .addOnSuccessListener {
                    Toast.makeText(activity, "Join Success", Toast.LENGTH_SHORT).show()
                    setLeaveListener(clubJoinButton, clubRef, studentId)
                }
                .addOnFailureListener {
                    Toast.makeText(activity, "Join Fail", Toast.LENGTH_SHORT).show()
                }

        }

    }

    private fun setLeaveListener(clubJoinButton: Button, clubRef: DocumentReference, studentId: String?) {

        clubJoinButton.text = getString(R.string.leave)

        clubJoinButton.setOnClickListener {

            clubRef.update("member_list", FieldValue.arrayRemove(studentId))
                .addOnSuccessListener {
                    Toast.makeText(activity, "Leave Success", Toast.LENGTH_SHORT).show()
                    setJoinListener(clubJoinButton, clubRef, studentId)
                }
                .addOnFailureListener {
                    Toast.makeText(activity, "Leave Fail", Toast.LENGTH_SHORT).show()
                }

        }

    }*/

}
