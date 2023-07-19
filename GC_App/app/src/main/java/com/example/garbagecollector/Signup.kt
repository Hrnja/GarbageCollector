package com.example.garbagecollector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.garbagecollector.ORMDatabase.GCUsers
import com.example.garbagecollector.viewModel.GCViewModel
import java.time.LocalDateTime

class Signup : AppCompatActivity() {

    private lateinit var name:EditText
    private lateinit var password:EditText
    private lateinit var email:EditText
    private lateinit var signup: Button
    private lateinit var gcViewModel:GCViewModel
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        name = findViewById(R.id.signup_name)
        password = findViewById(R.id.signup_password)
        email = findViewById(R.id.signup_email)
        signup = findViewById(R.id.signup_button)
        backButton = findViewById(R.id.back_button_signup)
        val activity = this@Signup

        gcViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(GCViewModel::class.java)

        backButton.setOnClickListener {
            val from = intent.getStringExtra("from")
            if(from == "FromLogin"){
                startActivity(Intent(this,Login::class.java))
                this.finish()
            }
            else if(from == "FromMainActivity") {
                startActivity(Intent(this, MainActivity::class.java))
                this.finish()
            }
        }

        signup.setOnClickListener {

            if(name.text.isNotEmpty() && email.text.isNotEmpty()  && password.text.isNotEmpty()) {
                val pname = name.text.toString()
                val pemail = email.text.toString()
                val ppassword = password.text.toString()
                val currentDateTime = LocalDateTime.now()
                val currentDate = currentDateTime.toLocalDate().toString()
                val currentTime = currentDateTime.toLocalTime().toString()


                gcViewModel.getIDforSignup(pname,pemail,ppassword).observeOnce(this, Observer {userID ->
                    if(userID == null) {
                        gcViewModel.addUser(GCUsers(pname,pemail,ppassword,currentDate, currentTime))
                        Toast.makeText(activity,"Successfully added user",Toast.LENGTH_LONG).show()
                        val intent = Intent(activity,PickControl::class.java)
                        intent.putExtra("source", "Signup")
                        intent.putExtra("name", pname)
                        startActivity(intent)
                        activity.finish()
                    }
                    else {
                        Toast.makeText(activity,"User exist in database", Toast.LENGTH_LONG).show()
                    }
                })
            }
            else if(name.text.isNotEmpty() && email.text.isNotEmpty() && password.text.isEmpty()) {
                Toast.makeText(activity,"Enter password", Toast.LENGTH_SHORT).show()
            }
            else if(name.text.isNotEmpty() && email.text.isEmpty() && password.text.isNotEmpty()) {
                Toast.makeText(activity,"Enter email", Toast.LENGTH_SHORT).show()
            }
            else if(name.text.isEmpty() && email.text.isNotEmpty() && password.text.isNotEmpty()) {
                Toast.makeText(activity,"Enter name", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(activity,"All fields are empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: Observer<T>) {
        observe(owner, object : Observer<T> {
            override fun onChanged(t: T) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

}