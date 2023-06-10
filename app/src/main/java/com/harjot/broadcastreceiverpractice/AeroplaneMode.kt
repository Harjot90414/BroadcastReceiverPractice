package com.harjot.broadcastreceiverpractice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AeroplaneMode :BroadcastReceiver() {
    lateinit var bcrInterface: BcrInterface
    override fun onReceive(context: Context?, intent: Intent?) {
        var state = intent?.getBooleanExtra("state",false)?:false
        bcrInterface.OnAeroplaneMode(state)
        }
    fun initializeInterface(bcrInterface: BcrInterface){
        this.bcrInterface = bcrInterface
    }
}