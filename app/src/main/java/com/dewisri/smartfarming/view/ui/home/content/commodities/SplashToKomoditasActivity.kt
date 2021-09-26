package com.dewisri.smartfarming.view.ui.home.content.commodities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dewisri.smartfarming.R
import kotlinx.android.synthetic.main.activity_splash_to_komoditi.*

class SplashToKomoditasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_to_komoditi)

        btnGoKomoditi.setOnClickListener {
            val komoditi = Intent(this, KomoditasActivity::class.java)
            startActivity(komoditi)
            finish()
        }
    }
}