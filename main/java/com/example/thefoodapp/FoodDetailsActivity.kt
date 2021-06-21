package com.example.thefoodapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FoodDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_details)

        val order: Button = findViewById(R.id.button6)
        val firstname: TextView = findViewById(R.id.first_name)
        val lastname: TextView = findViewById(R.id.last_name)
        val contactdisp: TextView = findViewById(R.id.contactdisplay)
        val addtcontact: TextView = findViewById(R.id.addtcontactdisplay)
        val quantitydisp: TextView = findViewById(R.id.quantitydisplay)
        val image: ImageView = findViewById(R.id.image_food)
        val details = intent.getSerializableExtra("DETAILS") as foods
        firstname.text = details.uploaderName
        lastname.text = details.foodName
        image.setImageResource(details.logo)
        contactdisp.text = details.contact
        addtcontact.text = details.additionalcontact
        quantitydisp.text = details.quantity

        order.setOnClickListener {
            Toast.makeText(applicationContext,"Food Ordered", Toast.LENGTH_SHORT).show()
        }
    }
}