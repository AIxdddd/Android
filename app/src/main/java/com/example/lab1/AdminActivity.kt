package com.example.lab1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class AdminActivity : Activity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userList: ListView
    private lateinit var users: List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        dbHelper = DatabaseHelper(this)


        when (dbHelper.getUserTheme(sharedPreferences.getLong("current_user_id", -1))) {
            "dark" -> setTheme(R.style.Theme_Lab1_Dark)
            else -> setTheme(R.style.Theme_Lab1_Light)
        }
        setContentView(R.layout.admin)

        dbHelper = DatabaseHelper(this)
        userList = findViewById(R.id.adminListView)

        loadUsers()

        userList.setOnItemClickListener { _, _, position, _ ->
            val user = users[position]
            val profileIntent = Intent(this, ProfileActivity::class.java).apply {
                putExtra("user_id", user.id)
                putExtra("is_admin_view", true) // Флаг что это просмотр из админки
            }
            startActivity(profileIntent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        loadUsers() // Обновляем список при возвращении
    }

    private fun loadUsers() {
        users = dbHelper.getAllUsers()
        val userDisplayList = users.map { user ->
            "${user.name} (${user.login})${if (user.isAdmin) " - Администратор" else ""}"
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, userDisplayList)
        userList.adapter = adapter
    }
}