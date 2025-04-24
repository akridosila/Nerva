package com.example.realnerva

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.realnerva.R

class BodyChoiceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.body_choice)

        val maleButton = findViewById<Button>(R.id.maleButton)
        maleButton.setOnClickListener {
            val intent = Intent(this, MaleBodyType::class.java)
            startActivity(intent)
        }

        val femaleButton = findViewById<Button>(R.id.femaleButton)
        femaleButton.setOnClickListener {
            val intent = Intent(this, FemaleBodyType::class.java)
            startActivity(intent)
        }
    }
}