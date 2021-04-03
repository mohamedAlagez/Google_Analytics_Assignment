package com.example.google_analytics_assignment

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.categoryitems.view.*


class CategoriesAdapter(var activity: Activity, var data: MutableList<String>) :
    RecyclerView.Adapter<CategoriesAdapter.myViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        var root =
            LayoutInflater.from(parent.context).inflate(R.layout.categoryitems, parent, false)
        return myViewHolder(root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.CategoryName.text = data[position]
        holder.itemView.setOnClickListener { itemClickListener?.onClick(data.get(position)) }
    }

    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val CategoryName = itemView.tvCategoryName
    }

    companion object {
        var itemClickListener: OnCategoryItemClickListener? = null
    }

    fun setOnItemClickListener(onCategoryItemClickListener: CategoriesAdapter.OnCategoryItemClickListener) {
        itemClickListener = onCategoryItemClickListener
    }

    interface OnCategoryItemClickListener {
        fun onClick(s: String)
    }

}