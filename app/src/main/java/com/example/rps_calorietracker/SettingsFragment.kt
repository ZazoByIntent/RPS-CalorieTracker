package com.example.rps_calorietracker

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.rps_calorietracker.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar

class SettingsFragment : Fragment() {
    lateinit var app: MyApplication
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        app = activity?.application as MyApplication
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        app.saveGoals(binding.addMealGoal.text.toString().toInt(), binding.addActivityGoal.text.toString().toInt())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.title = "Settings"

        binding.addActivityGoal.setText(app.caloriesBurnedGoal.toString())
        binding.addMealGoal.setText(app.caloriesBurnedGoal.toString())

        var DarkMode = false
        val nightModeFlags = view.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            DarkMode = true
        }
        binding.switchTheme.setOnClickListener {
            if (DarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Snackbar.make(view,getString(R.string.dark_mode_no), Snackbar.LENGTH_SHORT).show()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Snackbar.make(view,getString(R.string.dark_mode_yes), Snackbar.LENGTH_SHORT).show()
            }
        }

    }
}
