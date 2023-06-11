package com.example.helper_needer

import android.Manifest.permission.CALL_PHONE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helper_needer.HomeScreen.Adapter1
import com.example.helper_needer.HomeScreen.ViewModel1
import com.example.helper_needer.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText


class SearchFragment(x:String,y:String) : Fragment((R.layout.fragment_search)) {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel:ViewModel1
    lateinit var movieAdapter: Adapter1
    lateinit var rec:RecyclerView
    lateinit var Name: TextInputEditText
    lateinit var flash:FloatingActionButton
   var x1:String=x
   var y1:String=y
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flash=getView()!!.findViewById(R.id.floatingActionButton)
        flash.setOnClickListener(View.OnClickListener() {
            val callintent=Intent(Intent.ACTION_CALL)

        })
        movieAdapter= Adapter1()
        Log.i("Tag","Bady")
        rec=getView()!!.findViewById(R.id.rv1)
        rec.apply{
            layoutManager= LinearLayoutManager(context)
            adapter=movieAdapter
        }
        viewModel= ViewModelProvider(this)[ViewModel1::class.java]
        viewModel.getPopularMovies(x1,y1)
        viewModel.observeMovieLiveData().observe(viewLifecycleOwner, Observer { movieList ->
            movieAdapter.setMoviesData(movieList)
        })


    }
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding= ActivityMainBinding.inflate(layoutInflater)
       movieAdapter= Adapter1()
        rv1.apply{
            layoutManager= LinearLayoutManager(context)
            adapter=movieAdapter
        }

        viewModel= ViewModelProvider(this)[ViewModel1::class.java]
        viewModel.getPopularMovies()
        viewModel.observeMovieLiveData().observe(this, Observer { movieList ->
            movieAdapter.setMoviesData(movieList)
        })
    }*/




}