package com.muthoknee.keytabuapp

class User {
    var name:String = ""
    var email:String = ""
    var phoneNumber:String = ""
    var profilePicture:String = ""
    var userId:String = ""

    constructor(
        name: String,
        email: String,
        phoneNumber: String,
        profilePicture: String,
        userId: String
    ) {
        this.name = name
        this.email = email
        this.phoneNumber = phoneNumber
        this.profilePicture = profilePicture
        this.userId = userId
    }

    constructor()
}