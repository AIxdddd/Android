package com.example.lab1
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class HelloActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helloact)
        val b = intArrayOf(0,0);
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val text = findViewById<TextView>(R.id.textView)

        button1.setOnClickListener {
            b[0]++;
            text.text = getString(R.string.click_counter_format, b[0], b[1])
        }
        button2.setOnClickListener {
            b[1]++;
            text.text = getString(R.string.click_counter_format, b[0], b[1])
        }

    }
}