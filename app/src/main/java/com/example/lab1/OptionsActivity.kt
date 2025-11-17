package com.example.lab1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast

class OptionsActivity : Activity() {

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

        //setTheme(R.style.Theme_Lab1_Light)
        setContentView(R.layout.options)



        val themeSwitch = findViewById<Switch>(R.id.switch1)
        val applyButton = findViewById<Button>(R.id.button13)
        // Устанавливаем текущее состояние переключателя
        val currentTheme = sharedPreferences.getString("app_theme", "light") ?: "light"
        themeSwitch.isChecked = currentTheme == "dark"

        themeSwitch.setOnClickListener {
            val theme = if (themeSwitch.isChecked) "dark" else "light"

            // Сохраняем тему в SharedPreferences для неавторизованных пользователей
            with(sharedPreferences.edit()) {
                putString("app_theme", theme)
                apply()
            }

            // Если пользователь авторизован, сохраняем тему в БД
            val currentUserId = sharedPreferences.getLong("current_user_id", -1)
            if (currentUserId != -1L) {
                dbHelper.updateUserTheme(currentUserId, theme)
            }



            Toast.makeText(this, "Тема изменена", Toast.LENGTH_SHORT).show()
            recreate() // Перезагружаем activity для применения темы





        }






    }
}