package com.marcuswkl.cnsplatform.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.marcuswkl.cnsplatform.MainActivity

import com.marcuswkl.cnsplatform.R

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth // Declare Firebase Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        auth = Firebase.auth // Initialise Firebase Auth

        val usernameEditText = findViewById<EditText>(R.id.student_id_field)
        val passwordEditText = findViewById<EditText>(R.id.password_field)

        val login = findViewById<Button>(R.id.login_button)

        login.setOnClickListener {

            Toast.makeText(applicationContext, "Login Clicked.", Toast.LENGTH_SHORT).show()

            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (validateForm(username, password)) {
                logIn(username, password)
            }

        }

        supportActionBar?.hide()

    }

    // Update UI if user is signed in
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    // Sign in user with credentials
    private fun logIn(username: String, password: String) {

        val email = "$username@imail.sunway.edu.my"

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Login Successful.", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(applicationContext, "Login Failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }

    }

    // Update user interface for signed in user
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // Validate form before sign in
    private fun validateForm(username: String, password: String): Boolean {

        var isValid = true

        if (username.isEmpty()) {
            Toast.makeText(applicationContext, "Invalid Username", Toast.LENGTH_SHORT).show()
            isValid = false
        } else {
            Toast.makeText(applicationContext, username, Toast.LENGTH_SHORT).show()
        }

        if (password.isEmpty()) {
            Toast.makeText(applicationContext, "Invalid Password", Toast.LENGTH_SHORT).show()
            isValid = false
        } else {
            Toast.makeText(applicationContext, password, Toast.LENGTH_SHORT).show()
        }

        return isValid

    }

}