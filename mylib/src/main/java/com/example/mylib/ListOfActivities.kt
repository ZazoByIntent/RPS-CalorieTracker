package com.example.mylib

import java.util.*

class ListOfActivities(var name:String,var id:String = UUID.randomUUID().toString().replace("-", "")) {
    val list: MutableList<Activity> = mutableListOf()

    override fun toString(): String {
        return "List of activities named $name, with ID: $id has Activites: $list."
    }
}

