package com.example.thefoodapp

import FoodAdapter
import OnFoodItemClickListner
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import org.json.JSONArray
import org.json.JSONObject


class MainPage : AppCompatActivity(), OnFoodItemClickListner {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var foodList: ArrayList<foods>
    lateinit var session: SessionManager
    lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainpage)

        session = SessionManager(applicationContext)

        foodList = ArrayList()
        foodDisplay()

        /*var foodRecycler = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.foodRecycler)
        foodRecycler.layoutManager = LinearLayoutManager(this)
        foodRecycler.addItemDecoration(DividerItemDecoration(this, 1))
        foodRecycler.adapter = FoodAdapter(foodList)*/

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.navView)
        swipeRefresh = findViewById(R.id.swipeRefresh)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            when(it.itemId) {
                R.id.miItem1 -> openUploads()

                R.id.miItem2 -> Toast.makeText(
                    applicationContext,
                    "Item2 was clicked",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.miItem3 -> logout()
            }
            true
        }

        swipeRefresh.setOnRefreshListener {
            foodList.clear()
            foodDisplay()
            swipeRefresh.isRefreshing = false
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openUploads() {
        val intent = Intent(this, UploadActivity::class.java)
        startActivity(intent)
    }

    private fun logout() {

        session.checkLogin()
        var userid: String? = session.getUserDetails()
        Toast.makeText(
                applicationContext,
                userid,
                Toast.LENGTH_SHORT
        ).show()
        session.logoutUser()
    }

    private fun foodDisplay(){
        val url = "http://103.154.233.231:8080/foodDisplay"
        val requestQueue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val jsonArray : JSONArray = response
                for (i in 0 until response.length()) {
                    val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                    val foodname:String = jsonObject.getString("foods")
                    val fname:String = jsonObject.getString("nameFirst")
                    val lname:String = jsonObject.getString("nameLast")
                    val name:String = "$fname $lname"
                    val contact:String = jsonObject.getString("contact")
                    val contactAdditional:String = jsonObject.getString("contactAdditional")
                    val quantity: String = jsonObject.getString("quantity")
                    val uploadId = jsonObject.getString("uploadID").toInt()
                    foodList.add(foods(name, foodname, contact, contactAdditional, quantity, R.drawable.logo, uploadId))
                }
                update()
            },
            {
                Toast.makeText(applicationContext, "Error Occurred!", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonArrayRequest)
    }

    private fun update(){
        var foodRecycler = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.foodRecycler)
        foodRecycler.layoutManager = LinearLayoutManager(this)
        foodRecycler.addItemDecoration(DividerItemDecoration(this, 1))
        foodRecycler.adapter = FoodAdapter(foodList, this)
    }

    override fun onItemClick(item: foods, position:Int) {
        Toast.makeText(this, item.uploaderName, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, FoodDetailsActivity::class.java)
//        intent.putExtra("FIRSTNAME", item.uploaderName)
//        intent.putExtra("LASTNAME", item.foodName)
//        intent.putExtra("UPLOADID", item.uploadId)
//        intent.putExtra("FOODLOGO", item.logo.toString())
        intent.putExtra("DETAILS", item)
        startActivity(intent)

    }
}

