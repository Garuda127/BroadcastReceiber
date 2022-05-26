package com.garuda127.broadcastreceiber.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.view.Gravity
import android.widget.EditText
import android.widget.Toast
import com.garuda127.broadcastreceiber.MainActivity
import com.garuda127.broadcastreceiber.R

//broadcast receiver (phone)
class CallReceiver: BroadcastReceiver() {
    var numero: String? = null
    var mensaje: String? = null
    override fun onReceive(p0: Context?, p1: Intent?) {

        if (p1!!.getStringExtra(TelephonyManager.EXTRA_STATE)== TelephonyManager.EXTRA_STATE_OFFHOOK){
            showToastmsg(p0!!,"Llamada Iniciada")
        }else if (
            p1!!.getStringExtra(TelephonyManager.EXTRA_STATE)== TelephonyManager.EXTRA_STATE_IDLE){
            showToastmsg(p0!!,"Llamada Terminada")
        }else if (
            p1!!.getStringExtra(TelephonyManager.EXTRA_STATE)== TelephonyManager.EXTRA_STATE_RINGING){
            showToastmsg(p0!!,"Llamada entrante")
            try {
                var p:SharedPreferences=p0!!.getSharedPreferences("datos",Context.MODE_PRIVATE)
                numero=p.getString("numero","")
                mensaje=p.getString("mensaje","")
                if (numero!=null && mensaje!=null){
                    var smsManager:SmsManager=SmsManager.getDefault()
                    smsManager.sendTextMessage(numero,null,mensaje,null,null)
                }
            }catch (e:Exception){
                e.printStackTrace()
            }

        }


    }



    fun showToastmsg(context: Context, msg: String) {
        val toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }


}