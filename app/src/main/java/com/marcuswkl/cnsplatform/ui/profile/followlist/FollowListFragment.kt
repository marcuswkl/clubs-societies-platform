package com.marcuswkl.cnsplatform.ui.profile.followlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.marcuswkl.cnsplatform.R

class FollowListFragment : Fragment() {

    private lateinit var followListViewModel: FollowListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        followListViewModel =
            ViewModelProvider(this).get(followListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_follow_list, container, false)

        return root
    }

}
