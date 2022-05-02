package com.example.rps_calorietracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationHelper = MyNotificationHelper(context)
        val suggestion = intent.extras?.getString("Suggestion")
        notificationHelper.createNotificationChannel()
        if (suggestion != null) {
            notificationHelper.createNotify("A reminder from CalorieTracker", suggestion, R.drawable.ic_baseline_food_bank_24)
        }
        else{
            notificationHelper.createNotify("A reminder from CalorieTracker", "You're doing great!", R.drawable.ic_baseline_food_bank_24)
        }
    }
}
