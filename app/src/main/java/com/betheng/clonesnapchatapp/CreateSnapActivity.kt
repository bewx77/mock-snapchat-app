package com.betheng.clonesnapchatapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.UUID


class CreateSnapActivity : AppCompatActivity() {

    private lateinit var snapImageView: ImageView;
    private val imageName = UUID.randomUUID().toString() + ".jpg"
    private lateinit var msgEditText: EditText;

    fun nextBtnPressed(view: View){
        // Get the data from an ImageView as bytes
        snapImageView.isDrawingCacheEnabled = true
        snapImageView.buildDrawingCache()
        val bitmap = (snapImageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val ref = FirebaseStorage.getInstance().reference.child("images").child(imageName)

        var uploadTask = ref.putBytes(data)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            if (taskSnapshot.metadata != null) {
                if (taskSnapshot.metadata!!.reference != null) {
                    val result = taskSnapshot.storage.downloadUrl
                    result.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()
                        Log.i("Info",imageUrl)
                        var intent = Intent(this, UsersListActivity::class.java)
                        intent.putExtra("imageName",imageName)
                        intent.putExtra("imageUrl", imageUrl)
                        intent.putExtra("message", msgEditText.text.toString())
                        startActivity(intent)
                    }
                }
            }
        }
    }

    fun chooseImgBtnPressed(view: View){
        Log.i("Info", "Button pressed")
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
        } else {
            getPhoto()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_snap)

        snapImageView = findViewById(R.id.snapImageView)
        msgEditText = findViewById(R.id.msgEditText)
    }

    fun getPhoto(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedImage = data!!.data
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                snapImageView.setImageBitmap(bitmap)
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto()
            }
        }
    }
}