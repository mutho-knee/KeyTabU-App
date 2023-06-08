package com.muthoknee.keytabuapp


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class CarsAdapter(var context: Context, var data:ArrayList<Book>):BaseAdapter() {
    private class ViewHolder(row:View?){
        var mTxtName:TextView
        var mTxtAuthor:TextView
        var mTxtPublisher:TextView
        var mTxtCategory:TextView
        var mTxtPrice:TextView
        var mTxtDeliveryPrice:TextView
        var mImage:ImageView
        init {
            this.mTxtName = row?.findViewById(R.id.mTvName) as TextView
            this.mTxtAuthor = row?.findViewById(R.id.mTvAuthor) as TextView
            this.mTxtPublisher = row?.findViewById(R.id.mTvPublisher) as TextView
            this.mTxtCategory = row?.findViewById(R.id.mTvCategory) as TextView
            this.mTxtPrice = row?.findViewById(R.id.mTvPrice) as TextView
            this.mTxtDeliveryPrice = row?.findViewById(R.id.mTvDeliveryPrice) as TextView
            this.mImage = row?.findViewById(R.id.mImgPic) as ImageView
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        var viewHolder:ViewHolder
        if (convertView == null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.book_layout,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var item:Book = getItem(position) as Book
        viewHolder.mTxtName.text = item.name
        viewHolder.mTxtAuthor.text = item.author
        viewHolder.mTxtPublisher.text = item.publisher
        viewHolder.mTxtCategory.text = item.category
        viewHolder.mTxtPrice.text = item.price
        viewHolder.mTxtDeliveryPrice.text = item.deliveryPrice
        Glide.with(context).load(item.imageUrl).into(viewHolder.mImage)
        return view as View
    }

    override fun getItem(position: Int): Any {
        return  data.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.count()
    }
}