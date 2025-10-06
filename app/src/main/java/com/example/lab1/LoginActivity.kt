package com.example.lab1

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        // Изначально блокируем кнопку
        addButton.isEnabled = false

        // Добавляем слушатель изменений текста в поле логина
        logField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                // Проверяем наличие символа "@" в логине
                val loginText = s?.toString() ?: ""
                val hasAtSymbol = loginText.contains("@")

                // Блокируем или разблокируем кнопку в зависимости от наличия "@"
                addButton.isEnabled = hasAtSymbol

                // Можно также добавить визуальную обратную связь
                if (!hasAtSymbol && loginText.isNotEmpty()) {
                    logField.error = "Логин должен содержать символ @"
                } else {
                    logField.error = null
                }
            }
        })

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