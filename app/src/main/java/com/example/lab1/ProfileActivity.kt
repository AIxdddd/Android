package com.example.lab1

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ProfileActivity : Activity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences
    private var currentUserId: Long = -1
    private var isAdminView: Boolean = false
    private var viewedUserId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        dbHelper = DatabaseHelper(this)


        when (dbHelper.getUserTheme(sharedPreferences.getLong("current_user_id", -1))) {
            "dark" -> setTheme(R.style.Theme_Lab1_Dark)
            else -> setTheme(R.style.Theme_Lab1_Light)
        }
        setContentView(R.layout.profile)

        dbHelper = DatabaseHelper(this)
        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        currentUserId = sharedPreferences.getLong("current_user_id", -1)
        viewedUserId = intent.getLongExtra("user_id", currentUserId)
        isAdminView = intent.getBooleanExtra("is_admin_view", false)

        setupUI()
        loadUserData()
    }

    private fun setupUI() {
        val makeAdminButton = findViewById<Button>(R.id.buttonMakeAdmin)

        // Показываем кнопку "Сделать администратором" только если:
        // 1. Текущий пользователь - администратор
        // 2. Это не его собственный профиль
        // 3. Это просмотр из админки
        val isCurrentUserAdmin = sharedPreferences.getBoolean("current_user_is_admin", false)
        val isOwnProfile = viewedUserId == currentUserId

        if (isCurrentUserAdmin && !isOwnProfile && isAdminView) {
            makeAdminButton.visibility = View.VISIBLE
            makeAdminButton.setOnClickListener {
                dbHelper.updateUserAdminStatus(viewedUserId, true)
                Toast.makeText(this, "Пользователь назначен администратором", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            makeAdminButton.visibility = View.GONE
        }
    }

    private fun loadUserData() {
        val users = dbHelper.getAllUsers()
        val user = users.find { it.id == viewedUserId }

        user?.let {
            findViewById<TextView>(R.id.textView5).text = "Логин: ${it.login}"
            findViewById<TextView>(R.id.textView6).text = "Дата рождения: ${it.birthDate}"
            findViewById<TextView>(R.id.textView7).text = "ФИО: ${it.name}"
            findViewById<TextView>(R.id.textView8).text = "Пол: ${it.gender}"

            // Добавляем информацию о статусе администратора
            val adminStatusText = findViewById<TextView>(R.id.textViewAdminStatus)
            adminStatusText.text = if (it.isAdmin) "Статус: Администратор" else "Статус: Пользователь"
        }
    }
    override fun onRestart() {
        super.onRestart()
        recreate()
    }
}