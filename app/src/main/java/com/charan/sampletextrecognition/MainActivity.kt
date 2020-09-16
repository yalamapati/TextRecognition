package com.charan.sampletextrecognition

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        add_image.setOnClickListener { openGallery() }
    }

    private fun openGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                //show popup to request runtime permission
                requestPermissions(permissions, GALLERY_PERMISSION_REQUEST_CODE)
            } else {
                startGalleryIntent()
            }
        }
    }

    private fun startGalleryIntent() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = ACTION_PICK_IMAGE_TYPE
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode ==  Activity.RESULT_OK) {
            if(requestCode == GALLERY_REQUEST_CODE) {
                startTextDisplayActivity(data?.data)
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            GALLERY_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    startGalleryIntent()
                } else {
                    Toast.makeText(this, "Gallery permission denied", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun startTextDisplayActivity(data: Uri?) {
        val textDisplayIntent = Intent(this, TextDisplayActivity::class.java)
        textDisplayIntent.data = data
        startActivity(textDisplayIntent)
    }
}