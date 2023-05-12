package com.example.crudadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.crudadmin.databinding.ActivityDeleteBinding
import com.google.firebase.database.*

class DeleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the Firebase Realtime Database reference
        val databaseReference = FirebaseDatabase.getInstance().getReference("card details")

        // Add a ValueEventListener to the database reference
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Check if any data was retrieved
                if (dataSnapshot.exists()) {
                    // Get the value of the "card" child node
                    val card = dataSnapshot.child("card").getValue(String::class.java)

                    // Set the value of the "deleteCard" field
                    binding.deleteCard.setText(card)
                } else {
                    // No data was retrieved, display an error message
                    Toast.makeText(this@DeleteActivity, "No data found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // An error occurred, display an error message
                Toast.makeText(this@DeleteActivity, "Failed to fetch data: " + databaseError.message, Toast.LENGTH_SHORT).show()
            }
        })
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Set click listener for back button
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        binding.deleteButton.setOnClickListener {
            val card=binding.deleteCard.text.toString()
            if(card.matches("\\d{16}".toRegex())){1
                deleteData(card)
            }
            else
            {
                binding.deleteCard.error = "Please enter a valid 16-digit card number"
                binding.deleteCard.requestFocus()
                return@setOnClickListener
            }


        }
        val maxLength = 16 // Limit to 10 characters
        val inputFilter = InputFilter.LengthFilter(maxLength)
        binding.deleteCard.filters = arrayOf(inputFilter)
    }

    private fun deleteData(card: String){
        databaseReference=FirebaseDatabase.getInstance().getReference("card details")
        databaseReference.child(card).removeValue().addOnSuccessListener {
            binding.deleteCard.text.clear()
            Toast.makeText(this, "Deleted" , Toast.LENGTH_SHORT).show()

            val intent = Intent(this@DeleteActivity, CardMainActivity::class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener{
            Toast.makeText(this, "Failed to Delete" , Toast.LENGTH_SHORT).show()
        }


    }
    override fun onBackPressed() {
        val intent = Intent(this, CardMainActivity::class.java)
        startActivity(intent)
        finish()
    }


}

