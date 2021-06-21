package com.example.thefoodapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val regBtn = findViewById<Button>(R.id.register)
        val nameFirst = findViewById<TextView>(R.id.firstName)
        val nameLast = findViewById<TextView>(R.id.lastName)
        val emailTxt = findViewById<TextView>(R.id.email)
        val contactNum = findViewById<TextView>(R.id.mobile)
        val addContact = findViewById<TextView>(R.id.addMobile)
        val passwordTxt = findViewById<TextView>(R.id.password)
        val confirmPass = findViewById<TextView>(R.id.confirmPassword)


        regBtn.setOnClickListener{

            val nameF: String = nameFirst.text.toString().trim()
            val nameL: String = nameLast.text.toString().trim()
            val email: String = emailTxt.text.toString().trim()
            val contact: String = contactNum.text.toString().trim()
            val adContact: String = addContact.text.toString().trim()
            val password: String = passwordTxt.text.toString().trim()
            val confirmPas: String = confirmPass.text.toString().trim()
            val params = HashMap<String, String>()
            params["firstname"] = nameF
            params["lastname"] = nameL
            params["email"] = email
            params["contact"] = contact
            params["additionalcontact"] = adContact
            params["password"] = password
            if(validate(nameF, nameL, email, contact, adContact, password)){
                if(password == confirmPas) {
                    userRegister(params)
                } else {
                    Toast.makeText(applicationContext, "Password Mismatch", Toast.LENGTH_SHORT).show()
                }
            }
            else
                Toast.makeText(applicationContext, "Fields cant be empty", Toast.LENGTH_SHORT).show()

        }

    }

    private fun validate(nameF: String, nameL: String, email: String, contact: String, adContact: String, password: String): Boolean {

        var check = true
        if (nameF.isEmpty()) check = false
        if (nameL.isEmpty()) check = false
        if (email.isEmpty()) check = false
        if (contact.isEmpty()) check = false
        if (adContact.isEmpty()) check = false
        if (password.isEmpty()) check = false

        return check
    }

    private fun userRegister(params: HashMap<String, String>){
        val url = "http://103.154.233.231:8080/register"
        val requestQueue = Volley.newRequestQueue(this)

        val jsonObject = JSONObject(params as Map<*, *>)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, jsonObject,
                { response ->
                    val message: String = response.getString("message")
                    val status: Int = response.getString("status").toInt()
                    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                    if (status == 200) {
                        nextDisplay()
                    }
                },
                {
                    Toast.makeText(applicationContext, "Error Occurred!", Toast.LENGTH_SHORT).show()
                }
        )

        requestQueue.add(jsonObjectRequest)
    }

    private fun nextDisplay() {
        val intent = Intent(this, MainPage::class.java)
        startActivity(intent)
    }
}