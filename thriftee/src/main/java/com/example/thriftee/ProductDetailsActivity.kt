package com.example.thriftee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var tvPdtId: TextView
    private lateinit var tvPdtName: TextView
    private lateinit var tvPdtPrice: TextView
    private lateinit var tvPdtDescription: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pruduct_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("pdtId").toString(),
                intent.getStringExtra("pdtName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("pdtId").toString()
            )
        }

    }

    private fun initView() {
        tvPdtId = findViewById(R.id.tvPdtId)
        tvPdtName = findViewById(R.id.tvPdtName)
        tvPdtPrice = findViewById(R.id.tvPdtPrice)
        tvPdtDescription = findViewById(R.id.tvPdtDescription)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvPdtId.text = intent.getStringExtra("pdtId")
        tvPdtName.text = intent.getStringExtra("pdtName")
        tvPdtPrice.text = intent.getStringExtra("pdtPrice")
        tvPdtDescription.text = intent.getStringExtra("pdtDescription")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Product").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Product data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        pdtId: String,
        pdtName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etPdtName = mDialogView.findViewById<EditText>(R.id.etPdtName)
        val etPdtPrice = mDialogView.findViewById<EditText>(R.id.etPdtPrice)
        val etPdtDescription = mDialogView.findViewById<EditText>(R.id.etPdtDescription)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etPdtName.setText(intent.getStringExtra("pdtName").toString())
        etPdtPrice.setText(intent.getStringExtra("pdtPrice").toString())
        etPdtDescription.setText(intent.getStringExtra("pdtDescription").toString())

        mDialog.setTitle("Updating $pdtName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updatePdtData(
                pdtId,
                etPdtName.text.toString(),
                etPdtPrice.text.toString(),
                etPdtDescription.text.toString()
            )

            Toast.makeText(applicationContext, "Product Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our text views
            tvPdtName.text = etPdtName.text.toString()
            tvPdtPrice.text = etPdtPrice.text.toString()
            tvPdtDescription.text = etPdtDescription.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updatePdtData(
        id: String,
        name: String,
        price: String,
        description: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Product").child(id)
        val pdtInfo = ProductModel(id, name, price, description)
        dbRef.setValue(pdtInfo)
    }

}