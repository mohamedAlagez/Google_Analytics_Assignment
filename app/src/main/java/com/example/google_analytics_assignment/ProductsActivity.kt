package com.example.google_analytics_assignment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_products.*

class ProductsActivity : AppCompatActivity() {

    var myCategory: String? = null
    var myList = ArrayList<String>()
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    var begin: Long = 0
    var finish: Long = 0
    var sharedPreferences: SharedPreferences? = null
    var db: FirebaseFirestore? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(applicationContext)
        trackScreen("products screen")

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
        myCategory = intent.getStringExtra("category")

        when (myCategory) {
            "Computers" -> {
                myList.add("Mi Notebook Pro 14")
                myList.add("Lenovo 500w Gen 3")
                myList.add("Apple MacBook Air MRE82HN/A")
                myList.add("HP ProBook")
                myList.add("HP NoteBook 445 G8")

                rvProducts.layoutManager = LinearLayoutManager(applicationContext)
                var adapter = CategoriesAdapter(this, myList)
                rvProducts.adapter= adapter
                adapter.setOnItemClickListener(object :
                    CategoriesAdapter.OnCategoryItemClickListener {
                    override fun onClick(s: String) {
                        setEvent(System.currentTimeMillis().toString() + "", "$s was clicked")
                        val i = Intent(applicationContext, Detailse::class.java)
                        i.putExtra("product", s)
                        startActivity(i)
                    }
                })
            }
            "Mobiles" -> {
                myList.add("Apple iPhone XS Max")
                myList.add("Apple iPhone 6 Plus")
                myList.add("Huawei Y7a")
                myList.add("Samsung Galaxy A52")
                myList.add("Samsung Galaxy S20")

                rvProducts.layoutManager = LinearLayoutManager(applicationContext)
                var adapter = CategoriesAdapter(this, myList)
                rvProducts.adapter= adapter
                adapter.setOnItemClickListener(object :
                    CategoriesAdapter.OnCategoryItemClickListener {
                    override fun onClick(s: String) {
                        setEvent(System.currentTimeMillis().toString() + "", "$s was clicked")
                        val i = Intent(applicationContext, Detailse::class.java)
                        i.putExtra("product", s)
                        startActivity(i)
                    }
                })
            }
            "food" -> {
                myList.add("Kabaab")
                myList.add("Pizza")
                myList.add("Shawarma")
                myList.add("Grilled chiken")
                myList.add("Drinks")

                rvProducts.layoutManager = LinearLayoutManager(applicationContext)
                var adapter = CategoriesAdapter(this, myList)
                rvProducts.adapter= adapter
                adapter.setOnItemClickListener(object :
                    CategoriesAdapter.OnCategoryItemClickListener {
                    override fun onClick(s: String) {
                        setEvent(System.currentTimeMillis().toString() + "", "$s was clicked")
                        val i = Intent(applicationContext, Detailse::class.java)
                        i.putExtra("product", s)
                        startActivity(i)
                    }
                })
            }
            "Clothes" -> {
                myList.add("Cap")
                myList.add("Shirt")
                myList.add("Jacket")
                myList.add("Jeans")
                myList.add("Shoes")

                rvProducts.layoutManager = LinearLayoutManager(applicationContext)
                var adapter = CategoriesAdapter(this, myList)
                rvProducts.adapter= adapter
                adapter.setOnItemClickListener(object :
                    CategoriesAdapter.OnCategoryItemClickListener {
                    override fun onClick(s: String) {
                        setEvent(System.currentTimeMillis().toString() + "", "$s was clicked")
                        val i = Intent(applicationContext, Detailse::class.java)
                        i.putExtra("product", s)
                        startActivity(i)
                    }
                })
            }
            "Electrical devices" -> {
                myList.add("Refrigerator")
                myList.add("Washer")
                myList.add("Conditioner")
                myList.add("Fan")
                myList.add("Microwave")

                rvProducts.layoutManager = LinearLayoutManager(applicationContext)
                var adapter = CategoriesAdapter(this, myList)
                rvProducts.adapter  = adapter
                adapter.setOnItemClickListener(object :
                    CategoriesAdapter.OnCategoryItemClickListener {
                    override fun onClick(s: String) {
                        setEvent(System.currentTimeMillis().toString() + "", "$s was clicked")
                        val i = Intent(applicationContext, Detailse::class.java)
                        i.putExtra("product", s)
                        startActivity(i)
                    }
                })
            }

        }
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