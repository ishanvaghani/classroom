package com.example.classroom.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.classroom.R
import com.example.classroom.databinding.FragmentPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PageFragment : Fragment() {

    private var _binding: FragmentPageBinding ?= null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPageBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}