package com.example.thriftee

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchingActivity : AppCompatActivity() {

    private lateinit var pdtRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var pdtList: ArrayList<ProductModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        pdtRecyclerView = findViewById(R.id.rvPdt)
        pdtRecyclerView.layoutManager = LinearLayoutManager(this)
        pdtRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        pdtList = arrayListOf<ProductModel>()

        getProductsData()

    }

    private fun getProductsData() {

        pdtRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Product")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pdtList.clear()
                if (snapshot.exists()){
                    for (pdtSnap in snapshot.children){
                        val pdtData = pdtSnap.getValue(ProductModel::class.java)
                        pdtList.add(pdtData!!)
                    }
                    val mAdapter = PdtAdapter(pdtList)
                    pdtRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : PdtAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, ProductDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("pdtId", pdtList[position].pdtId)
                            intent.putExtra("pdtName", pdtList[position].pdtName)
                            intent.putExtra("pdtPrice", pdtList[position].pdtPrice)
                            intent.putExtra("pdtDescription", pdtList[position].pdtDescription)
                            startActivity(intent)
                        }

                    })

                    pdtRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}