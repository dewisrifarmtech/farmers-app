package com.dewisri.smartfarming.view.ui.login

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dewisri.smartfarming.databinding.ActivityLoginBinding
import com.dewisri.smartfarming.view.ui.main.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.valdesekamdem.library.mdtoast.MDToast


class LoginActivity : AppCompatActivity(){

    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        validatePermissions()
        initFirst()
    }

    private fun initFirst() {
       loginBinding.btnLogin.setOnClickListener OnClickListener@{
           val email = loginBinding.edtLoginEmail.text.toString().trim()
           val password = loginBinding.edtLoginPassword.text.toString().trim()

           if (TextUtils.isEmpty(email)){
               Toast.makeText(applicationContext,"Please Entre your email.",Toast.LENGTH_SHORT).show()
               return@OnClickListener
           }
           if (TextUtils.isEmpty(password)) {
               Toast.makeText(applicationContext, "Please Enter your Password", Toast.LENGTH_SHORT).show()
               return@OnClickListener
           }

           loginBinding.progressBar.visibility = View.VISIBLE
           mAuth.signInWithEmailAndPassword(email, password)
                   .addOnCompleteListener(this, OnCompleteListener {
                       task ->
                       loginBinding.progressBar.visibility = View.VISIBLE

                       if (!task.isSuccessful){
                           loginBinding.progressBar.visibility = View.GONE
                           MDToast.makeText(baseContext, "Login Gagal!!!", Toast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show()
                       }else{
                           startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                           finish()
                       }
                   })

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

                }

                override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                    Log.e("Permission", "Skipping rationale request in validatePermissions()!")
                }
            })
            .check()
    }
}