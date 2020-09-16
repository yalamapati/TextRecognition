package com.charan.sampletextrecognition

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_text_display.*


class TextDisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_display)

       intent?. let {
            val imageUri = intent.data!!
            selected_image.setImageURI(imageUri)
            processImageAndDisplayText(imageUri)
       }
    }

    private fun processImageAndDisplayText(imageUri: Uri) {
        val firebaseVisionImage = FirebaseVisionImage.fromFilePath(this, imageUri)
        val detector = FirebaseVision.getInstance()
            .onDeviceTextRecognizer
        val result = detector.processImage(firebaseVisionImage)
            .addOnSuccessListener { text ->
                image_text.text = text.text
            }
            .addOnFailureListener {
                image_text.text = it.localizedMessage
            }
    }
}