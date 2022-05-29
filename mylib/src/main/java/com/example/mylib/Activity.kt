package com.example.mylib

import java.lang.IllegalArgumentException
import java.util.*

class Activity(var name: String, var burnedCalories: Double, var id: String = UUID.randomUUID().toString().replace("-", "")) {
    init{
        if(burnedCalories<=0)
            throw IllegalArgumentException("Napacna vrednost pokurjenih kalorij")
    }
    override fun toString(): String {
        return "Activity: $name has burned $burnedCalories  calories"
    }
}
