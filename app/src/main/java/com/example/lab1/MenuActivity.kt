package com.example.lab1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.lab1.databinding.MenuBinding
class MenuActivity : Activity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var binding: MenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        dbHelper = DatabaseHelper(this)



        when (dbHelper.getUserTheme(sharedPreferences.getLong("current_user_id", -1))) {
            "dark" -> setTheme(R.style.Theme_Lab1_Dark)
            else -> setTheme(R.style.Theme_Lab1_Light)
        }
        binding = MenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val isAdmin = sharedPreferences.getBoolean("current_user_is_admin", false)

        // Показываем кнопку администрирования только администраторам
        binding.adminButton.visibility = if (isAdmin) Button.VISIBLE else Button.GONE

        val login = sharedPreferences.getString("current_user_login", "")
        binding.textField2.text = "Добро пожаловать, $login${if (isAdmin) " (Администратор)" else ""}"

        binding.adminButton.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }

        binding.errorButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("mailto:")
            }
            startActivity(intent)
        }

        binding.profileButton.setOnClickListener {
            val profileIntent = Intent(this, ProfileActivity::class.java).apply {
                // Передаем ID текущего пользователя
                putExtra("user_id", sharedPreferences.getLong("current_user_id", -1))
            }
            startActivity(profileIntent)
        }
        binding.optionsButton.setOnClickListener {
            val Intent = Intent(this, OptionsActivity::class.java).apply {
                // Передаем ID текущего пользователя
                putExtra("user_id", sharedPreferences.getLong("current_user_id", -1))
            }
            startActivity(Intent)
        }
        binding.startButton.setOnClickListener {
            val Intent = Intent(this, GameActivity::class.java).apply {
                // Передаем ID текущего пользователя
                putExtra("user_id", sharedPreferences.getLong("current_user_id", -1))
            }
            startActivity(Intent)

        }

        binding.closeButton2.setOnClickListener {
            finish()
        }
    }

    override fun onRestart() {
        super.onRestart()
        recreate()
    }
}