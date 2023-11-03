package com.betheng.clonesnapchatapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.IOException
import java.net.URL
import java.util.concurrent.Executors


class ViewSnapsActivity : AppCompatActivity() {

    private lateinit var viewSnapsImageView: ImageView
    private lateinit var msgTextView: TextView

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val dbUrl = "https://mock-snapchat-app-default-rtdb.asia-southeast1.firebasedatabase.app/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_snaps)

        viewSnapsImageView = findViewById(R.id.viewSnapImageView)
        msgTextView = findViewById(R.id.msgTextView)

        msgTextView.text = intent.getStringExtra("message")

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        lateinit var bitmap : Bitmap
        executor.execute {
            try {
                val url = URL(intent.getStringExtra("imageUrl"))
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            } catch (e: Exception) {
                e.printStackTrace()
            }

            handler.post {
                //UI Events
                viewSnapsImageView.setImageBitmap(bitmap)
            }
        }
    }
    override fun onBackPressed(){
        super.onBackPressed()

        FirebaseDatabase.getInstance(dbUrl).reference.child("users").child(auth.currentUser?.uid.toString()).child("snaps").child(intent.getStringExtra("snapKey").toString()).removeValue()
        FirebaseStorage.getInstance().reference.child("images").child(intent.getStringExtra("imageName").toString()).delete()

    }
}