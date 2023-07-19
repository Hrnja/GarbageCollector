package com.example.garbagecollector.retrofitPackage

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HTTPRetrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.4.1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiControl = retrofit.create(ControlRobotApi::class.java)

    fun controlRobotCar(direction:String) {
        apiControl.sendControlCarRequest(DirectionForCar(direction)).enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if(response.isSuccessful) {
                    Log.d("onResponse HTTP request", "Successful request, status code: " + response.code())
                }
                else {
                    Log.e("onResponse HTTP request", "Failed request, status code: " + response.code())
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                Log.e("onFailure HTTP request", "HTTP  request failed", t)
            }
        })
    }

    fun controlRobotArm(angle:Int,motor:String) {
        apiControl.sendControlRobotArmRequest(DirectionForRobotArm(angle,motor)).enqueue(object :  Callback<Void?>{
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if(response.isSuccessful) {
                    Log.d("onResponse HTTP request", "Successful request, status code: " + response.code())
                }
                else {
                    Log.e("onResponse HTTP request", "Failed request, status code: " + response.code())
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                Log.e("onFailure HTTP request", "HTTP  request failed", t)
            }
        })
        // Pauza od 100ms
        Thread.sleep(100)
    }

}