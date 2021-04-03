package com.example.google_analytics_assignment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var categorieslist = ArrayList<String>()
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    var begin: Long = 0
    var finish: Long = 0
    var sharedPreferences: SharedPreferences? = null
    var db: FirebaseFirestore? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(applicationContext)
        trackScreen("categories screen")

        begin = System.currentTimeMillis()
        db = FirebaseFirestore.getInstance()
        sharedPreferences = getSharedPreferences("id", Context.MODE_PRIVATE)
        val id = sharedPreferences?.getString("id", "")
        if (id == "") {
            val sharedPreferences2 =
                getSharedPreferences("id", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.putString("id", System.currentTimeMillis().toString() + "")
            editor?.apply()
        }


        categorieslist.add("Computers")
        categorieslist.add("Mobiles")
        categorieslist.add("food")
        categorieslist.add("Clothes")
        categorieslist.add("Electrical devices")

        rvCategories.layoutManager = LinearLayoutManager(applicationContext)
        var adapter = CategoriesAdapter(this, categorieslist)
        rvCategories.adapter = adapter
        adapter.setOnItemClickListener(object : CategoriesAdapter.OnCategoryItemClickListener {
            override fun onClick(s: String) {
                setEvent(System.currentTimeMillis().toString() + "", "$s was clicked")
                val i = Intent(applicationContext, ProductsActivity::class.java)
                i.putExtra("category", s)
                startActivity(i)
            }
        })
    }

    override fun onDestroy() {
        Thread(Runnable {
            finish = System.currentTimeMillis()
            val timeSpend: Long = finish - begin
            val seconds = (timeSpend / 1000).toInt() % 60
            val minutes = (timeSpend / (1000 * 60) % 60).toInt()
            val hours = (timeSpend / (1000 * 60 * 60) % 24).toInt()
            db!!.collection("times").add(
                Model(
                    "id : " + sharedPreferences!!.getString("id", ""),
                    "Time  : $hours:$minutes:$seconds"
                )
            )
        })
        super.onDestroy()
    }

    fun trackScreen(screenName: String?) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
        mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    fun setEvent(id: String, name: String) {
        val bundle = Bundle()
        bundle.putString("id", "id : $id")
        bundle.putString("name", "name : $name")
        mFirebaseAnalytics!!.logEvent(id, bundle)
    }

}