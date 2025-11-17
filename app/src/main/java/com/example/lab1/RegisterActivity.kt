package com.example.lab1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class RegisterActivity : Activity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        dbHelper = DatabaseHelper(this)


        when (dbHelper.getUserTheme(sharedPreferences.getLong("current_user_id", -1))) {
            "dark" -> setTheme(R.style.Theme_Lab1_Dark)
            else -> setTheme(R.style.Theme_Lab1_Light)
        }
        setContentView(R.layout.register)

        dbHelper = DatabaseHelper(this)

        val logField = findViewById<EditText>(R.id.editTextLog)
        val nameField = findViewById<EditText>(R.id.editTextName)
        val passwordField = findViewById<EditText>(R.id.editTextPassword)
        val birthDateField = findViewById<EditText>(R.id.editTextDate)
        val male = findViewById<CheckBox>(R.id.checkBoxMale)
        val female = findViewById<CheckBox>(R.id.checkBoxFemale)
        val image = findViewById<ImageButton>(R.id.imageButton)
        val addButton = findViewById<Button>(R.id.button3)

        addButton.isEnabled = false

        logField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val loginText = s?.toString() ?: ""
                val hasAtSymbol = loginText.contains("@")
                addButton.isEnabled = hasAtSymbol &&
                        nameField.text.toString().isNotEmpty() &&
                        passwordField.text.toString().isNotEmpty()

                if (!hasAtSymbol && loginText.isNotEmpty()) {
                    logField.error = getString(R.string.error_login_at_symbol)
                } else {
                    logField.error = null
                }
            }
        })

        // Добавляем валидацию для других полей
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val loginText = logField.text.toString()
                val hasAtSymbol = loginText.contains("@")
                addButton.isEnabled = hasAtSymbol &&
                        nameField.text.toString().isNotEmpty() &&
                        passwordField.text.toString().isNotEmpty()
            }
        }

        nameField.addTextChangedListener(textWatcher)
        passwordField.addTextChangedListener(textWatcher)

        addButton.setOnClickListener {
            val login = logField.text.toString()
            val name = nameField.text.toString()
            val password = passwordField.text.toString()
            val birthDate = birthDateField.text.toString()
            val gender = when {
                male.isChecked -> getString(R.string.checkbox_male)
                female.isChecked -> getString(R.string.checkbox_female)
                else -> ""
            }

            try {
                // Проверяем, есть ли уже пользователи в базе
                val isFirstUser = !dbHelper.hasUsers()

                val user = User(
                    login = login,
                    password = password,
                    name = name,
                    birthDate = birthDate,
                    gender = gender,
                    isAdmin = isFirstUser
                )

                val userId = dbHelper.addUser(user)
                if (userId != -1L) {
                    Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show()
                    if (isFirstUser) {
                        Toast.makeText(this, "Вы первый пользователь и стали администратором!", Toast.LENGTH_LONG).show()
                    }

                    // Автоматически входим после регистрации
                    val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
                    with(sharedPreferences.edit()) {
                        putLong("current_user_id", userId)
                        putString("current_user_login", user.login)
                        putBoolean("current_user_is_admin", user.isAdmin)
                        putString("current_user_theme", user.theme)
                        apply()
                    }

                    // Переходим в меню
                    val menuIntent = Intent(this, MenuActivity::class.java).apply {
                        putExtra("login", user.login)
                        putExtra("is_admin", user.isAdmin)
                    }
                    startActivity(menuIntent)
                    finish()
                } else {
                    Toast.makeText(this, "Ошибка регистрации. Возможно, логин уже занят.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Ошибка при регистрации: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("RegisterActivity", "Registration error", e)
            }

            Log.i(getString(R.string.register), getString(R.string.log_add_user))
        }

        image.setOnClickListener {
            Log.i(getString(R.string.register), getString(R.string.log_select_image))
        }

        male.setOnClickListener {
            if (male.isChecked) {
                female.isChecked = false
            }
        }

        female.setOnClickListener {
            if (female.isChecked) {
                male.isChecked = false
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
        recreate()
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