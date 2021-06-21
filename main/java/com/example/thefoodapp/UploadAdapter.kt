package com.example.thefoodapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UploadAdapter (var uploads: List<Upload>) : RecyclerView.Adapter<UploadAdapter.UploadViewHolder>(){


    inner class UploadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_upload, parent, false)
        return UploadViewHolder(view)
    }

    override fun onBindViewHolder(holder: UploadViewHolder, position: Int) {
        holder.itemView.apply {
            val tvtitle : TextView = findViewById(R.id.tvtitle)
            val tvquantity : TextView = findViewById(R.id.tvquantity)
            tvtitle.text = uploads[position].title
            tvquantity.text = uploads[position].quantity
        }
    }

    override fun getItemCount(): Int {
        return uploads.size
    }
}