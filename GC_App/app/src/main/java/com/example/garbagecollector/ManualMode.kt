package com.example.garbagecollector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import com.example.garbagecollector.retrofitPackage.*

class ManualMode : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_mode)

        init()
        val httpRetrofit = HTTPRetrofit()

        backButton.setOnClickListener {
            startActivity(Intent(this,PickControl::class.java))
            this.finish()
        }
        forward.setOnClickListener {
            forward.setBackgroundResource(R.drawable.arrow_border)
            httpRetrofit.controlRobotCar("forward")
        }
        right.setOnClickListener {
            right.setBackgroundResource(R.drawable.arrow_border)
            httpRetrofit.controlRobotCar("right")
        }
        left.setOnClickListener {
            left.setBackgroundResource(R.drawable.arrow_border)
            httpRetrofit.controlRobotCar("left")
        }

        down.setOnClickListener {
            down.setBackgroundResource(R.drawable.arrow_border)
            httpRetrofit.controlRobotCar("down")
        }
        stop.setOnClickListener {
            forward.background = null
            right.background = null
            left.background = null
            down.background = null
            httpRetrofit.controlRobotCar("stop")
        }

        motor1SeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                httpRetrofit.controlRobotArm(progress,"motor1")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //TODO("Not yet implemented")
            }

        })

        motor2SeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                httpRetrofit.controlRobotArm(progress,"motor2")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //TODO("Not yet implemented")
            }

        })

        motor3SeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                httpRetrofit.controlRobotArm(progress,"motor3")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //TODO("Not yet implemented")
            }

        })

        motor4SeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                httpRetrofit.controlRobotArm(progress,"motor4")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //TODO("Not yet implemented")
            }

        })

        motor5SeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                httpRetrofit.controlRobotArm(progress,"motor5")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //TODO("Not yet implemented")
            }

        })


    }

    private fun init() {
        backButton = findViewById(R.id.back_button_manual)
        forward = findViewById(R.id.arrow_up)
        right = findViewById(R.id.arrow_right)
        left = findViewById(R.id.arrow_left)
        down = findViewById(R.id.arrow_down)
        stop = findViewById(R.id.arrow_stop)
        motor1SeekBar = findViewById(R.id.seek_first)
        motor2SeekBar = findViewById(R.id.seek_second)
        motor3SeekBar = findViewById(R.id.seek_third)
        motor4SeekBar = findViewById(R.id.seek_fourth)
        motor5SeekBar = findViewById(R.id.seek_fifth)
    }

    private lateinit var backButton:ImageView
    private lateinit var forward:ImageView
    private lateinit var right:ImageView
    private lateinit var left:ImageView
    private lateinit var down:ImageView
    private lateinit var stop:ImageView
    private lateinit var motor1SeekBar:SeekBar
    private lateinit var motor2SeekBar:SeekBar
    private lateinit var motor3SeekBar:SeekBar
    private lateinit var motor4SeekBar:SeekBar
    private lateinit var motor5SeekBar:SeekBar
}