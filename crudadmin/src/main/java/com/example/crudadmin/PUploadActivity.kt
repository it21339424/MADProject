package com.example.crudadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.crudadmin.databinding.ActivityPuploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PUploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPuploadBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPuploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
            }

        binding.saveButton.setOnClickListener {
            val code = binding.addCode.text.toString()
            val product = binding.addProduct.text.toString()
            val price = binding.addPrice.text.toString()
            val description = binding.addDescription.text.toString()

            if (code.isEmpty()) {
                binding.addCode.error = "Please enter the Product Code"
            } else if (product.isEmpty()) {
            binding.addProduct.error = "Please enter the Product name"
            } else if (price.isEmpty()) {
                binding.addPrice.error = "Please enter the price"
            } else if (description.isEmpty()) {
                binding.addDescription.error = "Please enter the description"
            }
            else {
                databaseReference =
                    FirebaseDatabase.getInstance().getReference("product details") //table name
                val products = ProductData(code, product, price, description)

                databaseReference.child(code).setValue(products).addOnSuccessListener {
                    binding.addCode.text.clear()
                    binding.addProduct.text.clear()
                    binding.addPrice.text.clear()
                    binding.addDescription.text.clear()


                    Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@PUploadActivity, ProductActivity::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

                }


            }
        }
    }
        override fun onBackPressed() {
            val intent = Intent(this, ProductActivity::class.java)
            startActivity(intent)
            finish()
        }
}