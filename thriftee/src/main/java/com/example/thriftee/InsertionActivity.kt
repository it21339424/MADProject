package com.example.thriftee

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etPdtName: EditText
    private lateinit var etPdtPrice: EditText
    private lateinit var etPdtDescription: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etPdtName = findViewById(R.id.etPdtName)
        etPdtPrice = findViewById(R.id.etPdtPrice)
        etPdtDescription = findViewById(R.id.etPdtDescription)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Product")

        btnSaveData.setOnClickListener {
            saveProductData()
        }
    }

    private fun saveProductData() {

        //getting values
        val pdtName = etPdtName.text.toString()
        val pdtPrice = etPdtPrice.text.toString()
        val pdtDescription = etPdtDescription.text.toString()

        if (pdtName.isEmpty()) {
            etPdtName.error = "Please enter product name"
        }
        if (pdtPrice.isEmpty()) {
            etPdtPrice.error = "Please enter price"
        }
        if (pdtDescription.isEmpty()) {
            etPdtDescription.error = "Please enter description"
        }

        val pdtId = dbRef.push().key!!
        val product = ProductModel(pdtId, pdtName, pdtPrice, pdtDescription)

        dbRef.child(pdtId).setValue(product)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                etPdtName.text.clear()
                etPdtPrice.text.clear()
                etPdtDescription.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}