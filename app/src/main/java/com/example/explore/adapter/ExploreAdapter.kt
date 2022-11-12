package com.example.explore.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.explore.R
import com.example.explore.api.ExploreResponseItem
import kotlinx.android.synthetic.main.country.view.*

class ExploreAdapter:RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>() {
   lateinit var exploreResponseItem : ExploreResponseItem

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
            txtCountryName.text = exploreResponseItem.name.toString()
            txtCountryCap.text = exploreResponseItem.capital.toString()
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