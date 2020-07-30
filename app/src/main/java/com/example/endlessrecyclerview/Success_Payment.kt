package com.example.endlessrecyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_success__payment.*

class Success_Payment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success__payment)
        btnHome.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            applicationContext.startActivity(intent)
        }
    }
}
