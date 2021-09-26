package com.dewisri.smartfarming.view.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dewisri.smartfarming.databinding.ActivityRegisterBinding
import com.dewisri.smartfarming.data.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.valdesekamdem.library.mdtoast.MDToast
import java.util.*


class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var db: FirebaseDatabase
    private lateinit var user: User
    var PreReqCode = 1
    var REQUESCODE = 1
    private lateinit var prefs: SharedPreferences
    lateinit var pickedImgUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)
        supportActionBar?.hide()
        prefs = getSharedPreferences("dewisri", Context.MODE_PRIVATE)
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
        databaseReference = db.getReference("users")
        initFirst()
        initBtn()

    }

    private fun initBtn() {
        registerBinding.backBtn.setOnClickListener {
            onBackPressed()
        }

        registerBinding.signInText.setOnClickListener {
            val login = Intent(this, LoginActivity::class.java)
            startActivity(login)
        }
    }

    private fun initFirst() {
        registerBinding.regProgressBar.visibility = View.INVISIBLE

        registerBinding.regBtn.setOnClickListener OnClickListener@{
            registerBinding.regBtn.visibility = View.INVISIBLE;
            registerBinding.regProgressBar.visibility = View.VISIBLE;
            val name = registerBinding.regName.text.toString().trim()
            val email = registerBinding.regMail.text.toString().trim()
            val password = registerBinding.regPassword.text.toString().trim()
            val repeatPassword = registerBinding.regPassword2.text.toString().trim()
            val avatar = registerBinding.regUserPhoto.drawable.toString()


            if (TextUtils.isEmpty(email)){
                MDToast.makeText(applicationContext, "Enter your email Address!!", Toast.LENGTH_LONG, MDToast.TYPE_INFO).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)){
                MDToast.makeText(applicationContext, "Enter your Password", Toast.LENGTH_LONG, MDToast.TYPE_INFO).show()
                return@OnClickListener
            }else if (password.length < 6) {
                MDToast.makeText(applicationContext, "Enter your Password", Toast.LENGTH_LONG, MDToast.TYPE_INFO).show()
                return@OnClickListener
            }
            if (password.length < 6){
                MDToast.makeText(applicationContext, "Password too short, enter mimimum 6 charcters", Toast.LENGTH_LONG, MDToast.TYPE_INFO).show()
                return@OnClickListener
            }else if(repeatPassword.length < 6)
            {
                MDToast.makeText(applicationContext, "Password too short, enter mimimum 6 charcters", Toast.LENGTH_LONG, MDToast.TYPE_INFO).show()
                return@OnClickListener
            }


            if (name.isEmpty() || email.isEmpty() || password.isEmpty()
                    || repeatPassword.isEmpty() || avatar.isEmpty())
            {
                showMessage("Please Verify all fields");
                registerBinding.regBtn.visibility = View.VISIBLE;
                registerBinding.regProgressBar.visibility = View.INVISIBLE;
            }
            else {

                CreateUserAccount(email, name, password)
            }

        }


        registerBinding.regUserPhoto.setOnClickListener {
                openGalery()
        }
    }

    private fun CreateUserAccount(email: String, name: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, OnCompleteListener { task ->
                    Log.e("register", "createUserWithEmail:onComplete" + task.isSuccessful)
                    if (!task.isSuccessful) {
                        showMessage("account creation failed" + task.exception?.message)
                        registerBinding.regBtn.visibility = View.VISIBLE
                        registerBinding.regProgressBar.visibility = View.INVISIBLE;
                        return@OnCompleteListener
                    } else {
                        showMessage("Account Berhasil dibuat")

                        registerBinding.regBtn.visibility = View.VISIBLE
                        registerBinding.regProgressBar.visibility = View.GONE
                        uploadToStorage()
                    }
                })
    }

    private fun uploadToStorage() {
        if (pickedImgUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(pickedImgUri)
            .addOnSuccessListener { it ->
                Log.d("reg", "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("reg", "File Location: $it")

                    saveUserToDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d("reg", "Failed to upload image to storage: ${it.message}")
            }
    }

    private fun saveUserToDatabase(avatar: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val name = registerBinding.regName.text.toString().trim()
        val email = registerBinding.regMail.text.toString().trim()
        val user = User(name, email, avatar)

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("reg", "Finally we saved the user to Firebase Database")
            }
            .addOnFailureListener {
                Log.d("reg", "Failed to set value to database: ${it.message}")
            }
    }

    fun initEdt(){
        registerBinding.regName.setText("")
        registerBinding.regMail.setText("")
        registerBinding.regPassword.setText("")
        registerBinding.regPassword2.setText("")

    }






    private fun showMessage(message: String) {
        MDToast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    private fun openGalery() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, REQUESCODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null) {

            pickedImgUri = data.data!!
            registerBinding.regUserPhoto.setImageURI(pickedImgUri)

        }
    }

}