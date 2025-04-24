package com.example.realnerva

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {

    val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = Constants.apiKey
    )

    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }

    init {

        messageList.add(MessageModel("Γεία σας, είμαι το Nerva Bot. Είμαι ο πρωσοπικός σας βοηθός και σύμμαχος στον αγώνα σας ενάντιας του χρόνιου πόνου... Πως θα μπορούσα να σας βοηθήσω σήμερα; 😁", "Model"))
    }


    fun initializeWithContext(context: List<String>) {
        context.forEach { answer ->
            messageList.add(MessageModel(answer, "User"))
        }
    }

    fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role) { text(it.message) }
                    }.toList()
                )
                messageList.add(MessageModel(question, "User"))
                messageList.add(MessageModel("Typing...", "Model"))

                val response = chat.sendMessage(question)
                if (response != null && response.text != null) {
                    messageList.removeAt(messageList.size - 1)
                    messageList.add(MessageModel(response.text.toString(), "Model"))
                } else {
                    throw Exception("Unexpected response: $response")
                }
            } catch (e: Exception) {
                messageList.removeAt(messageList.size - 1)
                messageList.add(MessageModel("Error: ${e.message}", "Model"))
                Log.e("ChatViewModel", "Error sending message", e)
            }
        }
    }
}