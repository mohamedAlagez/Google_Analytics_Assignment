package com.example.google_analytics_assignment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore

class Detailse : AppCompatActivity() {

    var imageView: ImageView? = null
    var detailsTV: TextView? = null
    var btnBuyNow: Button? = null
    var myProduct: String? = null
    var begin: Long = 0
    var finish: Long = 0
    var sharedPreferences: SharedPreferences? = null
    var db: FirebaseFirestore? = null
    private var mFirebaseAnalytics: FirebaseAnalytics? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailse)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(applicationContext)
        trackScreen(" product details screen")

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

        myProduct = intent.getStringExtra("product")
        btnBuyNow?.setOnClickListener {
            setEvent(System.currentTimeMillis().toString() + "", "Buy Now Button Was Clicked")
        }

        when (myProduct) {
            "Mi Notebook Pro 14" -> {
                imageView?.setImageResource(R.drawable.l1)
                detailsTV?.setText(
                    R.string.l1
                )
            }
            "Lenovo 500w Gen 3" -> {
                imageView?.setImageResource(R.drawable.l2)
                detailsTV?.setText(
                    R.string.l2
                )
            }
            "Apple MacBook Air MRE82HN/A" -> {
                imageView?.setImageResource(R.drawable.l3)
                detailsTV?.setText(
                    R.string.l3
                )
            }
            "HP ProBook" -> {
                imageView?.setImageResource(R.drawable.l4)
                detailsTV?.setText(
                    R.string.l4
                )
            }
            "HP NoteBook 445 G8" -> {
                imageView?.setImageResource(R.drawable.l5)
                detailsTV?.setText(
                    R.string.l5
                )
            }
            "Apple iPhone XS Max" -> {
                imageView?.setImageResource(R.drawable.m1)
                detailsTV?.setText(
                    R.string.m1
                )
            }
            "Apple iPhone 6 Plus" -> {
                imageView?.setImageResource(R.drawable.m2)
                detailsTV?.setText(
                    R.string.m2
                )
            }
            "Huawei Y7a" -> {
                imageView?.setImageResource(R.drawable.m3)
                detailsTV?.setText(
                    R.string.m3
                )
            }
            "Samsung Galaxy A52" -> {
                imageView?.setImageResource(R.drawable.m4)
                detailsTV?.setText(
                    R.string.m4
                )
            }
            "Samsung Galaxy S20" -> {
                imageView?.setImageResource(R.drawable.m5)
                detailsTV?.setText(
                    R.string.m5
                )
            }

            "Kabaab" -> {
                imageView?.setImageResource(R.drawable.f1)
                detailsTV?.setText(
                    R.string.f1
                )
            }
            "Pizza" -> {
                imageView?.setImageResource(R.drawable.f2)
                detailsTV?.setText(
                    R.string.f2
                )
            }
            "Shawarma" -> {
                imageView?.setImageResource(R.drawable.f3)
                detailsTV?.setText(
                    R.string.f3
                )
            }
            "Grilled chiken" -> {
                imageView?.setImageResource(R.drawable.f4)
                detailsTV?.setText(
                    R.string.f4
                )
            }
            "Drinks" -> {
                imageView?.setImageResource(R.drawable.f5)
                detailsTV?.setText(
                    R.string.f5
                )
            }

            "Cap" -> {
                imageView?.setImageResource(R.drawable.c1)
                detailsTV?.setText(
                    R.string.c1
                )
            }
            "Shirt" -> {
                imageView?.setImageResource(R.drawable.c2)
                detailsTV?.setText(
                    R.string.c2
                )
            }
            "Jacket" -> {
                imageView?.setImageResource(R.drawable.c3)
                detailsTV?.setText(
                    R.string.c3
                )
            }
            "Jeans" -> {
                imageView?.setImageResource(R.drawable.c4)
                detailsTV?.setText(
                    R.string.c4
                )
            }
            "Shoes" -> {
                imageView?.setImageResource(R.drawable.c5)
                detailsTV?.setText(
                    R.string.c5
                )
            }

            "Refrigerator" -> {
                imageView?.setImageResource(R.drawable.e1)
                detailsTV?.setText(
                    R.string.e1
                )
            }
            "Washer" -> {
                imageView?.setImageResource(R.drawable.e2)
                detailsTV?.setText(
                    R.string.e2
                )
            }
            "Conditioner" -> {
                imageView?.setImageResource(R.drawable.e3)
                detailsTV?.setText(
                    R.string.e3
                )
            }
            "Fan" -> {
                imageView?.setImageResource(R.drawable.e4)
                detailsTV?.setText(
                    R.string.e4
                )
            }
            "Microwave" -> {
                imageView?.setImageResource(R.drawable.e5)
                detailsTV?.setText(
                    R.string.e5
                )
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