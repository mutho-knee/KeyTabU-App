package com.muthoknee.keytabuapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var textview:TextView
    private lateinit var button:Button
    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var edtConfirmPassword:EditText
    private lateinit var edtName:EditText
    private lateinit var edtPhoneNumber:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        textview=findViewById(R.id.textView)
        button=findViewById(R.id.button)
        edtEmail=findViewById(R.id.EdtEmail)
        edtPassword=findViewById(R.id.EdtPassword)
        edtName=findViewById(R.id.EdtName)
        edtPhoneNumber=findViewById(R.id.EdtPhoneNumber)
        edtConfirmPassword=findViewById(R.id.EdtConfirmPassword)
        textview.setOnClickListener {
            val intent =Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }


        firebaseAuth = FirebaseAuth.getInstance()

        textview.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        button.setOnClickListener {
            val email = edtEmail.text.toString()
            val pass = edtPassword.text.toString()
            val name = edtName.text.toString()
            val phoneNumber = edtPhoneNumber.text.toString()
            val confirmPass = edtConfirmPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty() && name.isNotEmpty() && phoneNumber.isNotEmpty()) {
                if (pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            var dbRef = FirebaseDatabase.getInstance().getReference().child("Users/"+firebaseAuth.currentUser!!.uid)
                            var userData = User(name,email,phoneNumber,"",firebaseAuth.currentUser!!.uid)
                            dbRef.setValue(userData)
                            val intent = Intent(this@SignUpActivity,LoginActivity::class.java)
                            finish()
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }
    }