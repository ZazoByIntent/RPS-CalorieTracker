package com.example.mylib

import java.util.*

class ListOfActivities(var name:String,var id:String = UUID.randomUUID().toString().replace("-", "")) {
    val listOfActivities: MutableList<Activity> = mutableListOf()

    fun addFood(item: Activity){
        try {
            listOfActivities.add(item)
        } catch (e: Exception) {
            println("$e")
        }
    }

    override fun toString(): String {
        return "List of foods named $name, with ID: $id has foods: $listOfActivities."

    }
}