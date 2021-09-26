package com.dewisri.smartfarming.view.ui.home.content.commodities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dewisri.smartfarming.R
import kotlinx.android.synthetic.main.activity_splash_komoditi.*

class SplashKomoditiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_komoditi)

        btnStartNow.setOnClickListener{
            val inputKebunActivity = Intent(this, InputKebunActivity::class.java)
            startActivity(inputKebunActivity)
        }

        tvSkip.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}