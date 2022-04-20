package com.example.rps_calorietracker


import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mylib.Activity
import com.example.rps_calorietracker.databinding.FragmentInputActivityBinding
import com.google.android.material.snackbar.Snackbar


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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activityName != "null"){
            // Edit
            binding.addActivity.setText(activityName)
            binding.addActivityCalories.setText(activityCal)
            binding.addActivityBtn.text = getString(R.string.fragment_input_activity_editActivityBtn)
            binding.addActivityBtn.setOnClickListener {
                try {
                    /*app.updateActivity(UUID, binding.addActivityCalories.text.toString().toDouble(), binding.addActivity.text.toString())
                    activity?.onBackPressed()
                    app.saveActivityToFile()*/
                    val builder = android.app.AlertDialog.Builder(context)
                    builder.setTitle("Update")
                    builder.setMessage("Are you sure you want to update \n${activityName}(burned calories: $activityCal) -> \n" +
                            "${binding.addActivity.text}(burned calories: ${binding.addActivityCalories.text})")
                    builder.setIcon(android.R.drawable.ic_menu_edit)
                    builder.setPositiveButton("Yes"){dialogInterface, which -> //performing positive action
                        Toast.makeText(context,"Edited",Toast.LENGTH_LONG).show()

                        app.updateActivity(UUID, binding.addActivityCalories.text.toString().toDouble(), binding.addActivity.text.toString())
                        activity?.onBackPressed()
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
                } catch (e: Exception) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
                    Log.e(ContentValues.TAG, e.toString())
                }
            }
        }
        else{
            binding.addActivityBtn.setOnClickListener {
                try {
                    app.dataActivity.list.add( Activity(
                        binding.addActivity.text.toString(),
                        binding.addActivityCalories.text.toString().toDouble()
                    )
                    )
                    app.saveActivityToFile()
                    binding.addActivity.setText("")
                    binding.addActivityCalories.setText("")

                } catch (e: Exception) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
                    Log.e(ContentValues.TAG, e.toString())
                }
                println("Novo dodan element ${app.dataActivity}")
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}