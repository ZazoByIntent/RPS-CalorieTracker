package com.example.rps_calorietracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rps_calorietracker.databinding.FragmentMainBinding
import timber.log.Timber

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
        val mealAdapter = MealAdapter(app.dataFoods, object:MealAdapter.MyOnClick{
            override fun onClick(p0: View?, position: Int) {
                if (p0 != null) {
                    val bundle = Bundle(3)
                    bundle.putString("mealName", app.dataFoods.listOfFoods[position].name)
                    bundle.putString("mealCalories", app.dataFoods.listOfFoods[position].cal.toString())
                    bundle.putString("mealAmount", app.dataFoods.listOfFoods[position].amount.toString())
                    bundle.putString("UUID", app.dataFoods.listOfFoods[position].id)
                    findNavController().navigate(R.id.action_fragment_main_to_inputMealFragment, bundle)
                }
            }

            override fun onLongClick(p0: View?, position: Int) {
                if(app.dataFoods.listOfFoods.size > 0)
                {
                    app.DeleteFood(position)
                    binding.rvMeal.adapter?.notifyItemRemoved(position)
                    app.saveFoodToFile()
                }else{
                    Timber.d("empty")
                }

            }
        })
        val activityAdapter = ActivityAdapter(app.dataActivity)
        binding.rvMeal.adapter = mealAdapter
        binding.rvActivity.adapter = activityAdapter

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvMeal.layoutManager = LinearLayoutManager(activity)
        binding.rvActivity.layoutManager = LinearLayoutManager(activity)
        super.onViewCreated(view, savedInstanceState)
        CreateNewAdapter()

        binding.btnAddMeal.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_main_to_inputMealFragment)
        }
        binding.btnAddActivity.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_main_to_inputActivityFragment)
        }
        binding.btnAbout.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_main_to_aboutFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}