package com.harjot.broadcastreceiverpractice

import android.app.Dialog
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.harjot.broadcastreceiverpractice.databinding.AirplaneModeDialogBinding

class MainActivity : AppCompatActivity(),BcrInterface {
    lateinit var aeroplaneMode: AeroplaneMode
    lateinit var internetCheck: InternetCheck
    lateinit var dialog : Dialog
    lateinit var dialogBinding : AirplaneModeDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dialog = Dialog(this)
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
           dialogBinding = AirplaneModeDialogBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog.setCancelable(false)
            dialog.show()
        }
        else{
            dialog.dismiss()
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
