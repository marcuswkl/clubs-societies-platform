package com.marcuswkl.cnsplatform.ui.search.leadership

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.marcuswkl.cnsplatform.R
import com.marcuswkl.cnsplatform.ui.club.ClubFragment
import kotlinx.android.synthetic.main.fragment_leadership.view.*


class LeadershipFragment : Fragment() {

    private lateinit var leadershipViewModel: LeadershipViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        leadershipViewModel =
            ViewModelProvider(this).get(LeadershipViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_leadership, container, false)

        root.leadership_susc_tile.setOnClickListener {
            val clubFragment = ClubFragment()
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            if (fragmentTransaction != null) {
                fragmentTransaction.replace(R.id.leadership_fragment, clubFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        return root
    }
}
