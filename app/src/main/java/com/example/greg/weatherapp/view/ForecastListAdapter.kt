package com.example.greg.weatherapp.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greg.weatherapp.R
import com.example.greg.weatherapp.dataMapping.CityForecast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.city_item.view.*

/**
 * Created by greg on 16/08/2017.
 */
class ForecastListAdapter(
        private val cities: List<CityForecast>,
        private val itemClick: (CityForecast) -> Unit
) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.city_item, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindForecast(cities[position])
    }

    override fun getItemCount() = cities.size

    class ViewHolder(
            private val view: View,
            private val itemClickListener: (CityForecast) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        fun bindForecast(forecast: CityForecast) {
            with(forecast) {
                itemView.apply {
                    Picasso.with(itemView.context).load(iconUrl).into(iconView)
                    cityNameView.text = city
                    humidityView.text = humidity.toString()
                    currentTemperatureView.text = "$temp C"
                    descriptionView.text = description
                    itemView.setOnClickListener { itemClickListener(forecast) }
                }
            }
        }
    }
}