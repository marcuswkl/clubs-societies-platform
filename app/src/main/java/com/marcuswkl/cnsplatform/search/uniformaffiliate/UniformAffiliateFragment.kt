package com.marcuswkl.cnsplatform.search.uniformaffiliate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.marcuswkl.cnsplatform.R
import com.marcuswkl.cnsplatform.club.ClubFragment
import kotlinx.android.synthetic.main.fragment_leadership.view.*


class UniformAffiliateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_leadership, container, false)

        val fragmentManager = activity?.supportFragmentManager

        root.leadership_susc_tile.setOnClickListener {

            val clubId = "sunwayuniversitystudentcouncil"
            fragmentManager?.setFragmentResult("tileClubId", bundleOf("clubId" to clubId))

            val clubFragment = ClubFragment()
            val fragmentTransaction = fragmentManager?.beginTransaction()
            if (fragmentTransaction != null) {
                fragmentTransaction.replace(R.id.leadership_fragment, clubFragment)
                    .addToBackStack(null)
                    .commit()
            }

        }

        return root
    }
}
