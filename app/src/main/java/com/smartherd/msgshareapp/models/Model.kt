package com.smartherd.msgshareapp.models

data class Hobby(var title: String)

object Supplier {

    var hobbies = mutableListOf(
        Hobby("Swimming"),
        Hobby("Reading"),
        Hobby("Walking"),
        Hobby("Sleeping"),
        Hobby("Gaming"),
        Hobby("Programming"),
        Hobby("Talking")
    )

}