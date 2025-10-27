package com.example.lab1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MenuActivity:Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
        val Optionsbutton = findViewById<Button>(R.id.button12)
        val Profilebutton = findViewById<Button>(R.id.button8)
        val Errorbutton = findViewById<Button>(R.id.button7)
        val Closebutton = findViewById<Button>(R.id.button11)
        val Startbutton = findViewById<Button>(R.id.button10)
        val Textfield = findViewById<TextView>(R.id.textView9)

        val OptionsIntent = Intent(this, OptionsActivity::class.java)
        val ProfileIntent = Intent(this, ProfileActivity::class.java)

        val arguments: Bundle? = intent.extras
        val a = intent.extras?.getString("login")
        Textfield.text = "Добро пожаловать, $a"




        Errorbutton.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("mailto:")
            }
                startActivity(intent)
        }




        Optionsbutton.setOnClickListener {

            startActivity(OptionsIntent)

        }


        Profilebutton.setOnClickListener {
            startActivity(ProfileIntent)

        }

    }
}