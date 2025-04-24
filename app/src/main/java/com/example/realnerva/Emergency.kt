package com.example.realnerva

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import androidx.activity.ComponentActivity

class Emergency : ComponentActivity() {
    private lateinit var emergencyButton: Button
    private lateinit var cancelButton: Button
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)

        emergencyButton = findViewById(R.id.emergencyButton)
        cancelButton = findViewById(R.id.cancelButton)

        startEmergencyCountdown()

        emergencyButton.setOnClickListener {
            startEmergencyCountdown()
        }

        cancelButton.setOnClickListener {
            cancelEmergencyCountdown()
            val newIntent = Intent(this, HomeActivity::class.java)
            startActivity(newIntent)
        }
    }

    private fun startEmergencyCountdown() {
        emergencyButton.isEnabled = false
        cancelButton.isEnabled = true
        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                emergencyButton.text = "Wait ${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                emergencyButton.text = "Emergency"
                emergencyButton.isEnabled = true
                cancelButton.isEnabled = false
                callEmergencyNumber()
            }
        }.start()
    }

    private fun cancelEmergencyCountdown() {
        countDownTimer?.cancel()
        emergencyButton.text = "Emergency"
        emergencyButton.isEnabled = true
        cancelButton.isEnabled = false
    }

    private fun callEmergencyNumber() {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:6987672432")
        }
        startActivity(intent)
    }
}