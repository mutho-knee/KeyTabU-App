package com.muthoknee.keytabuapp

class Book {
    var name:String = ""
    var author:String = ""
    var publisher:String = ""
    var category:String = ""
    var price:String = ""
    var deliveryPrice:String = ""
    var imageUrl:String = ""
    var userId:String = ""
    var bookId:String = ""

    constructor(
        name: String,
        author: String,
        publisher: String,
        category: String,
        price: String,
        deliveryPrice: String,
        imageUrl: String,
        userId: String,
        bookId: String
    ) {
        this.name = name
        this.author = author
        this.publisher = publisher
        this.category = category
        this.price = price
        this.deliveryPrice = deliveryPrice
        this.imageUrl = imageUrl
        this.userId = userId
        this.bookId = bookId
    }
    constructor()
}