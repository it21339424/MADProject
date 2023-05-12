package com.example.crudadmin

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var productId: String
    private lateinit var database: FirebaseDatabase
    private lateinit var productRef: DatabaseReference
    private lateinit var productData: ProductData

    private lateinit var productNameTextView: TextView
    private lateinit var productPriceTextView: TextView
    private lateinit var productDescriptionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        // Get the selected product ID from the HomeActivity
        productId = intent.getStringExtra("productId") ?: ""

        // Initialize Firebase
        database = FirebaseDatabase.getInstance()
        productRef = database.getReference("product details").child(productId)

        // Initialize views
        productNameTextView = findViewById(R.id.product_name_text_view)
        productPriceTextView = findViewById(R.id.product_price_text_view)
        productDescriptionTextView = findViewById(R.id.product_description_text_view)

        // Retrieve the selected product's details from the database
        productRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productData = snapshot.getValue(ProductData::class.java) ?: return

                // Set the views with the selected product's details
                productNameTextView.text = productData.product
                productPriceTextView.text = productData.price
                productDescriptionTextView.text = productData.description
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProductDetailsActivity, "Failed to fetch product details", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

