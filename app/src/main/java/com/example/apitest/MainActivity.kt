/*WORKING ON
* - Go back with backstack
* - bring in API images, name and description to each fragment
* - bring in API for game in the main acitvity
* - add layout*/


package com.example.apitest

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    companion object {

        lateinit var navController: NavController
        lateinit var tv: TextView
        lateinit var btn: Button
        lateinit var backbtn: Button

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        backbtn = findViewById<Button?>(R.id.button3)
        backbtn.setBackgroundColor(Color.BLACK)
        backbtn.setOnClickListener {
            navController.navigate(R.id.homeFragment)
        }

        tv = findViewById(R.id.textView)
        btn = findViewById(R.id.button)
        btn.setBackgroundColor(Color.BLACK)

       btn.setOnClickListener {
            Log.i("niko", "Sanskrit etc: ")
            val requestQueue: RequestQueue = Volley.newRequestQueue(this)
            Log.i("niko", requestQueue.toString())

            var url = "https://priyangsubanerjee.github.io/yogism/yogism-api.json"

            var request = StringRequest(Request.Method.GET, url, { res ->

                var dataArray = JSONObject(res).getJSONArray("featured")
                var yogaSessions = dataArray.getJSONObject(0)

                var name = yogaSessions.getString("name")
                Log.i("niko",  name)

                val scheduledArray = yogaSessions.getJSONArray("scheduled")
                val firstScheduled = scheduledArray.getJSONObject(0)

                val englishName = firstScheduled.getString("english_name")
                val sanskrit = firstScheduled.getString("sanskrit_name")
                tv.text = (sanskrit + " = " + englishName)

                Log.i("niko", "success! " + name + englishName + sanskrit)

            },
                { error ->
                    Log.e("niko", "failed:" + error.message)
                Log.i("niko", "failed" )
            })
            requestQueue.cache.clear()
            requestQueue.add(request)
           requestQueue.start()
           }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}

/*
       override fun onBackPressed() {
           super.onBackPressed()
           Log.i("niko", "onBackPressed: back")
           // return
           if (supportFragmentManager.backStackEntryCount == 0) {
               Toast.makeText(
                   baseContext,
                   "can't go back anymore ",
                   Toast.LENGTH_SHORT
               ).show()
               return
           }
       }*/

/*
                fc = findViewById(R.id.fragmentContainerView)
                // val fm: FragmentManager = supportFragmentManager
                // var bf2:BlankFragment2 = BlankFragment2()
                //val fragmentManager = supportFragmentManager
                Toast.makeText(
                    baseContext,
                    "amount of fragments now: " + supportFragmentManager.backStackEntryCount,
                    Toast.LENGTH_SHORT
                ).show()*/