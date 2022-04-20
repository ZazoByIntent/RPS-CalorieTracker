package com.example.rps_calorietracker

import android.content.ContentValues
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.rps_calorietracker.databinding.FragmentInputMealBinding
import com.google.android.material.snackbar.Snackbar


class InputMealFragment : Fragment() {

    private var _binding: FragmentInputMealBinding? = null
    lateinit var app: MyApplication
    private lateinit var mealName: String
    private lateinit var mealCalories : String
    private lateinit var mealAmount : String
    private lateinit var UUID : String
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mealName = requireArguments().getString("mealName")!!
            mealCalories = requireArguments().getString("mealCalories")!!
            mealAmount = requireArguments().getString("mealAmount")!!
            UUID = requireArguments().getString("UUID")!!
        } else{
            mealName = "null"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        app = activity?.application as MyApplication
        _binding = FragmentInputMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(mealName != "null"){
            // Edit
            binding.addFoodName.setText(mealName)
            binding.addFoodCalories.setText(mealCalories)
            binding.addWeight.setText(mealAmount)
            binding.addMealBtn.text = getString(R.string.fragment_input_meal_editMealBtn)
            binding.addMealBtn.setOnClickListener {
                if (isDataValid()) {
                    try {
                        val builder = android.app.AlertDialog.Builder(context)
                        builder.setTitle("Update")
                        builder.setMessage("Are you sure you want to update \n$mealName($mealCalories calories, $mealAmount grams) to \n" +
                                "${binding.addFoodName.text.toString()}(${binding.addFoodCalories.text.toString()} calories, ${binding.addWeight.text.toString()} grams)")
                        builder.setIcon(android.R.drawable.ic_menu_edit)
                        builder.setPositiveButton("Yes"){dialogInterface, which -> //performing positive action
                            Toast.makeText(context,"clicked yes",Toast.LENGTH_LONG).show()

                            app.updateFood(UUID, binding.addFoodCalories.text.toString().toInt(), binding.addWeight.text.toString().toInt(), binding.addFoodName.text.toString())
                            activity?.onBackPressed()
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

                    } catch (e: Exception) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
                        Log.e(ContentValues.TAG, e.toString())
                    }
                } else {
                    Snackbar.make(view, "Invalid data", Snackbar.LENGTH_LONG).show()
                }
            }
        }
        else
        {
            binding.addMealBtn.setOnClickListener {
                if (isDataValid()) {
                    try {
                        app.dataFoods.addFood(
                            Food(
                                binding.addFoodName.text.toString(),
                                binding.addFoodCalories.text.toString().toInt(),
                                binding.addWeight.text.toString().toInt()
                            )
                        )
                        binding.addFoodName.setText("")
                        binding.addFoodCalories.setText("")
                        binding.addWeight.setText("")
                        app.saveFoodToFile()
                        //println("Novo dodan element ${app.dataFoods}")

                    } catch (e: Exception) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
                        Log.e(ContentValues.TAG, e.toString())
                    }
                } else {
                    Snackbar.make(view, "Invalid data", Snackbar.LENGTH_LONG).show()
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
        if (TextUtils.isEmpty(binding.addFoodName.text.toString())) {
            binding.addFoodName.setError("set item")
            ok = false
        }
        if (TextUtils.isEmpty(binding.addWeight.text.toString())) {
            binding.addWeight.setError("set amount")
            ok = false
        }
        if (TextUtils.isEmpty(binding.addFoodCalories.text.toString())) {
            binding.addFoodCalories.setError("set amount")
            ok = false
        }
        return ok
    }

}