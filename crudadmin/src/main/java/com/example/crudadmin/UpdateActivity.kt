package com.example.crudadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.example.crudadmin.databinding.ActivityMainBinding
import com.example.crudadmin.databinding.ActivityUpdateBinding
import com.example.crudadmin.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError


class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Back button
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        databaseReference = FirebaseDatabase.getInstance().getReference("card details")

        // Add a ValueEventListener to the database reference to fetch the data
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Check if the dataSnapshot contains any data

                if (dataSnapshot.exists()) {
                    // Get the values from the dataSnapshot and set them in the EditText fields
                    val card = dataSnapshot.child("card").value.toString()
                    val cvv = dataSnapshot.child("cvv").value.toString()
                    val expiry = dataSnapshot.child("expiry").value.toString()
                    val firstName = dataSnapshot.child("firstName").value.toString()
                    val lastName = dataSnapshot.child("lastName").value.toString()

                    binding.referenceCard.setText(card)
                    binding.updateCvv.setText(cvv)
                    binding.updateUploadDate.setText(expiry)
                    binding.updateFName.setText(firstName)
                    binding.updateLName.setText(lastName)
                            }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors that occur while fetching the data
                Log.d("FirebaseError", error.message)
            }
        })


        // Set click listener for back button
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
        binding.updateButton.setOnClickListener {
            val referenceCard = binding.referenceCard.text.toString()
            val cvv=binding.updateCvv.text.toString()
            val expiry=binding.updateUploadDate.text.toString()
            val firstName=binding.updateFName.text.toString()
            val lastName=binding.updateLName.text.toString()

            if (referenceCard.isBlank() || cvv.isBlank() || expiry.isBlank() || firstName.isBlank() || lastName.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (referenceCard.length != 16) {
                Toast.makeText(this, "Card number should be 16 digits", Toast.LENGTH_SHORT).show()
            } else if (cvv.length != 3) {
                Toast.makeText(this, "CVV should be 3 digits", Toast.LENGTH_SHORT).show()
            } else if (expiry.length != 5 || !expiry.matches("\\d{2}/\\d{2}".toRegex())) {
                Toast.makeText(this, "Expiry date should be in the format MM/YY", Toast.LENGTH_SHORT).show()
            } else {
                updateData(referenceCard, firstName, lastName, cvv, expiry)
            }

        }
    }
    private fun updateData(card:String, firstName: String, lastName:String, cvv:String, expiry:String){
        databaseReference= FirebaseDatabase.getInstance().getReference("card details")
        val user = mapOf <String, String>("firstName" to firstName, "lastName" to lastName,
            "card" to card, "cvv" to cvv, "expiry" to expiry)
        databaseReference.child(card).updateChildren(user).addOnSuccessListener {
            binding.referenceCard.text.clear()
            binding.updateFName.text.clear()
            binding.updateLName.text.clear()
            binding.updateCvv.text.clear()
            binding.updateUploadDate.text.clear()
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT ).show()

            val intent = Intent(this@UpdateActivity, CardMainActivity::class.java)
            startActivity(intent)
            finish()

        }.addOnFailureListener {
            Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT ).show()

        }
        val maxLength = 16 // Limit to 10 characters
        val inputFilter = InputFilter.LengthFilter(maxLength)
        binding.referenceCard.filters = arrayOf(inputFilter)
    }
    override fun onBackPressed() {
        val intent = Intent(this, CardMainActivity::class.java)
        startActivity(intent)
        finish()
    }
}