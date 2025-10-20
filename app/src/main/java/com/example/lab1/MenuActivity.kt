package com.example.lab1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MenuActivity:Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
        val Optionsbutton = findViewById<Button>(R.id.button12)
        val Profilebutton = findViewById<Button>(R.id.button8)
        val Errorbutton = findViewById<Button>(R.id.button7)
        val Closebutton = findViewById<Button>(R.id.button11)
        val Startbutton = findViewById<Button>(R.id.button10)

        val OptionsIntent = Intent(this, OptionsActivity::class.java)
        val ProfileIntent = Intent(this, ProfileActivity::class.java)







        Errorbutton.setOnClickListener {

            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse(getString(R.string.phone_number))
            }
            if (intent.resolveActivity(packageManager) != null){
                startActivity(intent)
            } else {
            // Если нет приложения для совершения звонка
            Log.e(getString(R.string.menu), "Нет приложения для совершения звонка")
        }


        }




        Optionsbutton.setOnClickListener {

            startActivity(OptionsIntent)

        }


        Profilebutton.setOnClickListener {
            startActivity(ProfileIntent)

        }

    }
}