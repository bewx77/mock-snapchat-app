package com.betheng.clonesnapchatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
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
    private var snaps : ArrayList<DataSnapshot> = arrayListOf()
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
                snaps.add(snapshot)
                programAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                var index = 0
                for (snap: DataSnapshot in snaps) {
                    if (snap.key == snapshot.key) {
                        snaps.removeAt(index)
                        programName.removeAt(index)
                        programAdapter.notifyDataSetChanged()
                    }
                    index++
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}

        })

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                var snap = snaps[position]
                Log.i("Info", snap.toString())

                val intent = Intent(this, ViewSnapsActivity::class.java)
                intent.putExtra("message", snap.child("message").value as String)
                intent.putExtra("imageName", snap.child("imageName").value as String)
                intent.putExtra("imageUrl", snap.child("imageUrl").value as String)
                intent.putExtra("snapKey", snap.key as String)
                startActivity(intent)
        }
    }

}