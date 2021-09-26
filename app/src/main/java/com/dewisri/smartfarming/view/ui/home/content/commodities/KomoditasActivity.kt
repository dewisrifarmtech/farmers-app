package com.dewisri.smartfarming.view.ui.home.content.commodities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dewisri.smartfarming.R
import com.dewisri.smartfarming.data.model.Kebun
import com.dewisri.smartfarming.view.ui.home.content.commodities.adapter.KomoditasAdapter
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.valdesekamdem.library.mdtoast.MDToast
import kotlinx.android.synthetic.main.activity_monitoring.*
import kotlinx.android.synthetic.main.layout_empty.*


class KomoditasActivity : AppCompatActivity() {

    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase
    private var dataList = ArrayList<Kebun>()
    private lateinit var mAdapter: KomoditasAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitoring)

        FirebaseApp.initializeApp(this)
        mAuth = FirebaseAuth.getInstance()
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabaseReference = mFirebaseInstance.getReference("Komoditas")
        getDataFromDatabase()
        whenRefresh()
        btnCreateCommodities.setOnClickListener {
            val input = Intent(this, InputKebunActivity::class.java)
            startActivity(input)
        }
        rv()
        fabAdd.setOnClickListener {
            val input = Intent(this, InputKebunActivity::class.java)
            startActivity(input)
        }

    }

    private fun rv() {
        layoutManager = LinearLayoutManager(this)
        rvCommodities.layoutManager = layoutManager
        rvCommodities.setHasFixedSize(true)
        mAdapter = KomoditasAdapter(dataList)
        mAdapter.notifyDataSetChanged()
        rvCommodities.adapter = mAdapter
        val itemDecoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        rvCommodities.addItemDecoration(itemDecoration)
    }

    private fun getDataFromDatabase() {
        val uid = mAuth.currentUser?.uid
        uid?.let {
            mDatabaseReference.child(it).child("data_kebun")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        try {
                            dataList.clear()

                            if (dataSnapshot.exists()) {
                                rvCommodities.visibility = View.VISIBLE
                                fabAdd.visibility = View.VISIBLE
                                conEmptyCommodities.visibility = View.GONE
                                shimmer_view_container.stopShimmerAnimation()
                                shimmer_view_container.visibility = View.GONE

                                for (mDataSnapshot in dataSnapshot.children) {
                                    val data = mDataSnapshot.getValue(Kebun::class.java)
                                    dataList.add(data!!)
                                        Log.d("a","test2" + dataList.add(data))

                                }
                                mAdapter = KomoditasAdapter(dataList)
                                rvCommodities.adapter = mAdapter
                                Log.d("a","success")
                                mAdapter.notifyDataSetChanged()
                            } else {
                                fabAdd.visibility = View.GONE
                                conEmptyCommodities.visibility = View.VISIBLE
                                rvCommodities.visibility = View.GONE
                                shimmer_view_container.stopShimmerAnimation()
                                shimmer_view_container.visibility = View.GONE
                                mAdapter.notifyDataSetChanged()
                            }



                        } catch (e: Exception) {
                            Log.e("MyListDataActivty", e.toString())
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                        MDToast.makeText(
                            applicationContext,
                            databaseError.details + " " + databaseError.message, Toast.LENGTH_LONG, MDToast.TYPE_ERROR
                        ).show()
                    }
                })
        }
    }

    public override fun onResume() {
        super.onResume()
        shimmer_view_container.startShimmerAnimation()

    }

    public override fun onPause() {
        shimmer_view_container.stopShimmerAnimation()
        super.onPause()
    }

    private fun whenRefresh() {

    }


}

