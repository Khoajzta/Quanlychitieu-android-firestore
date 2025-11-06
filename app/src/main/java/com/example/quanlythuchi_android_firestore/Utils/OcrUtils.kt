package com.example.quanlythuchi_android_firestore.Utils

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.tasks.await

suspend fun recognizeTextFromImage(context: Context, uri: Uri): String {

    return try {
        val image = InputImage.fromFilePath(context, uri)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        val result = recognizer.process(image).await()
        result.text
    } catch (e: Exception) {
        Log.e("OCR", "Error recognizing text: ${e.message}")
        "Không nhận diện được nội dung"
    }
}