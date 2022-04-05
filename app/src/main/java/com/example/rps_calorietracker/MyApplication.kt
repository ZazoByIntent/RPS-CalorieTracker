package com.example.rps_calorietracker

import android.app.Application

class MyApplication : Application() {
    lateinit var dataFoods: ListOfFoods
    override fun onCreate() {
        super.onCreate()
        dataFoods = ListOfFoods("ListOfFoods")
    }

}