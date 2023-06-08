package com.muthoknee.keytabuapp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap

class UploadbookActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    lateinit var imagePreview: ImageView
    lateinit var btn_choose_image: Button
    lateinit var btn_upload_image: Button
    lateinit var progress: ProgressDialog
    lateinit var edtBookName: EditText
    lateinit var edtBookAuthor: EditText
    lateinit var edtBookPublisher: EditText
    lateinit var spinnerBookCategory: Spinner
    lateinit var edtBookPrice: EditText
    lateinit var edtBookDeliveryPrice: EditText
    lateinit var mtvReal : TextView
    lateinit var mAuth : FirebaseAuth
    lateinit var bookCategory : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploadbook)
        btn_choose_image = findViewById(R.id.btn_choose_image)
        btn_upload_image = findViewById(R.id.btn_upload_image)
        imagePreview = findViewById(R.id.image_preview)
        edtBookName = findViewById(R.id.mEdtBookName)
        edtBookAuthor = findViewById(R.id.mEdtBookAuthor)
        edtBookPublisher = findViewById(R.id.mEdtBookPublisher)
        spinnerBookCategory = findViewById(R.id.mSpinnerBookCategory)
        edtBookPrice = findViewById(R.id.mEdtBookPrice)
        edtBookDeliveryPrice = findViewById(R.id.mEdtBookDeliveryPrice)
        mtvReal = findViewById(R.id.mtvtextReal)

        mAuth = FirebaseAuth.getInstance()
        spinnerBookCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                bookCategory = p0!!.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")

        btn_choose_image.setOnClickListener { launchGallery() }
        btn_upload_image.setOnClickListener { uploadImage() }
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imagePreview.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage(){
        var name = edtBookName.text.toString().trim()
        var author = edtBookAuthor.text.toString().trim()
        var publisher = edtBookPublisher.text.toString().trim()
        var category = bookCategory
        var price = edtBookPrice.text.toString().trim()
        var deliveryPrice = edtBookDeliveryPrice.text.toString().trim()
        var userId = mAuth.currentUser!!.uid
        var bookId = System.currentTimeMillis().toString()
        if (name.isEmpty()){
            edtBookName.setError("Please fill this input")
            edtBookName.requestFocus()
        }else if (author.isEmpty()){
            edtBookAuthor.setError("Please fill this input")
            edtBookAuthor.requestFocus()
        }else if (publisher.isEmpty()){
            edtBookPublisher.setError("Please fill this input")
            edtBookPublisher.requestFocus()
        }else if (category.equals("Select category")){
            Toast.makeText(applicationContext, "Please select a category", Toast.LENGTH_SHORT).show()
        }else if (price.isEmpty()){
            edtBookPrice.setError("Please fill this input")
            edtBookPrice.requestFocus()
        }else if (deliveryPrice.isEmpty()){
            edtBookDeliveryPrice.setError("Please fill this input")
            edtBookDeliveryPrice.requestFocus()
        }else{
            if(filePath != null){

                val ref = storageReference?.child("Books/" + UUID.randomUUID().toString())
                progress.show()
                val uploadTask = ref?.putFile(filePath!!)!!.addOnCompleteListener{
                    progress.dismiss()
                    if (it.isSuccessful){
                        ref.downloadUrl.addOnSuccessListener {
                            var bookData = Book(name,author,publisher,category,price,deliveryPrice,it.toString(),userId,bookId)
                            var ref = FirebaseDatabase.getInstance().getReference().child("Books/$bookId")
                            ref.setValue(bookData)
                            Toast.makeText(this, "Book submitted successfully", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this, "Book submission failed", Toast.LENGTH_SHORT).show()
                    }
                }


            }else{
                Toast.makeText(this, "Please Select an Image", Toast.LENGTH_SHORT).show()
            }
        }

    }

}