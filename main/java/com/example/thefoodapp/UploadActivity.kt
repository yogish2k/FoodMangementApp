package com.example.thefoodapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class UploadActivity : AppCompatActivity() {
    lateinit var session: SessionManager
    lateinit var adapter: UploadAdapter
    var uploadList = mutableListOf(
        Upload("Item", "Quantity")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        session = SessionManager(applicationContext)

        val etupload: TextView = findViewById(R.id.etupload)
        val etquantity: TextView = findViewById(R.id.etquantity)
        val btnupload = findViewById<Button>(R.id.btnaddupload)

        val rvuploads: androidx.recyclerview.widget.RecyclerView = findViewById(R.id.rvuploads)
        rvuploads.addItemDecoration(DividerItemDecoration(this, 1))

        adapter = UploadAdapter(uploadList)
        rvuploads.adapter = adapter
        rvuploads.layoutManager = LinearLayoutManager(this)
        myUploads()

        /*val adapter = UploadAdapter(uploadList)
        rvuploads.adapter = adapter
        rvuploads.layoutManager = LinearLayoutManager(this)*/

        btnupload.setOnClickListener {
            val title: String = etupload.text.toString()
            val quantity: String = etquantity.text.toString()
            val contact: String = "1111111111"
//            val upload = Upload(title, quantity)
//            uploadList.add(upload)
//            adapter.notifyItemInserted(uploadList.size - 1)
            upload(title, quantity, contact)
        }
    }

    private fun upload(title: String, quantity: String, contact: String) {

        val url = "http://103.154.233.231:8080/foodUpload"
        val requestQueue = Volley.newRequestQueue(this)
        val userid: String = session.getUserDetails().toString()
        val params = JSONObject()
        val array = JSONArray()
        val categ = JSONObject()

        params.put("userid", userid)
        params.put("contact", contact)

        categ.put("foodItem", title)
        categ.put("quantity", quantity)
        array.put(categ)
        params.put("category", array)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, params,
            { response ->
                val message: String = response.getString("message")
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            },
            {
                Toast.makeText(applicationContext, "Error Occurred!", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }

    private fun myUploads(){
        val url = "http://103.154.233.231:8080/myUploads"
        val requestQueue = Volley.newRequestQueue(this)

        val userid: String = session.getUserDetails().toString()
        val params = JSONArray()
        var obj = JSONObject()
        obj.put("userid", userid)
        params.put(obj)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.POST, url, params,
            { response ->
                val jsonArray: JSONArray = response
                for (i in 0 until response.length()) {
                    val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                    val title: String = jsonObject.getString("foods")
                    val quantity: String = jsonObject.getString("quantity")
                    uploadList.add(Upload(title, quantity))
                    adapter.notifyItemInserted(uploadList.size - 1)
                }
                update()
            },
            {
                Toast.makeText(applicationContext, "No Uploads!", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonArrayRequest)
    }

    private fun update() {

    }


}