package com.example.submissionmakanan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListDetailFoodAdapter(private val listFood: ArrayList<Food>) : RecyclerView.Adapter<ListDetailFoodAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_column_food, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, _, photo) = listFood[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .into(holder.imgPhoto)
        holder.tvName.text = name

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listFood[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listFood.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.iv_foto_column)
        val tvName: TextView = itemView.findViewById(R.id.tv_name_column)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Food)
    }
}