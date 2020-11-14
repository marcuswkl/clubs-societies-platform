package com.marcuswkl.cnsplatform.ui.club

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.marcuswkl.cnsplatform.R

class ClubFragment : Fragment() {

    private lateinit var clubViewModel: ClubViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        clubViewModel =
            ViewModelProvider(this).get(ClubViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_club, container, false)

        return root
    }

}
