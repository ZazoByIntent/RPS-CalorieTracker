package com.example.rps_calorietracker
import java.util.*

class Food(var name:String, var cal : Int, var amount: Int , var id: String = UUID.randomUUID().toString().replace("-", "")) {
    override fun toString(): String {
        return "$name has $cal calories.\n The amount is $amount grams .  "
    }
}