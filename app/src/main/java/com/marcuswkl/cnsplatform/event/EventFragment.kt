package com.marcuswkl.cnsplatform.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.marcuswkl.cnsplatform.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_event.view.*

class EventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

                            val memberPriceText = if (memberPrice == 0) {
                                getString(R.string.member_price_free_text)
                            } else {
                                getString(R.string.member_price_text, memberPrice)
                            }

                            val priceText = StringBuilder()
                            priceText.appendLine(memberPriceText)

                            val isMemberOnly = document.getBoolean("is_member_only")

                            // Check if event open to non-members
                            if (!isMemberOnly!!) {
                                val nonMemberPrice = document.getDouble("non_member_price")?.toInt()

                                val nonMemberPriceText = if (nonMemberPrice == 0) {
                                    getString(R.string.non_member_price_free_text)
                                } else {
                                    getString(R.string.non_member_price_text, nonMemberPrice)
                                }

                                priceText.appendLine(nonMemberPriceText)
                            }

                            root.event_price.text = priceText.toString()

                            root.event_info.text = document.getString("info")

                            val user = Firebase.auth.currentUser
                            val iMail = user?.email
                            val studentId = iMail?.substringBefore("@")
                            val attendeeList = document.get("attendee_list") as List<*>

                            // Student is not an attendee
                            if (studentId !in attendeeList) {
                                setRegisterListener(root.event_register_button, eventRef, studentId)
                            }
                            // Student is an attendee
                            else {
                                setCancelListener(root.event_register_button, eventRef, studentId)
                            }

                        } else {
                            // Toast.makeText(activity, "Document Does Not Exist", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        // Toast.makeText(activity, "$exception", Toast.LENGTH_SHORT).show()
                    }

        })

        return root
    }

    private fun setRegisterListener(eventRegisterButton: Button, eventRef: DocumentReference, studentId: String?) {

        eventRegisterButton.text = getString(R.string.register)

        eventRegisterButton.setOnClickListener {

            eventRef.update("attendee_list", FieldValue.arrayUnion(studentId))
                .addOnSuccessListener {
                    // Toast.makeText(activity, "Register Success", Toast.LENGTH_SHORT).show()
                    setCancelListener(eventRegisterButton, eventRef, studentId)
                }
                .addOnFailureListener {
                    // Toast.makeText(activity, "Register Failed", Toast.LENGTH_SHORT).show()
                }

        }

    }

    private fun setCancelListener(eventRegisterButton: Button, eventRef: DocumentReference, studentId: String?) {

        eventRegisterButton.text = getString(R.string.cancel)

        eventRegisterButton.setOnClickListener {

            eventRef.update("attendee_list", FieldValue.arrayRemove(studentId))
                .addOnSuccessListener {
                    // Toast.makeText(activity, "Cancel Success", Toast.LENGTH_SHORT).show()
                    setRegisterListener(eventRegisterButton, eventRef, studentId)
                }
                .addOnFailureListener {
                    // Toast.makeText(activity, "Cancel Failed", Toast.LENGTH_SHORT).show()
                }

        }

    }

}
