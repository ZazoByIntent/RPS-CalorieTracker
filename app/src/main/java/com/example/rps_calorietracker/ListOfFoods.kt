package com.example.rps_calorietracker

import java.util.*

class ListOfFoods(var name:String,var id:String = UUID.randomUUID().toString().replace("-", "")) {
    val listOfFoods: MutableList<Food> = mutableListOf()

    fun addFood(item: Food){
        try {
            listOfFoods.add(item)
        } catch (e: Exception) {
            println("$e")
        }
    }

    override fun toString(): String {
        return "List of foods named $name, with ID: $id has foods: $listOfFoods."

    }
}