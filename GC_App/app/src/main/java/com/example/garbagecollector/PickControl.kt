package com.example.garbagecollector

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class PickControl : AppCompatActivity() {

    private lateinit var automatic:Button
    private lateinit var manual:Button
    private lateinit var welcomeUser:TextView
    private lateinit var connect:Button
    private lateinit var showUser: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_control)

        automatic = findViewById(R.id.automatic_button)
        manual = findViewById(R.id.manual_button)
        welcomeUser = findViewById(R.id.welcome_user)
        connect = findViewById(R.id.connectButton)
        showUser = findViewById(R.id.showuser)
        val frameLayout: FrameLayout = findViewById(R.id.frameLayout)
        val relativeLayout:RelativeLayout = findViewById(R.id.RelativeLayoutID)

        val source = intent.getStringExtra("source")
        val name = intent.getStringExtra("name")

        frameLayout.visibility = View.GONE


        if(source == "Login"){
            welcomeUser.text = "Welcome $name. You currently control Garbage Collector robot"
        }
        else if(source == "Signup") {
            welcomeUser.text = "Welcome $name. You currently control Garbage Collector robot"
        }

        showUser.setOnClickListener {
            if(frameLayout.visibility == View.GONE){
                relativeLayout.visibility = View.GONE
                frameLayout.visibility = View.VISIBLE
                replaceFragment(RecViewFragment())
            }
            else {
                frameLayout.visibility = View.GONE
                relativeLayout.visibility = View.VISIBLE
            }
        }

        connect.setOnClickListener {
            connectToWiFi()
        }

        manual.setOnClickListener {
            startActivity(Intent(this, ManualMode::class.java))
            this.finish()
        }
        automatic.setOnClickListener {
            startActivity(Intent(this, AutomaticMode::class.java))
            this.finish()
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }

    private fun connectToWiFi() {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        val request = WifiNetworkSpecifier.Builder()
            .setSsid("NodeMCU")
            .setWpa2Passphrase("12345678")
            .build()

        val requestInfo = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .setNetworkSpecifier(request)
            .build()

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                // Connect to the network
                val connectivityManager =
                    applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                connectivityManager.bindProcessToNetwork(network)
            }
        }

        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.requestNetwork(requestInfo, callback)
    }

}