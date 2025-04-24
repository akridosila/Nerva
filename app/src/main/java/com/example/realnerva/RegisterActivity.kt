package com.example.realnerva

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import android.content.SharedPreferences

class RegisterActivity : ComponentActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        dbHelper = DatabaseHelper(this)

        val registerButton = findViewById<Button>(R.id.registerButton)
        val email = findViewById<EditText>(R.id.emailEditText)
        val passw = findViewById<EditText>(R.id.passwordEditText)
        val amka = findViewById<EditText>(R.id.amkaEditText)
        val name = findViewById<EditText>(R.id.nameEditText)
        val surname = findViewById<EditText>(R.id.surnameEditText)

        registerButton.setOnClickListener {
            val username = email.text.toString()
            val password = passw.text.toString()
            val amka = amka.text.toString()
            val name = name.text.toString()
            val surname = surname.text.toString()
            val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

            if (username.isNotEmpty() && password.isNotEmpty() && amka.isNotEmpty() && name.isNotEmpty() && surname.isNotEmpty()) {
                val user = User(username, password, amka, name, surname)

                // Local
                if (dbHelper.registerUser(username, password, amka, name, surname)) {
                    registerUserToServer(user)

                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Registration failed. Username might already exist.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUserToServer(user: User) {
        val api = RetrofitClient.instance
        api.registerUser(user).enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: retrofit2.Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "User registered on server.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@RegisterActivity, "Failed to register on server.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}