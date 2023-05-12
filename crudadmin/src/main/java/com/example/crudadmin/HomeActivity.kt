package com.example.crudadmin

import android.content.Intent
import android.os.Bundle
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class HomeActivity : AppCompatActivity() {

        private lateinit var gridView: GridView
        private lateinit var database: FirebaseDatabase
        private lateinit var productsRef: DatabaseReference
        private lateinit var productDataList: MutableList<ProductData>

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)

            gridView = findViewById(R.id.grid_view)
            database = FirebaseDatabase.getInstance()
            productsRef = database.getReference("product details")
            productDataList = mutableListOf()

            val adapter = ProductDataAdapter(this, productDataList)
            gridView.adapter = adapter

            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
                // Handle bottom bar item clicks
                when (menuItem.itemId) {
                    R.id.navigation_home -> {
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.navigation_profile -> {
                        // Handle Profile item click
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }


            productsRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    productDataList.clear()

                    for (productSnapshot in snapshot.children) {
                        val code = productSnapshot.child("code").getValue(String::class.java)
                        val product = productSnapshot.child("product").getValue(String::class.java)
                        val price = productSnapshot.child("price").getValue(String::class.java)
                        val description = productSnapshot.child("description").getValue(String::class.java)

                        if (product != null && price != null) {
                            val productData = ProductData(code, product, price, description)
                            productDataList.add(productData)
                        }
                    }

                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@HomeActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

