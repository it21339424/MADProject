package com.example.crudadmin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.crudadmin.databinding.ActivityProductBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            // Handle bottom bar item clicks
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Handle Home item click
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

        binding.productUpload.setOnClickListener {
            val intent= Intent(this@ProductActivity, PUploadActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.productUpdate.setOnClickListener{
            val intent= Intent(this@ProductActivity, PUpdateActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.productDelete.setOnClickListener {
            val intent= Intent(this@ProductActivity, PDeleteActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}