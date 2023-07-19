package com.example.garbagecollector.retrofitPackage


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ControlRobotApi {
    @Headers("Content-Type: application/json")

    @POST("controlRobot")
    fun sendControlCarRequest(@Body direction: DirectionForCar) : Call<Void?>

    @POST ("controlRobot")
    fun sendControlRobotArmRequest(@Body directionForRobotArm:DirectionForRobotArm) : Call<Void?>
}