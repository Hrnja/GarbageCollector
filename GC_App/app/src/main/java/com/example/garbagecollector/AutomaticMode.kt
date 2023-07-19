package com.example.garbagecollector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.garbagecollector.retrofitPackage.HTTPRetrofit


class AutomaticMode : AppCompatActivity() {

    private lateinit var start:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_automatic_mode)

        start = findViewById(R.id.start_button)
        val backButton:ImageView = findViewById(R.id.back_button_automatic)
        val textAutomatic:TextView = findViewById(R.id.text_in_automatic)

        textAutomatic.visibility = View.VISIBLE
        val httpRetrofit = HTTPRetrofit()

        backButton.setOnClickListener {
            startActivity(Intent(this,PickControl::class.java))
            this.finish()
        }

        start.setOnClickListener {
            httpRetrofit.controlRobotCar("startAutomaticMode")
        }

    }

}