package com.example.rps_calorietracker


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView



class MealAdapter(private val data : ListOfFoods) : RecyclerView.Adapter<MealAdapter.ViewHolder>(){

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvCal: TextView = itemView.findViewById(R.id.tvCal)
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        // val line: CardView = itemView.findViewById(R.id.cvLine)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.card_view, parent, false)
    return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.listOfFoods.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = data.listOfFoods[position]

        holder.tvName.text = itemsViewModel.name
        holder.tvCal.text = itemsViewModel.cal.toString()
        holder.tvAmount.text = itemsViewModel.amount.toString()


    }

}