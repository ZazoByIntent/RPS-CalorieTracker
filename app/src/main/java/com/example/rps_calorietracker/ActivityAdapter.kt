package com.example.rps_calorietracker


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mylib.ListOfActivities


class ActivityAdapter(private val data : ListOfActivities, private val onClickObject: ActivityAdapter.MyOnClick) : RecyclerView.Adapter<ActivityAdapter.ViewHolder>(){

    interface MyOnClick {
        fun onClick(p0: View?, position:Int)
        fun onLongClick(p0:View?, position: Int)
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        val line: CardView = itemView.findViewById(R.id.cvActivity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_activity, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = data.list[position]

        holder.tvName.text = itemsViewModel.name
        holder.tvAmount.text = itemsViewModel.burnedCalories.toString()

        holder.line.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                onClickObject.onClick(p0,holder.absoluteAdapterPosition)
            }
        })

        holder.line.setOnLongClickListener(object:View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                onClickObject.onLongClick(p0,holder.absoluteAdapterPosition)
                return true
            }
        })

    }

}