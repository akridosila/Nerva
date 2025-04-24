package com.example.realnerva

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

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

        //val emamka = "${email.text.toString()} - ${amka.text.toString()}"

        registerButton.setOnClickListener {
            val username = email.text.toString()
            val password = passw.text.toString()
            val amka = amka.text.toString()
            val name = name.text.toString()
            val surname = surname.text.toString()

            if (dbHelper.registerUser(username, password, amka, name, surname)) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Registration failed. Username might already exist.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}