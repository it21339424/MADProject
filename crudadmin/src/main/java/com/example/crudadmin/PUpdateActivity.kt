package com.example.crudadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.crudadmin.databinding.ActivityPupdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPupdateBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPupdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        binding.updateButton.setOnClickListener {
            val code = binding.updateCode.text.toString()
            val product = binding.updateProduct.text.toString()
            val price=binding.updatePrice.text.toString()
            val description=binding.updateDescription.text.toString()

            updateData(code, product, price, description)
        }
    }
    private fun updateData(code:String, product:String, price: String, description:String){
        databaseReference= FirebaseDatabase.getInstance().getReference("product details")
        val products = mapOf <String, String>("code" to code, "product" to product, "price" to price, "description" to description
            )
        databaseReference.child(code).updateChildren(products).addOnSuccessListener {
            binding.updateCode.text.clear()
            binding.updateProduct.text.clear()
            binding.updatePrice.text.clear()
            binding.updateDescription.text.clear()

            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT ).show()

            val intent = Intent(this@PUpdateActivity, ProductActivity::class.java)
            startActivity(intent)
            finish()

        }.addOnFailureListener {
            Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT ).show()

        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, ProductActivity::class.java)
        startActivity(intent)
        finish()
    }
}