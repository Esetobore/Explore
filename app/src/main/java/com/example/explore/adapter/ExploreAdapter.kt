package com.example.explore.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.explore.R
import com.example.explore.api.ExploreResponseItem
import com.example.explore.util.ExploreItem
import kotlinx.android.synthetic.main.country.view.*
import java.util.*
import kotlin.collections.ArrayList

class ExploreAdapter:RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>() {

    inner class ExploreViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    private val diffCallBack = object : DiffUtil.ItemCallback<ExploreResponseItem>(){
        override fun areItemsTheSame(oldItem: ExploreResponseItem, newItem: ExploreResponseItem): Boolean {
            return oldItem.name == newItem.name
        }
        override fun areContentsTheSame(oldItem: ExploreResponseItem, newItem: ExploreResponseItem): Boolean {
          return oldItem.hashCode() == newItem.hashCode()
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
            txtCountryName.text = explore.name?.common
            txtCountryCap.text = explore.capital?.get(0)
            Glide.with(this).load(explore.flag).into(ImgFlag)
            setOnItemClickListener {
                onItemClickListener?.let { it(explore) }
            }
        }

    }

    override fun getItemCount(): Int {
       return diff.currentList.size
    }

    fun submitList(list1: List<ExploreResponseItem>) = diff.submitList(list1)

    private var onItemClickListener : ((ExploreResponseItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ExploreResponseItem) -> Unit){
        onItemClickListener = listener
    }

    }