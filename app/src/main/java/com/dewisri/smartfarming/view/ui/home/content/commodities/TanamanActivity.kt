package com.dewisri.smartfarming.view.ui.home.content.commodities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dewisri.smartfarming.R
import com.dewisri.smartfarming.data.model.Tanaman
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.valdesekamdem.library.mdtoast.MDToast
import kotlinx.android.synthetic.main.fragment_add_tanaman.*

class TanamanActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add_tanaman)
        initTanaman()

    }

    private fun initTanaman() {
      btn_add_tanaman.setOnClickListener {
          saveUserDB()
      }
    }



    private fun saveUserDB() {

        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/Komoditas/$uid/data_tanaman")
        val tanam = edt_nama_tanaman.text.toString().trim()

        if(tanam.isEmpty()){
            MDToast.makeText(this, "Tidak Boleh Kosong", Toast.LENGTH_LONG, MDToast.TYPE_INFO).show()

        }else {

            val com = Tanaman(tanam)

            ref.push().setValue(com)
                    .addOnSuccessListener {
                        Log.d("reg", "Finally we saved the comodities to Firebase Database")
                        goToTanaman()

                    }
                    .addOnFailureListener {
                        Log.d("reg", "Failed to set value to database: ${it.message}")
                    }

        }
    }

    private fun goToTanaman() {
        val tanaman = Intent(this, KomoditasActivity::class.java)
        startActivity(tanaman)
        finish()
    }

}