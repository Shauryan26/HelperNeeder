package com.example.helper_needer.HomeScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helper_needer.R
import com.example.helper_needer.databinding.RecyclerviewBinding

class Adapter1: RecyclerView.Adapter<Adapter1.ViewHolder>() {
    private var movieList=ArrayList<User>()

    fun setMoviesData(movieList : List<User>){
        this.movieList=movieList as ArrayList<User> /* = java.util.ArrayList<com.Result> */
        notifyDataSetChanged()
    }

    class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val name = view.findViewById<TextView>(R.id.name)
        val age = view.findViewById<TextView>(R.id.age)
        val phone = view.findViewById<TextView>(R.id.phone)
        val gender = view.findViewById<TextView>(R.id.gender)
        val duration = view.findViewById<TextView>(R.id.duration)
        val city = view.findViewById<TextView>(R.id.city)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.name.text=movieList[position].Name
        holder.age.text=movieList[position].Age
        holder.phone.text=movieList[position].Phone
        holder.gender.text=movieList[position].Gender
        holder.duration.text=movieList[position].Duration
        holder.city.text=movieList[position].City
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}