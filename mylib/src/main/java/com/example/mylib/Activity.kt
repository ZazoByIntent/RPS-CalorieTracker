package com.example.mylib

import java.lang.IllegalArgumentException
import java.util.*

class Activity(var name: String, var burnedCalories: Double, var id: String = UUID.randomUUID().toString().replace("-", "")) {
    init{
        if(burnedCalories<=0)
            throw IllegalArgumentException("Napacna vrednost pokurjenih kalorij")
    }
    override fun toString() : String = "\nAktivnost: $name (ID: $id) je pokurila $burnedCalories kalorij"
}

fun main() {
    val kolesarjenje=Activity("Kolesarjenje",200.50)
    val nogomet = Activity ("Nogomet",321.15)

    println("$kolesarjenje")
    println("$nogomet")
}