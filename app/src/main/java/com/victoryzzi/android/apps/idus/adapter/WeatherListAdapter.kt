package com.victoryzzi.android.apps.idus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.victoryzzi.android.apps.idus.R
import com.victoryzzi.android.apps.idus.adapter.viewholder.WeatherViewHolder
import com.victoryzzi.android.apps.idus.data.model.WeatherItem

class WeatherListAdapter : ListAdapter<WeatherItem, WeatherViewHolder>(WeatherDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder = WeatherViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_weather,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: MutableList<WeatherItem>?) {
        super.submitList(list?.let {
            ArrayList(it)
        })
    }
}

class WeatherDiffCallBack : DiffUtil.ItemCallback<WeatherItem>() {
    override fun areItemsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean =
        oldItem.locationName == newItem.locationName

    override fun areContentsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean =
        oldItem == newItem
}