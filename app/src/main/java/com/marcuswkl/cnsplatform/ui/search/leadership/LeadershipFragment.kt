package com.marcuswkl.cnsplatform.ui.search.leadership

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.marcuswkl.cnsplatform.R


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
        return root
    }
}
