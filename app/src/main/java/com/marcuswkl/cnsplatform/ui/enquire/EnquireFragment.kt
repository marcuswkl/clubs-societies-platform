package com.marcuswkl.cnsplatform.ui.enquire

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.marcuswkl.cnsplatform.R

class EnquireFragment : Fragment() {

    private lateinit var enquireViewModel: EnquireViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        enquireViewModel =
            ViewModelProvider(this).get(EnquireViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_enquire, container, false)
        return root
    }

}
