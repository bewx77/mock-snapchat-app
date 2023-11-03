package com.betheng.clonesnapchatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class SnapsActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var listView: ListView
    private var programImages : IntArray = intArrayOf(R.drawable.snapchat_user_1, R.drawable.snapchat_user_2,R.drawable.snapchat_user_3,R.drawable.snapchat_user_4)
//    private var programName : ArrayList<String> = arrayListOf("Beth", "Claire", "Aidan");
    private var programName : ArrayList<String> = arrayListOf()
    private val dbUrl = "https://mock-snapchat-app-default-rtdb.asia-southeast1.firebasedatabase.app/"

    fun createSnapBtnPressed(view: View){
        var intent : Intent = Intent(this, CreateSnapActivity::class.java)
        startActivity(intent)
    }
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
        var programAdapter = ProgramAdapter(this, programImages, programName);
        listView.adapter = programAdapter

        FirebaseDatabase.getInstance(dbUrl).reference.child("users").child(auth.currentUser?.uid.toString()).child("snaps").addChildEventListener(object :ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                programName.add(snapshot.child("from").value.toString())
                programAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}

        })

    }
}