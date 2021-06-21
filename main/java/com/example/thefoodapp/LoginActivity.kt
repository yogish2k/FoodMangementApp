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

class LoginActivity : AppCompatActivity() {

    lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        session = SessionManager(applicationContext)
        if (session.isLoggedIn())
        {
            var i: Intent = Intent(applicationContext, MainPage::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
            finish()
        }

        val loginBtn = findViewById<Button>(R.id.btn_login)
        val registerBtn = findViewById<Button>(R.id.btn_register)
        val loginTxt = findViewById<TextView>(R.id.et_email)
        val passwordTxt = findViewById<TextView>(R.id.et_password)

        loginBtn.setOnClickListener{
            val email: String = loginTxt.text.toString().trim()
            val password: String = passwordTxt.text.toString().trim()
            passwordTxt.text = ""
            userLogin(email,password)
        }

        registerBtn.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }

    private fun userLogin(email: String, password: String){
        val url = "http://103.154.233.231:8080/login"
        val requestQueue = Volley.newRequestQueue(this)
        val params = HashMap<String,String>()
        params["email"] = email
        params["password"] = password
        val jsonObject = JSONObject(params as Map<*, *>)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, jsonObject,
                { response ->
                    val message: String = response.getString("message")
                    val status: Int = response.getString("status").toInt()
                    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                    if(status == 200)
                    {
                        val userId: String = response.getString("userID")
                        session.createLoginSession(userId)
                        Toast.makeText(applicationContext,userId,Toast.LENGTH_SHORT).show()
                        nextDisplay()
                    }
                },
                {
                    Toast.makeText(applicationContext,"Error Occurred!",Toast.LENGTH_SHORT).show()
                }
        )

        requestQueue.add(jsonObjectRequest)
    }

    private fun nextDisplay() {
        val intent = Intent(this, MainPage::class.java)
        startActivity(intent)
    }
}