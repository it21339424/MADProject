package com.example.crudadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.crudadmin.databinding.ActivityShippingBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ShippingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShippingBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShippingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Set click listener for back button
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        binding.btnShipping.setOnClickListener {
            val line1 = binding.sLine1.text.toString().trim()
            val line2 = binding.sLine2.text.toString().trim()
            val line3 = binding.sLine3.text.toString().trim()
            val city = binding.sCity.text.toString().trim()
            val mobileP = binding.sTPno.text.toString().trim()

            // Validate first name
            if (line1.isEmpty()) {
                binding.sLine1.error = "This field is required"
                binding.sLine1.requestFocus()
                return@setOnClickListener
            }
            // Validate last name
            if (line2.isEmpty()) {
                binding.sLine2.error = "This field is required"
                binding.sLine2.requestFocus()
                return@setOnClickListener
            }

            // Validate city
            if (city.isEmpty()) {
                binding.sCity.error = "Please enter your card number"
                binding.sCity.requestFocus()
                return@setOnClickListener
            }

            // Validate CVV
            if (mobileP.isEmpty()) {
                binding.sTPno.error = "Please enter your mobile no"
                binding.sTPno.requestFocus()
                return@setOnClickListener
            } else if (!mobileP.matches("\\d{10}".toRegex())) {
                binding.sTPno.error = "Please enter a valid mobile no"
                binding.sTPno.requestFocus()
                return@setOnClickListener
            }

            // If all inputs are valid, save to database
            databaseReference = FirebaseDatabase.getInstance().getReference("shipping details") //table name
            val shipping = ShippingData(line1, line2, line2, city, mobileP)

            databaseReference.child(line1).setValue(shipping)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_SHORT).show()
                    clearInputs()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save data.", Toast.LENGTH_SHORT).show()
                }

        }
        binding.btnShippingDelete.setOnClickListener {
            val intent= Intent(this@ShippingActivity, ShippingDeleteActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun clearInputs() {
        binding.sLine1.text.clear()
        binding.sLine2.text.clear()
        binding.sLine3.text.clear()
        binding.sCity.text.clear()
        binding.sTPno.text.clear()
    }

    override fun onBackPressed() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }
}
