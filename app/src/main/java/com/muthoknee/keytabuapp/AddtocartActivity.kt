package com.muthoknee.keytabuapp
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class AddtocartActivity : AppCompatActivity() {
    lateinit var mTxtName: TextView
    lateinit var mTxtAuthor: TextView
    lateinit var mTxtPublisher: TextView
    lateinit var mTxtCategory: TextView
    lateinit var mTxtPrice: TextView
    lateinit var mTxtDeliveryPrice: TextView
    lateinit var mImage:ImageView
    lateinit var btnpurchase:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addtocart)
        mTxtName = findViewById(R.id.mTvName)
        mTxtAuthor = findViewById(R.id.mTvAuthor)
        mTxtPublisher = findViewById(R.id.mTvPublisher)
        mTxtCategory = findViewById(R.id.mTvCategory)
        mTxtPrice = findViewById(R.id.mTvPrice)
        mTxtDeliveryPrice = findViewById(R.id.mTvDeliveryPrice)
        mImage = findViewById(R.id.imageView)
        btnpurchase = findViewById(R.id.btnpurchase)





        btnpurchase.setOnClickListener {
            val simToolKitLaunchIntent =
                applicationContext.packageManager.getLaunchIntentForPackage("com.android.stk")
            simToolKitLaunchIntent?.let { startActivity(it) }
        }


        var name = intent.getStringExtra("name")
        var author = intent.getStringExtra("author")
        var publisher = intent.getStringExtra("publisher")
        var category = intent.getStringExtra("category")
        var price = intent.getStringExtra("price")
        var deliveryPrice = intent.getStringExtra("deliveryPrice")
        var image = intent.getStringExtra("image")
        Glide.with(this).load(image).into(mImage)
        mTxtName.setText(name)
        mTxtAuthor.setText(author)
        mTxtPublisher.setText(publisher)
        mTxtCategory.setText(category)
        mTxtPrice.setText(price)
        mTxtDeliveryPrice.setText(deliveryPrice)



    }
}