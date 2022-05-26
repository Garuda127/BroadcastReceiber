package com.garuda127.broadcastreceiber
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.garuda127.broadcastreceiber.receiver.CallReceiver


class MainActivity : AppCompatActivity() {

    var call: CallReceiver?=null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //permission
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) !=
            PackageManager.PERMISSION_GRANTED) {
           ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_PHONE_STATE), 369)
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS), 369)
        }



        findViewById<Button>(R.id.btnSendSMS).setOnClickListener {
            //insertar para mandar mensaje
            sendSMS()
        }

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            //insertar para guardar datos
            guardardatos()
        }
        //broadcast receiver (sms)

        val intentFilter = IntentFilter()
        intentFilter.addAction(TelephonyManager.EXTRA_STATE_RINGING)

        call=CallReceiver()
        registerReceiver(call, intentFilter)


    }



    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    fun sendSMS(){
        var mensaje = findViewById<EditText>(R.id.txtMensaje).text.toString()
        var numero = findViewById<EditText>(R.id.txtNumero).text.toString()
        var obj = SmsManager.getDefault()
        obj.sendTextMessage(numero,null, mensaje,null,null)


    }

    fun guardardatos(){
        var mensaje = findViewById<EditText>(R.id.txtMensaje).text.toString()
        var numero = findViewById<EditText>(R.id.txtNumero).text.toString()
        var preference:SharedPreferences = getSharedPreferences("datos",Context.MODE_PRIVATE)
        var editor:SharedPreferences.Editor = preference.edit()
        editor.putString("numero",numero)
        editor.putString("mensaje",mensaje)
        editor.commit()
    }

}





