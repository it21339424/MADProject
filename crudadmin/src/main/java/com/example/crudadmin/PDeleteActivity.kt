package com.example.crudadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.crudadmin.databinding.ActivityPdeleteBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PDeleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPdeleteBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        binding.PdeleteButton.setOnClickListener {
            val code=binding.deleteCode.text.toString()
            if(code.isNotEmpty()){
                deleteData(code)
            }else{
                Toast.makeText(this,"Please Enter the Code", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun deleteData(code: String){
        databaseReference=FirebaseDatabase.getInstance().getReference("product details")
        databaseReference.child(code).removeValue().addOnSuccessListener {
            binding.deleteCode.text.clear()
            Toast.makeText(this, "Deleted" , Toast.LENGTH_SHORT).show()

            val intent = Intent(this@PDeleteActivity, ProductActivity::class.java)
            startActivity(intent)
            finish()

        }.addOnFailureListener{
            Toast.makeText(this, "Failed to Delete" , Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, ProductActivity::class.java)
        startActivity(intent)
        finish()
    }
}

