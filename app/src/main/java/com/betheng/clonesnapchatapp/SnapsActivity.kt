package com.betheng.clonesnapchatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth

class SnapsActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var listView: ListView
    private var programImages : IntArray = intArrayOf(R.drawable.snapchat_user_1, R.drawable.snapchat_user_2,R.drawable.snapchat_user_3,R.drawable.snapchat_user_4)
    private var programName : Array<String> = arrayOf("Beth", "Claire", "Aidan");

    fun logOutBtnPressed(view: View){
        Log.i("Info", "Logout Button Pressed")
        auth.signOut()
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        auth.signOut()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snaps)

        listView = findViewById(R.id.listView)
        var programAdapter : ProgramAdapter = ProgramAdapter(this, programImages, programName);
        listView.adapter = programAdapter

    }
}