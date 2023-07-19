package com.example.garbagecollector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.garbagecollector.viewModel.GCViewModel
import java.time.LocalDateTime

class Login : AppCompatActivity() {

    private lateinit var name:EditText
    private lateinit var password:EditText
    private lateinit var login:Button
    private lateinit var register:TextView
    private lateinit var gcViewModel: GCViewModel
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        name = findViewById(R.id.login_name)
        password = findViewById(R.id.login_password)
        login = findViewById(R.id.login_button)
        register = findViewById(R.id.register)
        backButton = findViewById(R.id.back_button_login)

        backButton.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            this.finish()
        }

        gcViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(GCViewModel::class.java)

        register.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            intent.putExtra("from","FromLogin")
            startActivity(intent)
            this.finish()
        }

        login.setOnClickListener {
            val pname = name.text.toString()
            val ppassword = password.text.toString()

            if(name.text.isNotEmpty() && password.text.isNotEmpty()) {

                gcViewModel.getIDforLogin(pname, ppassword).observe(this, Observer { userID ->
                    if (userID == null) {
                        Toast.makeText(this, "User don't exist in database, please register", Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(this, PickControl::class.java)
                        intent.putExtra("source", "Login")
                        intent.putExtra("name", pname)
                        startActivity(intent)
                        this.finish()
                    }
                })
            }
            else if(name.text.isNotEmpty() && password.text.isEmpty()) {
                Toast.makeText(this,"Enter password", Toast.LENGTH_SHORT).show()
            }
            else if(name.text.isEmpty() && password.text.isNotEmpty()) {
                Toast.makeText(this,"Enter name", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this,"All fields are empty", Toast.LENGTH_SHORT).show()
            }
        }

    }
}