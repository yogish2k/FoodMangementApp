package com.example.thefoodapp

import java.io.Serializable

data class foods (var uploaderName: String, var foodName: String, var contact: String, val additionalcontact: String, val quantity: String, var logo: Int, var uploadId: Int) : Serializable {

}