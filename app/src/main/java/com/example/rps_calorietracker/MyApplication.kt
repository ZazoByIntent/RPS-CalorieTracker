package com.example.rps_calorietracker

import android.app.Application
import com.example.mylib.ListOfActivities

class MyApplication : Application() {
    lateinit var dataFoods: ListOfFoods
    lateinit var dataActivity: ListOfActivities

    override fun onCreate() {
        super.onCreate()
        dataFoods = ListOfFoods("ListOfFoods")
        dataActivity = ListOfActivities("ListofActivites")
    }

}