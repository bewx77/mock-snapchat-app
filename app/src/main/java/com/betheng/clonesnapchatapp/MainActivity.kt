package com.betheng.clonesnapchatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    var isLogin : Boolean = true
    private lateinit var auth: FirebaseAuth

    private lateinit var titleTextView : TextView
    private lateinit var button : Button
    private lateinit var qnTextView : TextView
    private lateinit var changeStateTextView : TextView

    private var usernameEditText : EditText? = null
    private var passwordEditText : EditText? = null

    fun redirectToHome(view: View){
        if (isLogin){
            auth.signInWithEmailAndPassword(usernameEditText?.text.toString(), passwordEditText?.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, Log in
                        logIn()
                    } else {
                        Log.i("Info", "Log In Failed")
                        Toast.makeText(applicationContext, "Log in failed", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            auth.createUserWithEmailAndPassword(usernameEditText?.text.toString(), passwordEditText?.text.toString())
                .addOnCompleteListener(this){ task ->
                    if (task.isSuccessful) {
                        logIn()
                    } else {
                        Log.i("Info", "Sign Up Failed")
                        Toast.makeText(applicationContext, "Sign Up failed", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }
    fun changeState(view: View){
        if (isLogin){
            isLogin = false
            titleTextView.text = "Sign Up"
            button.text = "Sign Up"
            qnTextView.text = "Already have an account?"
            changeStateTextView.text = "Log In"

        } else {
            isLogin = true
            titleTextView.text = "Log In"
            button.text = "Log In"
            qnTextView.text = "New to SnapChat?"
            changeStateTextView.text = "Sign Up"
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleTextView = findViewById(R.id.titleTextView)
        button = findViewById(R.id.button)
        qnTextView = findViewById(R.id.qnTextView)
        changeStateTextView = findViewById(R.id.changeStateTextView)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            logIn()
        }
    }

    fun logIn(){
        val intent = Intent(this, SnapsActivity::class.java)
        startActivity(intent)
    }

}