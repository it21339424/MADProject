package com.example.crudadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.crudadmin.databinding.ActivityMainBinding
import com.example.crudadmin.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Set click listener for back button
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        binding.saveButton.setOnClickListener {
            val firstname = binding.uploadFName.text.toString().trim()
            val lastname = binding.uploadLName.text.toString().trim()
            val card = binding.uploadCard.text.toString().trim()
            val cvv = binding.uploadCvv.text.toString().trim()
            val expiry = binding.uploadDate.text.toString().trim()

            // Validate first name
            if (firstname.isEmpty()) {
                binding.uploadFName.error = "Please enter your first name"
                binding.uploadFName.requestFocus()
                return@setOnClickListener
            } else if (!firstname.matches("[a-zA-Z]+".toRegex())) {
                binding.uploadFName.error = "Please enter a valid first name"
                binding.uploadFName.requestFocus()
                return@setOnClickListener
            }

            // Validate last name
            if (lastname.isEmpty()) {
                binding.uploadLName.error = "Please enter your last name"
                binding.uploadLName.requestFocus()
                return@setOnClickListener
            } else if (!lastname.matches("[a-zA-Z]+".toRegex())) {
                binding.uploadLName.error = "Please enter a valid last name"
                binding.uploadLName.requestFocus()
                return@setOnClickListener
            }

            // Validate card number
            if (card.isEmpty()) {
                binding.uploadCard.error = "Please enter your card number"
                binding.uploadCard.requestFocus()
                return@setOnClickListener
            } else if (!card.matches("\\d{16}".toRegex())) {
                binding.uploadCard.error = "Please enter a valid 16-digit card number"
                binding.uploadCard.requestFocus()
                return@setOnClickListener
            }

            // Validate CVV
            if (cvv.isEmpty()) {
                binding.uploadCvv.error = "Please enter your CVV"
                binding.uploadCvv.requestFocus()
                return@setOnClickListener
            } else if (!cvv.matches("\\d{3}".toRegex())) {
                binding.uploadCvv.error = "Please enter a valid 3-digit CVV"
                binding.uploadCvv.requestFocus()
                return@setOnClickListener
            }

            // Validate expiry date
            if (expiry.isEmpty()) {
                binding.uploadDate.error = "Please enter your card expiry date"
                binding.uploadDate.requestFocus()
                return@setOnClickListener
            } else if (!expiry.matches("\\d{2}/\\d{2}".toRegex())) {
                binding.uploadDate.error = "Please enter a valid expiry date (MM/YY)"
                binding.uploadDate.requestFocus()
                return@setOnClickListener
            }

            // If all inputs are valid, save to database
            databaseReference = FirebaseDatabase.getInstance().getReference("card details") //table name
            val user = UserData(firstname, lastname, card, cvv, expiry)
            databaseReference.child(card).setValue(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_SHORT).show()
                    clearInputs()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save data.", Toast.LENGTH_SHORT).show()
                }
        }
        val maxLength = 16 // Limit to 10 characters
        val inputFilter = InputFilter.LengthFilter(maxLength)
        binding.uploadCard.filters = arrayOf(inputFilter)
    }

    private fun clearInputs() {
        binding.uploadFName.text.clear()
        binding.uploadLName.text.clear()
        binding.uploadCard.text.clear()
        binding.uploadCvv.text.clear()
        binding.uploadDate.text.clear()
    }
    override fun onBackPressed() {
        val intent = Intent(this, CardMainActivity::class.java)
        startActivity(intent)
        finish()
    }


}
