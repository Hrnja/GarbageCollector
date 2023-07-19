package com.example.garbagecollector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var login:Button
    private lateinit var signup:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login = findViewById(R.id.login)
        signup = findViewById(R.id.signup)

        login.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
            this.finish()
        }

        signup.setOnClickListener {
            val intent = Intent(this,Signup::class.java)
            intent.putExtra("from","FromMainActivity")
            startActivity(intent)
            this.finish()
        }

    }
}