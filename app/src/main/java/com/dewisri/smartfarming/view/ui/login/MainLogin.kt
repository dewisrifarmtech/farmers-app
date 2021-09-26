package com.dewisri.smartfarming.view.ui.login

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.dewisri.smartfarming.databinding.ActivityMainLoginBinding
import com.dewisri.smartfarming.view.ui.main.MainActivity
import com.dewisri.smartfarming.utils.CheckInternetConnection
import com.google.firebase.auth.FirebaseAuth
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainLogin : AppCompatActivity() {
    private lateinit var mainLoginBinding: ActivityMainLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        mainLoginBinding= ActivityMainLoginBinding.inflate(layoutInflater)
        setContentView(mainLoginBinding.root)
        supportActionBar?.hide()

        CheckInternetConnection(this).checkConnection()
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null){
            updateUI()
            return
        }

        validatePermissions()
        initButton()
    }

    private fun initButton() {
        mainLoginBinding.buttonMasuk.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        mainLoginBinding.tvDaftarDisini.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }
    }

    private fun validatePermissions() {
        Dexter
            .withActivity(this)
            .withPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                   // MDToast.makeText(baseContext, "Success di izinkan!", Toast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show()
                }

                override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                    Log.e("Permission", "Skipping rationale request in validatePermissions()!")
                }
            })
            .check()
    }



    private fun updateUI() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


}