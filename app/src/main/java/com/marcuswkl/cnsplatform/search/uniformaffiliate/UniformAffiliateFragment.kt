package com.marcuswkl.cnsplatform.search.uniformaffiliate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.marcuswkl.cnsplatform.R
import com.marcuswkl.cnsplatform.club.ClubFragment
import kotlinx.android.synthetic.main.fragment_uniform_affiliate.view.*


class UniformAffiliateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_uniform_affiliate, container, false)

        val fragmentManager = activity?.supportFragmentManager

        root.uniform_affiliate_tedx_tile.setOnClickListener {

            val clubId = "tedxsunwayuniversity"
            fragmentManager?.setFragmentResult("tileClubId", bundleOf("clubId" to clubId))

            val clubFragment = ClubFragment()
            val fragmentTransaction = fragmentManager?.beginTransaction()
            if (fragmentTransaction != null) {
                fragmentTransaction.replace(R.id.uniform_affiliate_fragment, clubFragment)
                    .addToBackStack(null)
                    .commit()
            }

        }

        return root
    }
}
