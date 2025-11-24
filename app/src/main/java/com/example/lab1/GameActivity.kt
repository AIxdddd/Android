package com.example.lab1

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random
import com.example.lab1.databinding.GameBinding
class GameActivity: Activity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var binding: GameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        dbHelper = DatabaseHelper(this)



        when (dbHelper.getUserTheme(sharedPreferences.getLong("current_user_id", -1))) {
            "dark" -> setTheme(R.style.Theme_Lab1_Dark)
            else -> setTheme(R.style.Theme_Lab1_Light)
        }
        binding = GameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rockButton.setOnClickListener {
            var randomNumber = Random.nextInt(1, 4)
            when (randomNumber){
                1 -> binding.enemyField.text = "Ход противника:\nкамень"
                2 -> binding.enemyField.text = "Ход противника:\nбумага"
                3 -> binding.enemyField.text = "Ход противника:\nножницы"
            }
            binding.enemyField.visibility = View.VISIBLE
            binding.textField.text=game("rock",1,randomNumber)

        }

        binding.paperButton.setOnClickListener {
            var randomNumber = Random.nextInt(1, 4)
            when (randomNumber){
                1 -> binding.enemyField.text = "Ход противника:\nкамень"
                2 -> binding.enemyField.text = "Ход противника:\nбумага"
                3 -> binding.enemyField.text = "Ход противника:\nножницы"
            }
            binding.enemyField.visibility = View.VISIBLE
            binding.textField.text=game("paper",2,randomNumber)
        }
        binding.scissorsButton.setOnClickListener {
            var randomNumber = Random.nextInt(1, 4)
            when (randomNumber){
                1 -> binding.enemyField.text = "Ход противника:\nкамень"
                2 -> binding.enemyField.text = "Ход противника:\nбумага"
                3 -> binding.enemyField.text = "Ход противника:\nножницы"
            }
            binding.enemyField.visibility = View.VISIBLE
            binding.textField.text=game("scissors",3,randomNumber)
        }






    }
    private fun game(a: String, b: Int , randomNumber: Int): String {
        var result = "победа"
        var enemy = "a"

        if (b == randomNumber){
            return("Ничья")
        }
        if ((b == 1) and (randomNumber == 3) ){
            return("Победа")
        }
        if ((b == 2) and (randomNumber == 1) ){
            return("Победа")
        }
        if ((b == 3) and (randomNumber == 2) ){
            return("Победа")
        }

        val v = getSystemService(VIBRATOR_SERVICE) as Vibrator

// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            //deprecated in API 26
            v.vibrate(500)
        }
        return "Поражение"

    }
}