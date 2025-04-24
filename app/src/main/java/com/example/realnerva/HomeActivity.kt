package com.example.realnerva

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import androidx.activity.ComponentActivity

class HomeActivity : ComponentActivity() {
    private lateinit var emergencyButton: Button
    private lateinit var cancelButton: Button
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val painAssesmentButton = findViewById<Button>(R.id.painAssessmentButton)
        painAssesmentButton.setOnClickListener {
            val intent = Intent(this, PainAssessmentActivity::class.java)
            startActivity(intent)
        }

        val chatNowButton = findViewById<Button>(R.id.chatNowButton)
        chatNowButton.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }

        emergencyButton = findViewById(R.id.emergencyButton)

        emergencyButton.setOnClickListener {
            val intent = Intent(this, Emergency::class.java)
            startActivity(intent)
        }


    }


}