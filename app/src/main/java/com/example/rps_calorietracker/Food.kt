package com.example.rps_calorietracker
import java.util.*
class Food(var name:String, var cal : Int, var amount: Int , var id: String = UUID.randomUUID().toString().replace("-", "")) {
    override fun toString(): String {
        return "ID: $id ,food: $name has $cal per 100 grams.\n The amount is $amount .  "
    }
}