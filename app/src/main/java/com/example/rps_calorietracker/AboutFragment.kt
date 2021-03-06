package com.example.rps_calorietracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.rps_calorietracker.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    lateinit var app:MyApplication
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        app = activity?.application as MyApplication
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.title = "About"
        super.onViewCreated(view, savedInstanceState)
        binding.tvUUID.text = app.getID()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}