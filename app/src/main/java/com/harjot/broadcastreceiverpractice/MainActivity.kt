package com.harjot.broadcastreceiverpractice

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity(),BcrInterface {
    lateinit var aeroplaneMode: AeroplaneMode
    lateinit var internetCheck: InternetCheck
    lateinit var alertDialog: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alertDialog = AlertDialog.Builder(this)
        aeroplaneMode = AeroplaneMode()
        internetCheck = InternetCheck()
        internetCheck.initializeInterface(this)
        aeroplaneMode.initializeInterface(this)
        val intentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(aeroplaneMode,intentFilter)
        registerReceiver(internetCheck, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }
    override fun OnAeroplaneMode(isAirplaneModeEnabled: Boolean) {
        if(isAirplaneModeEnabled){
            alertDialog.setTitle("Airplane Mode")
            alertDialog.setMessage("Please Turn off your Airplane Mode")
            alertDialog.setCancelable(false)
            alertDialog.setPositiveButton("Done"){_,_->
             alertDialog.setCancelable(false)
            }
            alertDialog.show()
        }
        else{
            System.out.println("in mode else $isAirplaneModeEnabled")
        }
    }

    override fun Internet(isOnline: Boolean) {
        if(isOnline){
            Toast.makeText(this, "Online", Toast.LENGTH_SHORT).show()
        }
        else{
            System.out.println("in else $isOnline")
            Toast.makeText(this, "offline", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(aeroplaneMode)
        unregisterReceiver(internetCheck)
    }


}
