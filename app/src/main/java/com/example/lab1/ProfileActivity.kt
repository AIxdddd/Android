package com.example.lab1

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.lab1.databinding.ProfileBinding

class ProfileActivity : Activity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences
    private var currentUserId: Long = -1
    private var isAdminView: Boolean = false
    private var viewedUserId: Long = -1
    private lateinit var binding: ProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        dbHelper = DatabaseHelper(this)


        when (dbHelper.getUserTheme(sharedPreferences.getLong("current_user_id", -1))) {
            "dark" -> setTheme(R.style.Theme_Lab1_Dark)
            else -> setTheme(R.style.Theme_Lab1_Light)
        }
        binding = ProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)
        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        currentUserId = sharedPreferences.getLong("current_user_id", -1)
        viewedUserId = intent.getLongExtra("user_id", currentUserId)
        isAdminView = intent.getBooleanExtra("is_admin_view", false)

        setupUI()
        loadUserData()
    }

    private fun setupUI() {

        val isCurrentUserAdmin = sharedPreferences.getBoolean("current_user_is_admin", false)
        val isOwnProfile = viewedUserId == currentUserId

        if (isCurrentUserAdmin && !isOwnProfile && isAdminView) {
            binding.makeAdminButton.visibility = View.VISIBLE
            binding.makeAdminButton.setOnClickListener {
                Thread {
                    dbHelper.updateUserAdminStatus(viewedUserId, true)
                    runOnUiThread {
                        Toast.makeText(
                            this,
                            "Пользователь назначен администратором",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }.start()
            }
        } else {
            binding.makeAdminButton.visibility = View.GONE
        }
    }

    private fun loadUserData() {
        Thread {
            val users = dbHelper.getAllUsers()
            val user = users.find { it.id == viewedUserId }
            runOnUiThread {
            user?.let {
                binding.login.text = "Логин: ${it.login}"
                binding.dataOfBirth.text = "Дата рождения: ${it.birthDate}"
                binding.name.text = "ФИО: ${it.name}"
                binding.gender.text = "Пол: ${it.gender}"

                // Добавляем информацию о статусе администратора
                binding.textViewAdminStatus.text =
                    if (it.isAdmin) "Статус: Администратор" else "Статус: Пользователь"
            }
        }
        }.start()
    }
    override fun onRestart() {
        super.onRestart()
        recreate()
    }
}