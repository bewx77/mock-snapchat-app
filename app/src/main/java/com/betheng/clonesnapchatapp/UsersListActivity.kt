package com.betheng.clonesnapchatapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class UsersListActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private val dbUrl = "https://mock-snapchat-app-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private var programImages : IntArray = intArrayOf(R.drawable.snapchat_user_1, R.drawable.snapchat_user_2,R.drawable.snapchat_user_3,R.drawable.snapchat_user_4)
//    private var programName : Array<String> = arrayOf("Beth", "Claire", "Aidan");
    private var programName : ArrayList<String> = arrayListOf()
    private var keys : ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)

        listView = findViewById(R.id.listView)
        var programAdapter = ProgramAdapter(this, programImages, programName);
        listView.adapter = programAdapter

        FirebaseDatabase.getInstance(dbUrl).reference.child("users").addChildEventListener( object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val email = snapshot.child("email").value as String
                programName.add(email)
                keys.add(snapshot.key.toString())
                Log.i("Info", programName.toString())
                programAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })

        listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            var name = programName[position]
            val snapMap: Map<String,String> = mapOf("from" to FirebaseAuth.getInstance().currentUser?.email.toString(), "imageName" to intent.getStringExtra("imageName").toString(), "imageUrl" to intent.getStringExtra("imageUrl").toString(), "message" to intent.getStringExtra("message").toString() )
            Log.i("Info",name )
            FirebaseDatabase.getInstance(dbUrl).reference.child("users").child(keys[position]).child("snaps").push().setValue(snapMap)
            // .push --> adds a child with random name

            val intent = Intent(this, SnapsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}