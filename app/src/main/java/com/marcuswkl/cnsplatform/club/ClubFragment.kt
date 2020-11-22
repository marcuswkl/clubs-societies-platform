package com.marcuswkl.cnsplatform.club

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.marcuswkl.cnsplatform.R
import com.marcuswkl.cnsplatform.enquire.EnquireFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_club.view.*

class ClubFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_club, container, false)

        val db = Firebase.firestore

        val fragmentManager = activity?.supportFragmentManager

        // Listener for club tile
        fragmentManager?.setFragmentResultListener(
            "tileClubId", this, { key, bundle ->

                val tileClubId = bundle.getString("clubId")

                val clubRef = db.collection("clubs").document("$tileClubId")
                clubRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {

                            Picasso.get().load(document.getString("logo")).into(root.club_logo)
                            root.club_name_title.text = document.getString("name")
                            root.club_category.text = document.getString("category")
                            root.club_info.text = document.getString("info")
                            root.club_past_events.text = document.getString("past_events")

                            val committeeList = document.get("committee_list") as List<*>
                            root.club_committee_list.text = listToString(committeeList)

                            val meetingInfo = document.get("meeting_info") as List<*>
                            root.club_meeting_info.text = listToString(meetingInfo)

                            root.club_advisor.text = document.getString("advisor")

                            val membershipFee = document.getDouble("membership_fee")?.toInt()
                            val membershipFeeText = getString(R.string.membership_fee_text, membershipFee)
                            val membershipBenefits = document.get("membership_benefits") as List<*>
                            root.club_membership_info.text = membershipInfoToString(membershipFeeText, membershipBenefits)

                            root.club_email.text = document.getString("email")

                            val tags = document.get("tags") as List<*>
                            root.club_tags.text = tagsToString(tags)

                            root.club_enquire_button.setOnClickListener {
                                fragmentManager.setFragmentResult("enquireClubId", bundleOf("clubId" to tileClubId))
                                val enquireFragment = EnquireFragment()
                                val fragmentTransaction = fragmentManager.beginTransaction()
                                fragmentTransaction.replace(R.id.club_fragment, enquireFragment)
                                    .addToBackStack(null)
                                    .commit()
                            }

                            val user = Firebase.auth.currentUser
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
                            }

                            val followList = document.get("follow_list") as List<*>

                            // Student is not a follower
                            if (studentId !in followList) {
                                setFollowListener(root.club_follow_button, clubRef, studentId)
                            }
                            // Student is a follower
                            else {
                                setUnfollowListener(root.club_follow_button, clubRef, studentId)
                            }

                        } else {
                            Toast.makeText(activity, "Document Does Not Exist.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(activity, "Read Failed. $exception", Toast.LENGTH_SHORT).show()
                    }

        })

        // Listener for search result
        fragmentManager?.setFragmentResultListener(
            "resultClubId", this, { key, bundle ->

                val resultClubId = bundle.getString("clubId")

                val clubRef = db.collection("clubs").document("$resultClubId")
                clubRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {

                            Picasso.get().load(document.getString("logo")).into(root.club_logo)
                            root.club_name_title.text = document.getString("name")
                            root.club_category.text = document.getString("category")
                            root.club_info.text = document.getString("info")
                            root.club_past_events.text = document.getString("past_events")

                            val committeeList = document.get("committee_list") as List<*>
                            root.club_committee_list.text = listToString(committeeList)

                            val meetingInfo = document.get("meeting_info") as List<*>
                            root.club_meeting_info.text = listToString(meetingInfo)

                            root.club_advisor.text = document.getString("advisor")

                            val membershipFee = document.getDouble("membership_fee")?.toInt()
                            val membershipFeeText = getString(R.string.membership_fee_text, membershipFee)
                            val membershipBenefits = document.get("membership_benefits") as List<*>
                            root.club_membership_info.text = membershipInfoToString(membershipFeeText, membershipBenefits)

                            root.club_email.text = document.getString("email")

                            val tags = document.get("tags") as List<*>
                            root.club_tags.text = tagsToString(tags)

                            root.club_enquire_button.setOnClickListener {
                                fragmentManager.setFragmentResult("enquireClubId", bundleOf("clubId" to resultClubId))
                                val enquireFragment = EnquireFragment()
                                val fragmentTransaction = fragmentManager.beginTransaction()
                                fragmentTransaction.replace(R.id.club_fragment, enquireFragment)
                                    .addToBackStack(null)
                                    .commit()
                            }

                            val user = Firebase.auth.currentUser
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
                            }

                            val followList = document.get("follow_list") as List<*>

                            // Student is not a follower
                            if (studentId !in followList) {
                                setFollowListener(root.club_follow_button, clubRef, studentId)
                            }
                            // Student is a follower
                            else {
                                setUnfollowListener(root.club_follow_button, clubRef, studentId)
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

    // Convert retrieved Lists from db to strings for TextView display
    private fun listToString(list: List<*>): String {
        val listText = StringBuilder()
        list.forEach {listItem ->
            listText.appendLine(listItem)
        }
        return listText.toString()
    }

    // Modified listToString() for membership info
    private fun membershipInfoToString(membershipFee: String, membershipBenefits: List<*>): String {
        val listText = StringBuilder()
        listText.appendLine(membershipFee)
        membershipBenefits.forEach {benefit ->
            listText.appendLine("- $benefit")
        }
        return listText.toString()
    }

    // Modified listToString() for tags
    private fun tagsToString(list: List<*>): String {
        val listText = StringBuilder()
        list.forEach {listItem ->
            listText.append("$listItem, ")
        }
        return listText.toString().removeSuffix(", ")
    }

    private fun setJoinListener(clubJoinButton: Button, clubRef: DocumentReference, studentId: String?) {

        clubJoinButton.text = getString(R.string.join)

        clubJoinButton.setOnClickListener {

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

    }

    private fun setFollowListener(clubFollowButton: Button, clubRef: DocumentReference, studentId: String?) {

        clubFollowButton.text = getString(R.string.follow)

        clubFollowButton.setOnClickListener {

            clubRef.update("follow_list", FieldValue.arrayUnion(studentId))
                .addOnSuccessListener {
                    Toast.makeText(activity, "Follow Success", Toast.LENGTH_SHORT).show()
                    setUnfollowListener(clubFollowButton, clubRef, studentId)
                }
                .addOnFailureListener {
                    Toast.makeText(activity, "Follow Fail", Toast.LENGTH_SHORT).show()
                }

        }

    }

    private fun setUnfollowListener(clubFollowButton: Button, clubRef: DocumentReference, studentId: String?) {

        clubFollowButton.text = getString(R.string.unfollow)

        clubFollowButton.setOnClickListener {

            clubRef.update("follow_list", FieldValue.arrayRemove(studentId))
                .addOnSuccessListener {
                    Toast.makeText(activity, "Unfollow Success", Toast.LENGTH_SHORT).show()
                    setFollowListener(clubFollowButton, clubRef, studentId)
                }
                .addOnFailureListener {
                    Toast.makeText(activity, "Unfollow Fail", Toast.LENGTH_SHORT).show()
                }

        }

    }

}
