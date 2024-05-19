package com.example.volleygithubuser

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import androidx.recyclerview.widget.LinearLayoutManager



class MainActivity : AppCompatActivity() {

    private val url = "https://api.github.com/users"
    private var userInfoItem = arrayListOf<UserInfoItem>()
    private var userInfo = UserInfo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val recyclerView = findViewById<RecyclerView>(R.id.recycleview)

// Create a LinearLayoutManager instance
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

// Basic Toast test
        Toast.makeText(this, "App Started", Toast.LENGTH_SHORT).show()

        // Basic Toast test
        Toast.makeText(this, "App Started", Toast.LENGTH_SHORT).show()

        val stringRequest = StringRequest(url, Response.Listener { response ->
            Log.d("MainActivity", "Response received: $response")

            val gsonBuilder = GsonBuilder()
            val gson = gsonBuilder.create()

            try {
                val userInfoArray = gson.fromJson(response, Array<UserInfoItem>::class.java)
                userInfoItem = ArrayList(userInfoArray.asList())

                userInfoItem.forEach {
                    userInfo.add(it)
                }


                val adapter = Adapter(this,userInfo)

                val layoutManager = LinearLayoutManager(this)
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = adapter

                Log.d("MainActivity", "Parsed user info: $userInfo")
                runOnUiThread {
                    Toast.makeText(this, userInfo.toString(), Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error parsing JSON", e)
                runOnUiThread {
                    Toast.makeText(this, "Error parsing data: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }, Response.ErrorListener {
            Log.e("MainActivity", "Volley error", it)
            runOnUiThread {
                Toast.makeText(this, "Something went wrong: ${it.toString()}", Toast.LENGTH_LONG).show()
            }
        })

        val volleyQueue = Volley.newRequestQueue(this)
        volleyQueue.add(stringRequest)
    }
}
