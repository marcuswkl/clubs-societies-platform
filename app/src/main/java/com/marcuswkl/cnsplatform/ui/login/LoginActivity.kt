package com.marcuswkl.cnsplatform.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.marcuswkl.cnsplatform.R

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.student_id_field).text.toString()
        val password = findViewById<EditText>(R.id.password_field).text.toString()
        val login = findViewById<Button>(R.id.login_button)

        login.setOnClickListener {
            signIn(username, password)
        }

        auth = Firebase.auth

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun signIn(username: String, password: String) {

        val email = "$username@imail.sunway.edu.my"

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "Login Successful.", Toast.LENGTH_SHORT).show()
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Login Failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }

    }

    private fun updateUI(user: FirebaseUser?) {
        // Update user interface
    }

}