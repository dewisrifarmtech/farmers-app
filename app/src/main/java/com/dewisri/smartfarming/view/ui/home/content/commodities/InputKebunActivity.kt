package com.dewisri.smartfarming.view.ui.home.content.commodities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dewisri.smartfarming.R
import com.dewisri.smartfarming.data.model.Kebun
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.valdesekamdem.library.mdtoast.MDToast
import kotlinx.android.synthetic.main.fragment_add_kebun.*

class InputKebunActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add_kebun)
        sharedPreferences = getSharedPreferences("dewi_sri", Context.MODE_PRIVATE)
        initKebun()

    }

    private fun initKebun() {
        btn_add_kebun.setOnClickListener {
           initFitur()
        }
    }

    private fun initFitur() {
        val kebun = edt_kebun_name.text.toString().trim()
        val lok = edt_lokasi_kebun.text.toString().trim()

        if(kebun.isEmpty()||lok.isEmpty()){
            MDToast.makeText(this, "Tidak Boleh Kosong", Toast.LENGTH_LONG, MDToast.TYPE_INFO).show()
        }else{
            saveUserDB()
        }


    }

    private fun saveUserDB() {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/Komoditas/$uid/data_kebun")
        val kebun = edt_kebun_name.text.toString().trim()
        val lok = edt_lokasi_kebun.text.toString().trim()
        val com = Kebun(kebun, lok)
        ref.push().setValue(com)
                .addOnSuccessListener {
                    Log.d("reg", "Finally we saved the comodities to Firebase Database")
                    goToSplash()

                }
                .addOnFailureListener {
                    Log.d("reg", "Failed to set value to database: ${it.message}")
                }
    }

    private fun goToSplash() {
        val tanaman = Intent(this, TanamanActivity::class.java)
        startActivity(tanaman)
        finish()
    }

}