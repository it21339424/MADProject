package com.example.crudadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.crudadmin.databinding.ActivityMainBinding
import com.example.crudadmin.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewShipping.setOnClickListener {
            val intent= Intent(this@ProfileActivity, ShippingActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.viewCard.setOnClickListener {
            val intent= Intent(this@ProfileActivity, CardMainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.Product.setOnClickListener {
            val intent= Intent(this@ProfileActivity, ProductActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}

