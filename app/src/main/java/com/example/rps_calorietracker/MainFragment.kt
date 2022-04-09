package com.example.rps_calorietracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rps_calorietracker.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    lateinit var app:MyApplication
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        app = activity?.application as MyApplication
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun CreateNewAdapter(){
        val mealAdapter = MealAdapter(app.dataFoods)
        val activityAdapter = ActivityAdapter(app.dataActivity)
        val concatAdapter = ConcatAdapter(mealAdapter, activityAdapter)
        binding.rvMain.adapter = concatAdapter

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvMain.layoutManager = LinearLayoutManager(activity)
        super.onViewCreated(view, savedInstanceState)
        CreateNewAdapter()

        binding.btnAddMeal.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_main_to_inputMealFragment)
        }
        binding.btnAddActivity.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_main_to_inputActivityFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}