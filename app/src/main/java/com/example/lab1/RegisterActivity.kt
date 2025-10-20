package com.example.lab1

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton

class RegisterActivity:Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
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
                    logField.error = getString(R.string.error_login_at_symbol)
                } else {
                    logField.error = null
                }
            }
        })

        addButton.setOnClickListener {
            Log.i(getString(R.string.register),getString(R.string.log_add_user))
        }

        image.setOnClickListener {
            Log.i(getString(R.string.register),getString(R.string.log_select_image))
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
    override fun onStart() {
        super.onStart()
        Log.i(getString(R.string.register), "onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.i(getString(R.string.register), "onResume")
    }
    override fun onRestart() {
        super.onRestart()
        Log.i(getString(R.string.register), "onRestart")
    }
    override fun onPause() {
        super.onPause()
        Log.i(getString(R.string.register), "onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.i(getString(R.string.register), "onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i(getString(R.string.register), "onDestroy")
    }
}