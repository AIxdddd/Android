package com.example.lab1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import kotlin.toString

class LoginActivity:Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val Logfield = findViewById<EditText>(R.id.editTextLog2)
        val Passwordfield = findViewById<EditText>(R.id.editTextPassword2)
        val Enterbutton = findViewById<Button>(R.id.button4)
        val Regbutton = findViewById<Button>(R.id.button5)
        val Closebutton = findViewById<Button>(R.id.button6)


        val MenuIntent = Intent(this, MenuActivity::class.java)
        val RegIntent = Intent(this, RegisterActivity::class.java)





        Regbutton.setOnClickListener {

            startActivity(RegIntent)

        }


        Enterbutton.setOnClickListener {
            startActivity(MenuIntent)

        }


    }

    override fun onStart() {
        super.onStart()
        Log.i(R.string.login.toString(), "onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.i(R.string.login.toString(), "onResume")
    }
    override fun onRestart() {
        super.onRestart()
        Log.i(R.string.login.toString(), "onRestart")
    }
    override fun onPause() {
        super.onPause()
        Log.i(R.string.login.toString(), "onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.i(R.string.login.toString(), "onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i(R.string.login.toString(), "onDestroy")
    }

}