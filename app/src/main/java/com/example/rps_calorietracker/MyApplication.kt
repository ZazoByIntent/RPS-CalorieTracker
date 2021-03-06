package com.example.rps_calorietracker

import android.app.Application
import com.example.mylib.ListOfActivities
import timber.log.Timber
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.io.IOException
import org.apache.commons.io.FileUtils
import android.content.Context
import android.content.SharedPreferences
import com.example.mylib.Activity
import java.util.*

const val FILE_FOOD = "mydatafood.json"
const val FILE_ACTIVITY = "mydataactivity.json"
const val MY_SP_FILE_NAME = "myshared.data"


class MyApplication : Application() {
    lateinit var dataFoods: ListOfFoods
    private lateinit var gsonFood: Gson
    private lateinit var fileFood: File
    private lateinit var gsonActivity: Gson
    private lateinit var fileActivity: File
    private lateinit var sharedPref: SharedPreferences
    var calorieGoal = 0
    var caloriesBurnedGoal = 0


    lateinit var dataActivity: ListOfActivities

    override fun onCreate() {
        super.onCreate()
        dataFoods = ListOfFoods("ListOfFoods")
        dataActivity = ListOfActivities("ListofActivites")

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree()) //Init report type
        }
        Timber.d("On create application")
        gsonFood = GsonBuilder()
            .setPrettyPrinting()
            .create()
        fileFood = File(filesDir, FILE_FOOD)
        gsonActivity = GsonBuilder()
            .setPrettyPrinting()
            .create()
        fileActivity=File(filesDir, FILE_ACTIVITY)
        Timber.d("File name path ${fileFood.path}")
        initFoodData()
        initActivityData()
        initShared()
        getGoals()
        if(!containsID()) {
            saveID(UUID.randomUUID().toString().replace("-", ""))
        }
    }

    private fun getGoals() {
        calorieGoal = sharedPref.getInt("intake", 0)
        caloriesBurnedGoal = sharedPref.getInt("burned", 0)
    }

    fun saveGoals(calorieIntake: Int, caloriesBurned: Int) {
        with(sharedPref.edit()){
            putInt("intake", calorieIntake)
            putInt("burned", caloriesBurned)
            apply()
        }
    }

    private fun initShared() {
        sharedPref = getSharedPreferences(MY_SP_FILE_NAME, Context.MODE_PRIVATE)
    }

    private fun saveID(id:String) {
        with(sharedPref.edit()){
            putString("ID", id)
            apply()
        }
    }

    fun getID():String? {
        return sharedPref.getString("ID","NoData")
    }

    private fun containsID() : Boolean{
        return sharedPref.contains("ID")
    }

    fun initFoodData() {
        dataFoods = try { //www
            gsonFood.fromJson(FileUtils.readFileToString(fileFood), ListOfFoods::class.java)
        } catch (e: IOException) {
            Timber.d("No file init data.")
            ListOfFoods("First time init")
        }
    }

    fun saveFoodToFile() {
        try {
            FileUtils.writeStringToFile(fileFood,gsonFood.toJson(dataFoods))

            Timber.d("Save to file.")
        } catch (e: IOException) {
            Timber.d("Can't save " + fileFood.path)
        }
    }

    fun DeleteFood(index: Int) {
        try {
            dataFoods.listOfFoods.removeAt(index)
            saveFoodToFile()
        } catch (e: Exception) {
            Timber.e(e.message)
        }
    }

    fun updateFood(id: String, cals: Int, amount: Int, name: String) {
        try {
            for (food: Food in dataFoods.listOfFoods) {
                if (food.id == id) {
                    food.cal = cals
                    food.amount = amount
                    food.name = name
                    saveFoodToFile()
                }
            }
        } catch (e: Exception) {
            Timber.e(e.message)
        }
    }
    fun initActivityData() {
        dataActivity= try {
            gsonActivity.fromJson(FileUtils.readFileToString(fileActivity), ListOfActivities::class.java)
        } catch (e: IOException) {
            Timber.d("No file init data.")
            ListOfActivities("First time init")
        }
    }
    fun saveActivityToFile() {
        try {
            FileUtils.writeStringToFile(fileActivity,gsonActivity.toJson(dataActivity))
            Timber.d("Save to file.")
        } catch (e: IOException) {
            Timber.d("Can't save " + fileActivity.path)
        }
    }
    fun DeleteActivity(index: Int) {
        try {
            dataActivity.list.removeAt(index)
            saveFoodToFile()
        } catch (e: Exception) {
            Timber.e(e.message)
        }
    }
    fun updateActivity(id: String, burnedCalories: Double, name: String) {
        try {
            for (Activity: Activity in dataActivity.list) {
                if (Activity.id == id) {
                    Activity.name = name
                    Activity.burnedCalories = burnedCalories
                    saveFoodToFile()
                }
            }
        } catch (e: Exception) {
            Timber.e(e.message)
        }
    }
}