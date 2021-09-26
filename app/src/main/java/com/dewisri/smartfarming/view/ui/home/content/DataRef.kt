package com.dewisri.smartfarming.view.ui.home.content.commodities.fragments

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DataRef {
    var komoditas:String = "Komoditas"
    var  auth: FirebaseAuth? = null
    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference
    lateinit var rootNode: FirebaseDatabase
    lateinit var reference: DatabaseReference


}