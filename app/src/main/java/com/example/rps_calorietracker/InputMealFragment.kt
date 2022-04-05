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
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

                } catch (e: Exception) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
                    Log.e(ContentValues.TAG, e.toString())
                }
            } else {
                Snackbar.make(view, "Invalid data", Snackbar.LENGTH_LONG).show()
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