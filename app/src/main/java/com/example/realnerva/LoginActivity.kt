package com.example.realnerva

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class LoginActivity : ComponentActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        dbHelper = DatabaseHelper(this)

        val loginButton = findViewById<Button>(R.id.loginButton)
        val emamka = findViewById<EditText>(R.id.emamka)
        val passw = findViewById<EditText>(R.id.pass)

        loginButton.setOnClickListener {
            val username = emamka.text.toString()
            val password = passw.text.toString()
            val amka = emamka.text.toString()

            if (dbHelper.validateUser(username, password , amka)) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}