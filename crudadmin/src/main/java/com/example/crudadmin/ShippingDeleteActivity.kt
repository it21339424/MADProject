package com.example.crudadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.crudadmin.databinding.ActivityShippingBinding
import com.example.crudadmin.databinding.ActivityShippingDeleteBinding
import com.example.crudadmin.databinding.ActivityUpdateBinding
import com.google.firebase.database.*
import com.example.crudadmin.ShippingData


class ShippingDeleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShippingDeleteBinding
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShippingDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Back button
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        databaseReference = FirebaseDatabase.getInstance().getReference("shipping details")

        val line1 = binding.sLine1
        val line2 = binding.sLine2
        val line3 = binding.sLine3
        val city = binding.sCity
        val mobile = binding.sTPno

        // Fetch data from the database
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Assuming the data is stored as a ShippingData object
                    val shippingData = dataSnapshot.getValue(ShippingData::class.java)

                    // Populate the EditText fields with the retrieved data
                    line1.setText(shippingData?.line1)
                    line2.setText(shippingData?.line2)
                    line3.setText(shippingData?.line3)
                    city.setText(shippingData?.city)
                    mobile.setText(shippingData?.mobileP)
                } else {
                    // Handle the case when the data does not exist
                    // Clear the EditText fields or show an error message
                    line1.text.clear()
                    line2.text.clear()
                    line3.text.clear()
                    city.text.clear()
                    mobile.text.clear()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that occurred while fetching the data
                Toast.makeText(this@ShippingDeleteActivity, "Failed to fetch data: " + databaseError.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    override fun onBackPressed() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }
}

