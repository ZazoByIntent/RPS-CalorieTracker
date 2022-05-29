package com.example.rps_calorietracker

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mylib.Activity
import com.example.rps_calorietracker.databinding.FragmentInputActivityBinding
import com.google.android.material.snackbar.Snackbar
import android.widget.ArrayAdapter

class InputActivityFragment : Fragment() {
    private var _binding: FragmentInputActivityBinding? = null
    lateinit var app:MyApplication
    private lateinit var activityName: String
    private lateinit var activityCal : String
    private lateinit var UUID : String
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            activityName = requireArguments().getString("activityName")!!
            activityCal = requireArguments().getString("activityCal")!!
            UUID = requireArguments().getString("UUID")!!
        } else{
            activityName = "null"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        app = activity?.application as MyApplication
        _binding = FragmentInputActivityBinding.inflate(inflater, container, false)
        var allActivites: MutableList<String> = mutableListOf()
        var tmpName= "None"
        for (activity: Activity in app.dataActivity.list) {

            if (tmpName != activity.name){
                allActivites.add(activity.name)
            }
            tmpName = activity.name
        }
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, allActivites)
        binding.activitytr.setAdapter(arrayAdapter)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Activity tracker"
        if(activityName != "null"){
            //DO NOT SHOW
            binding.textInputLayout.visibility = View.GONE
            // Edit
            binding.addActivity.setText(activityName)
            binding.addActivityCalories.setText(activityCal)
            binding.addActivityBtn.text = getString(R.string.fragment_input_activity_editActivityBtn)
            binding.addActivityBtn.setOnClickListener {
                if(isDataValid()){
                    try {
                        val builder = android.app.AlertDialog.Builder(context)
                        builder.setTitle("Update")
                        builder.setMessage("Are you sure you want to update \n${activityName}(burned calories: $activityCal) -> \n" +
                                "${binding.addActivity.text}(burned calories: ${binding.addActivityCalories.text})")
                        builder.setIcon(android.R.drawable.ic_menu_edit)
                        builder.setPositiveButton("Yes"){dialogInterface, which -> //performing positive action
                            app.updateActivity(UUID, binding.addActivityCalories.text.toString().toDouble(), binding.addActivity.text.toString())
                            activity?.onBackPressed()
                            app.saveActivityToFile()
                            Snackbar.make(view,getString(R.string.activity_edited), Snackbar.LENGTH_SHORT).show()
                        }
                        builder.setNeutralButton("Cancel"){dialogInterface , which -> //performing cancel action
                            Snackbar.make(view,getString(R.string.cancelled), Snackbar.LENGTH_SHORT).show()
                        }
                        builder.setNegativeButton("No"){dialogInterface, which -> //performing negative action
                            Snackbar.make(view,getString(R.string.cancelled), Snackbar.LENGTH_SHORT).show()
                        }

                        val alertDialog: android.app.AlertDialog = builder.create()
                        alertDialog.setCancelable(false)
                        alertDialog.show()
                    } catch (e: Exception) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Snackbar.make(view, getString(R.string.invalid_data), Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
        else{
            binding.addActivityBtn.setOnClickListener {
                if (isDataValid()) {
                    try {
                        app.dataActivity.list.add(
                            Activity(
                                binding.addActivity.text.toString(),
                                binding.addActivityCalories.text.toString().toDouble()
                            )
                        )
                        println("Activity added, " + binding.addActivity.text.toString())

                        app.saveActivityToFile()
                        binding.addActivity.setText("")
                        binding.addActivityCalories.setText("")
                        Snackbar.make(
                            view,
                            getString(R.string.activity_added),
                            Snackbar.LENGTH_SHORT
                        ).show()

                        //na novo nastavim adapter
                        var allActivities: MutableList<String> = mutableListOf()
                        var tmpName = "None"
                        for (activity: Activity in app.dataActivity.list) {

                            if (tmpName != activity.name) {
                                allActivities.add(activity.name)
                            }
                            tmpName = activity.name
                        }
                        val arrayAdapter =
                            ArrayAdapter(requireContext(), R.layout.dropdown_item, allActivities)
                        binding.activitytr.setAdapter(arrayAdapter)


                    } catch (e: Exception) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                    println("Novo dodan element ${app.dataActivity}")
                }
                else {
                    Snackbar.make(view, getString(R.string.invalid_data), Snackbar.LENGTH_LONG)
                        .show()
                }
            }

           binding.activitytr.setOnItemClickListener { parent, view, position, id ->
                val name = binding.activitytr.text.toString()
               println(name);
                for (activity: Activity in app.dataActivity.list) {
                    if (activity.name == name) {
                        println("Aktivnost:${activity}");

                        binding.addActivity.setText(activity.name)
                        binding.addActivityCalories.setText(activity.burnedCalories.toString())
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun isDataValid(): Boolean {
        var ok = true
        if (TextUtils.isEmpty(binding.addActivity.text.toString())) {
            binding.addActivity.setError("set item")
            ok = false
        }
        if (TextUtils.isEmpty(binding.addActivityCalories.text.toString())) {
            binding.addActivityCalories.setError("set amount")
            ok = false
        }
        return ok
    }
}