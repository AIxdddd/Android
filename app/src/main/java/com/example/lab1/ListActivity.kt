package com.example.lab1

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class ListActivity :Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_view)
        var b = 1;
        val button = findViewById<Button>(R.id.button)
        val myStringArray = ArrayList<String>()
        val textList = findViewById<ListView>(R.id.listView)
        val textAdapter: ArrayAdapter<String> = ArrayAdapter(this, R.layout.item,
            R.id.itemContent, myStringArray)
        textList.setAdapter(textAdapter)



        button.setOnClickListener {

            textAdapter.add("User_" + b)
            b++
        }

    }

}