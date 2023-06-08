package com.muthoknee.keytabuapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var cardUpload:CardView
    lateinit var cardHome:CardView
    lateinit var cardLogout:CardView
    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cardUpload = findViewById(R.id.Card_upload)
        cardHome = findViewById(R.id.Card_home)
        cardLogout = findViewById(R.id.Card_logout)
        mAuth = FirebaseAuth.getInstance()

        cardUpload.setOnClickListener {
            startActivity(Intent(this,UploadbookActivity::class.java))
        }

        cardHome.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }

        cardLogout.setOnClickListener {
            var alert = AlertDialog.Builder(this)
            alert.setTitle("Exiting the app")
            alert.setMessage("Are you sure you want to exit?")
            alert.setNegativeButton("No",null)
            alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->

                mAuth.signOut()
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            })
            alert.create().show()
        }
    }
}