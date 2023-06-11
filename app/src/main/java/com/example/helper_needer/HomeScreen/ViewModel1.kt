package com.example.helper_needer.HomeScreen

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.helper_needer.ModelResponse.RegisterResponse
import com.example.helper_needer.Profile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModel1 : ViewModel() {

    private var movieLiveData= MutableLiveData<List<User>>()
    fun getPopularMovies(x:String,y:String){
        //val x= Profile()
       /* val a=x.sharedpref
        val cityx=a.getString("City",null).toString()
        val typex=a.getString("Type",null).toString()*/
        var y1=y
        if(y=="helper"){
            y1="needer"
        }
        else{
            y1="helper"
        }
        ReterofitClient2.api.setType(x,y1).enqueue(object :
            Callback<Data1> {
            override fun onResponse(call: Call<Data1>, response: Response<Data1>) {
                if(response.body()!=null) {

                    Log.i("NumberGenerated",response.body()!!.error)
                    movieLiveData.value=response.body()!!.users
                }
            }

            override fun onFailure(call: Call<Data1>, t: Throwable) {
                Log.i("Tag","BadyP")
            }

        })
    }
    fun observeMovieLiveData() : MutableLiveData<List<User>> {
        return movieLiveData
    }


    }
