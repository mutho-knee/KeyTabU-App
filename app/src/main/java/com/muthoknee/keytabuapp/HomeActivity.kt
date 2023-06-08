package com.muthoknee.keytabuapp

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity() {
    lateinit var mListBooks:ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mListBooks = findViewById(R.id.mListBooks)
        var books:ArrayList<Book> = ArrayList()
        var myAdapter = CarsAdapter(applicationContext,books)
        var progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")

        //Access the table in the database

        var my_db = FirebaseDatabase.getInstance().reference.child("Books")
        //Start retrieving data
        progress.show()
        my_db.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                //Get the data and put it on the arraylist users
                books.clear()
                for (snap in p0.children){
                    var book = snap.getValue(Book::class.java)
                    books.add(book!!)
                }
                //Notify the adapter that data has changed
                myAdapter.notifyDataSetChanged()
                progress.dismiss()
            }

            override fun onCancelled(p0: DatabaseError) {
                progress.dismiss()
                Toast.makeText(applicationContext,"DB Locked",Toast.LENGTH_LONG).show()
            }
        })

        mListBooks.adapter = myAdapter
        mListBooks.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this,AddtocartActivity::class.java)
            var name: String = books.get(position).name
            var author: String = books.get(position).author
            var publisher: String = books.get(position).publisher
            var category: String = books.get(position).category
            var price: String = books.get(position).price
            var deliveryPrice: String = books.get(position).deliveryPrice
            var image: String = books.get(position).imageUrl

            intent.putExtra("name",name)
            intent.putExtra("author",author)
            intent.putExtra("publisher",publisher)
            intent.putExtra("category",category)
            intent.putExtra("price",price)
            intent.putExtra("deliveryPrice",deliveryPrice)
            intent.putExtra("image",image)
            startActivity(intent)
        }

        mListBooks.setOnItemLongClickListener(object : AdapterView.OnItemLongClickListener{
            override fun onItemLongClick(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ): Boolean {
                var alert = AlertDialog.Builder(this@HomeActivity)
                alert.setTitle("Choose option")
                alert.setMessage("Delete or update app")
                alert.setNegativeButton("Delete", DialogInterface.OnClickListener { dialogInterface, i ->
                    var bookId = books.get(p2).bookId
                    var delRef = FirebaseDatabase.getInstance().getReference().child("Books/$bookId")
                    delRef.removeValue()

                })
                alert.setPositiveButton("Update", DialogInterface.OnClickListener { dialogInterface, i ->

                    Toast.makeText(applicationContext, "Update clicked", Toast.LENGTH_SHORT).show()
                })
                alert.create().show()
                return true
            }
        })


    }
}