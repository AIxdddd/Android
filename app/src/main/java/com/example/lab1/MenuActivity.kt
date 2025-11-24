package com.example.lab1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MenuActivity : Activity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        dbHelper = DatabaseHelper(this)


        when (dbHelper.getUserTheme(sharedPreferences.getLong("current_user_id", -1))) {
            "dark" -> setTheme(R.style.Theme_Lab1_Dark)
            else -> setTheme(R.style.Theme_Lab1_Light)
        }
        setContentView(R.layout.menu)

        val adminButton = findViewById<Button>(R.id.button9)
        val profileButton = findViewById<Button>(R.id.button8)
        val errorButton = findViewById<Button>(R.id.button7)
        val closeButton = findViewById<Button>(R.id.button11)
        val startButton = findViewById<Button>(R.id.button10)
        val textField = findViewById<TextView>(R.id.textView9)
        val optionsButton = findViewById<Button>(R.id.button12)

        val isAdmin = sharedPreferences.getBoolean("current_user_is_admin", false)

        // Показываем кнопку администрирования только администраторам
        adminButton.visibility = if (isAdmin) Button.VISIBLE else Button.GONE

        val login = sharedPreferences.getString("current_user_login", "")
        textField.text = "Добро пожаловать, $login${if (isAdmin) " (Администратор)" else ""}"

        adminButton.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }

        errorButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("mailto:")
            }
            startActivity(intent)
        }

        profileButton.setOnClickListener {
            val profileIntent = Intent(this, ProfileActivity::class.java).apply {
                // Передаем ID текущего пользователя
                putExtra("user_id", sharedPreferences.getLong("current_user_id", -1))
            }
            startActivity(profileIntent)
        }
        optionsButton.setOnClickListener {
            val Intent = Intent(this, OptionsActivity::class.java).apply {
                // Передаем ID текущего пользователя
                putExtra("user_id", sharedPreferences.getLong("current_user_id", -1))
            }
            startActivity(Intent)
        }
        startButton.setOnClickListener {
            val Intent = Intent(this, GameActivity::class.java).apply {
                // Передаем ID текущего пользователя
                putExtra("user_id", sharedPreferences.getLong("current_user_id", -1))
            }
            startActivity(Intent)

        }

        closeButton.setOnClickListener {
            finish()
        }
    }

    override fun onRestart() {
        super.onRestart()
        recreate()
    }
}