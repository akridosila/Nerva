package com.example.realnerva

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

class PainAssessmentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            PainAssessmentScreen { answers ->
                val intent = Intent(this, ChatActivity::class.java).apply {
                    putStringArrayListExtra("answers", ArrayList(answers))
                }
                startActivity(intent)
                finish()
            }
        }
    }
}

@Composable
fun PainAssessmentScreen(onSubmit: (List<String>) -> Unit) {
    val painDescriptors = listOf("Flickering", "Quivering", "Pulsing", "Throbbing", "Beating", "Pounding")
    val evaluative = listOf("Mild", "Discomforting", "Distressing", "Horrible", "Excruciating")
    val miscellaneous = listOf("Spreading", "Radiating", "Penetrating", "Piercing", "Tight")

    var selectedPainDescriptor by remember { mutableStateOf("") }
    var selectedEvaluative by remember { mutableStateOf("") }
    var selectedMiscellaneous by remember { mutableStateOf("") }
    var painIntensity by remember { mutableStateOf(1) }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0FFFF))
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Το McGill Pain Questionnaire (MPQ) είναι σχεδιασμένο για την εκτίμηση της ποιότητας και την έντασης τού πόνου. Παρακαλώ απαντήστε σε κάθε επιλογή με βάση την πιο πρόσφατη εμπειρία πόνου.",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp, top = 20.dp)
        )

        PainSection("Select the words that best describe your pain...", painDescriptors) { selectedPainDescriptor = it }
        PainSection("Evaluative", evaluative) { selectedEvaluative = it }
        PainSection("Miscellaneous", miscellaneous) { selectedMiscellaneous = it }

        Text(
            text = "Indicate the intensity of your pain on the scale below.",
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Slider(
            value = painIntensity.toFloat(),
            onValueChange = { painIntensity = it.toInt() },
            valueRange = 1f..10f,
            steps = 8,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            (1..10).forEach {
                Text(text = it.toString(), color = Color.Black)
            }
        }

        if (showError) {
            Text(
                text = "Παρακαλώ επιλέξτε μια επιλογή για κάθε ερώτηση.",
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                if (selectedPainDescriptor.isNotEmpty() && selectedEvaluative.isNotEmpty() && selectedMiscellaneous.isNotEmpty()) {
                    val answers = listOf(
                        "Pain Descriptor: $selectedPainDescriptor",
                        "Evaluative: $selectedEvaluative",
                        "Miscellaneous: $selectedMiscellaneous",
                        "Pain intensity: $painIntensity"
                    )
                    onSubmit(answers)
                } else {
                    showError = true
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Continue")
        }
    }
}

@Composable
fun PainSection(title: String, options: List<String>, onSelect: (String) -> Unit) {
    var selectedOption by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(text = title, fontSize = 16.sp, color = Color.Black, modifier = Modifier.padding(bottom = 8.dp))
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        selectedOption = option
                        onSelect(option)
                    }
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = {
                        selectedOption = option
                        onSelect(option)
                    }
                )
                Text(text = option, fontSize = 14.sp, color = Color.Black, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}