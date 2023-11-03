package com.betheng.clonesnapchatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class UsersListActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private var programImages : IntArray = intArrayOf(R.drawable.snapchat_user_1, R.drawable.snapchat_user_2,R.drawable.snapchat_user_3,R.drawable.snapchat_user_4)
    private var programName : Array<String> = arrayOf("Beth", "Claire", "Aidan");
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)

        listView = findViewById(R.id.listView)
        var programAdapter : ProgramAdapter = ProgramAdapter(this, programImages, programName);
        listView.adapter = programAdapter
    }
}