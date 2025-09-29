package com.example.lab1

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton

class LoginActivity:Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val logField = findViewById<EditText>(R.id.editTextLog)
        val nameField = findViewById<EditText>(R.id.editTextName)
        val passwordField = findViewById<EditText>(R.id.editTextPassword)
        val birthDateField = findViewById<EditText>(R.id.editTextDate)
        val male = findViewById<CheckBox>(R.id.checkBoxMale)
        val female = findViewById<CheckBox>(R.id.checkBoxFemale)
        val image = findViewById<ImageButton>(R.id.imageButton)
        val addButton = findViewById<Button>(R.id.button3)


        addButton.setOnClickListener {
            Log.i("Login","Добавляет пользователя")
        }

        image.setOnClickListener {
            Log.i("Login","Выбор изображения")
        }
        male.setOnClickListener {
            if(male.isChecked){
            if (female.isChecked){
                female.isChecked= false
            }
        }

    }
        female.setOnClickListener {
            if(female.isChecked){
                if (male.isChecked){
                    male.isChecked= false
                }
            }
        }
    }

}