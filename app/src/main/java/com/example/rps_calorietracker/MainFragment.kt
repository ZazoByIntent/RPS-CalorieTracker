package com.example.rps_calorietracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rps_calorietracker.databinding.FragmentMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
                    val builder = android.app.AlertDialog.Builder(context)
                    builder.setTitle("Delete")
                    builder.setMessage("Meal ${app.dataFoods.listOfFoods[position].toString()}")
                    builder.setIcon(android.R.drawable.ic_dialog_alert)
                    builder.setPositiveButton("Yes"){dialogInterface, which -> //performing positive action
                        Toast.makeText(context,"clicked yes",Toast.LENGTH_LONG).show()
                        app.DeleteFood(position)
                        binding.rvMeal.adapter?.notifyItemRemoved(position)
                        app.saveFoodToFile()
                    }
                    builder.setNeutralButton("Cancel"){dialogInterface , which -> //performing cancel action
                        Toast.makeText(context,"clicked cancel",Toast.LENGTH_LONG).show()
                    }
                    builder.setNegativeButton("No"){dialogInterface, which -> //performing negative action
                        Toast.makeText(context,"clicked No", Toast.LENGTH_LONG).show()
                    }

                    val alertDialog: android.app.AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }else{
                    Timber.d("empty")
                }

            }
        })
        val activityAdapter = ActivityAdapter(app.dataActivity,object:ActivityAdapter.MyOnClick{
            override fun onClick(p0: View?, position: Int) {
                if (p0 != null) {
                    val bundle = Bundle(3)
                    bundle.putString("activityName", app.dataActivity.list[position].name)
                    bundle.putString("activityCal", app.dataActivity.list[position].burnedCalories.toString())
                    bundle.putString("UUID", app.dataActivity.list[position].id)
                    findNavController().navigate(R.id.action_fragment_main_to_inputActivityFragment, bundle)
                }
            }

            override fun onLongClick(p0: View?, position: Int) {
                if(app.dataActivity.list.size > 0)
                {
                    val builder = android.app.AlertDialog.Builder(context)
                    builder.setTitle("Delete")
                    builder.setMessage("${app.dataActivity.list[position]}")
                    builder.setIcon(android.R.drawable.ic_dialog_alert)
                    builder.setPositiveButton("Yes"){dialogInterface, which -> //performing positive action
                        Toast.makeText(context,"Deleted",Toast.LENGTH_LONG).show()
                        app.DeleteActivity(position)
                        binding.rvActivity.adapter?.notifyItemRemoved(position)
                        app.saveActivityToFile()
                    }
                    builder.setNeutralButton("Cancel"){dialogInterface , which -> //performing cancel action
                        Toast.makeText(context,"Canceled",Toast.LENGTH_LONG).show()
                    }
                    builder.setNegativeButton("No"){dialogInterface, which -> //performing negative action
                        Toast.makeText(context,"Declined", Toast.LENGTH_LONG).show()
                    }

                    val alertDialog: android.app.AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }else{
                    Timber.d("empty")
                }

            }
        })
        binding.rvMeal.adapter = mealAdapter
        binding.rvActivity.adapter = activityAdapter

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as AppCompatActivity).supportActionBar?.title = "Foods:"

        binding.rvMeal.layoutManager = LinearLayoutManager(activity)
        binding.rvActivity.visibility = View.GONE
        //binding.rvActivityText.visibility = View.GONE
        binding.rvActivity.layoutManager = LinearLayoutManager(activity)

        super.onViewCreated(view, savedInstanceState)
        CreateNewAdapter()
        var isVisible = false
        var rvActivity = true

        binding.swapRvFab.visibility = View.GONE
        binding.swapRvText.visibility = View.GONE
        binding.addMealFab.visibility = View.GONE
        binding.addMealText.visibility = View.GONE
        binding.addActivityFab.visibility = View.GONE
        binding.addActivityText.visibility = View.GONE
        binding.aboutFab.visibility = View.GONE
        binding.aboutText.visibility = View.GONE
        binding.settingsFab.visibility = View.GONE
        binding.settingsText.visibility = View.GONE


        binding.addMealFab.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_main_to_inputMealFragment)
        }
        binding.addActivityFab.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_main_to_inputActivityFragment)
        }
        binding.aboutFab.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_main_to_aboutFragment)
        }
        binding.settingsFab.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_main_to_settingsFragment)
        }
        binding.swapRvFab.setOnClickListener {
            if (rvActivity) {
                binding.rvMeal.visibility = View.GONE
                //binding.rvFoodText.visibility = View.GONE
                binding.rvActivity.visibility = View.VISIBLE
                //binding.rvActivityText.visibility = View.VISIBLE
                (activity as AppCompatActivity).supportActionBar?.title = "Activities:"
                rvActivity = false
            } else if (!rvActivity) {
                binding.rvActivity.visibility = View.GONE
                //binding.rvActivityText.visibility = View.GONE
                binding.rvMeal.visibility = View.VISIBLE
                //binding.rvFoodText.visibility = View.VISIBLE
                (activity as AppCompatActivity).supportActionBar?.title = "Foods:"
                rvActivity = true
            }
        }
        binding.fabMenu.setOnClickListener {
            if (isVisible) {
                binding.swapRvFab.visibility = View.GONE
                binding.swapRvText.visibility = View.GONE
                binding.addMealFab.visibility = View.GONE
                binding.addMealText.visibility = View.GONE
                binding.addActivityFab.visibility = View.GONE
                binding.addActivityText.visibility = View.GONE
                binding.aboutFab.visibility = View.GONE
                binding.aboutText.visibility = View.GONE
                binding.settingsFab.visibility = View.GONE
                binding.settingsText.visibility = View.GONE
                isVisible = false
            } else if (!isVisible) {
                binding.swapRvFab.visibility = View.VISIBLE
                binding.swapRvText.visibility = View.VISIBLE
                binding.addMealFab.visibility = View.VISIBLE
                binding.addMealText.visibility = View.VISIBLE
                binding.addActivityFab.visibility = View.VISIBLE
                binding.addActivityText.visibility = View.VISIBLE
                binding.aboutFab.visibility = View.VISIBLE
                binding.aboutText.visibility = View.VISIBLE
                binding.settingsFab.visibility = View.VISIBLE
                binding.settingsText.visibility = View.VISIBLE
                isVisible = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}