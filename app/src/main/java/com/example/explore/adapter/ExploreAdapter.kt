package com.example.explore.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.explore.R
import com.example.explore.api.ExploreResponseItem

class ExploreAdapter:RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>() {

    inner class ExploreViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val countryImage : ImageView = itemView.findViewById(R.id.flag)
        val countryTitle: TextView = itemView.findViewById(R.id.countryName)
        val countryCap: TextView = itemView.findViewById(R.id.countryCap)
        //val alphabet: TextView = itemView.findViewById(R.id.alphabet)
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<ExploreResponseItem>(){
        override fun areItemsTheSame(oldItem: ExploreResponseItem, newItem: ExploreResponseItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ExploreResponseItem, newItem: ExploreResponseItem): Boolean {
          return oldItem == newItem
        }
    }
    val diff = AsyncListDiffer(this, diffCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        return ExploreViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.country,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        val explore = diff.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(explore.flag).into(holder.countryImage)
            holder.countryTitle.text = explore.name.toString()
            holder.countryCap.text = explore.capital.toString()
            setOnItemClickListener {
                onItemClickListener?.let { it(explore) }
            }
        }

    }

    override fun getItemCount(): Int {
       return diff.currentList.size
    }
    private var onItemClickListener : ((ExploreResponseItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ExploreResponseItem) -> Unit){
        onItemClickListener = listener
    }

}