package com.example.lab1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : Activity() {

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
        setContentView(R.layout.login)

        dbHelper = DatabaseHelper(this)
        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        // Применяем сохраненную тему
        applyThemeFromPrefs()

        val logField = findViewById<EditText>(R.id.editTextLog2)
        val passwordField = findViewById<EditText>(R.id.editTextPassword2)
        val enterButton = findViewById<Button>(R.id.button4)
        val regButton = findViewById<Button>(R.id.button5)
        val closeButton = findViewById<Button>(R.id.button6)

        enterButton.setOnClickListener {
            val login = logField.text.toString()
            val password = passwordField.text.toString()

            val user = dbHelper.getUser(login, password)
            if (user != null) {
                // Сохраняем данные пользователя в SharedPreferences
                with(sharedPreferences.edit()) {
                    putLong("current_user_id", user.id)
                    putString("current_user_login", user.login)
                    putBoolean("current_user_is_admin", user.isAdmin)
                    putString("current_user_theme", user.theme)
                    apply()
                }

                // Применяем тему пользователя
                applyUserTheme(user.theme)

                val menuIntent = Intent(this, MenuActivity::class.java).apply {
                    putExtra("login", user.login)
                    putExtra("is_admin", user.isAdmin)
                }
                startActivity(menuIntent)
            } else {
                Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
            }
        }

        regButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        closeButton.setOnClickListener {
            finish()
        }
    }

    private fun applyThemeFromPrefs() {
        val theme = sharedPreferences.getString("app_theme", "light") ?: "light"
        applyTheme(theme)
    }

    private fun applyUserTheme(theme: String) {
        applyTheme(theme)
    }

    private fun applyTheme(theme: String) {
        when (theme) {
            "dark" -> setTheme(R.style.Theme_Lab1_Dark)
            else -> setTheme(R.style.Theme_Lab1_Light)
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
        recreate()
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