package com.example.greg.weatherapp.view

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.greg.weatherapp.R
import com.example.greg.weatherapp.data.mapping.CityForecast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.city_item.view.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by greg on 16/08/2017.
 */
class ForecastListAdapter(
        citiesList: List<CityForecast>,
        private val itemClick: (CityForecast) -> Unit
) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {
    private val cities = citiesList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.city_item, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.setOnTouchListener(object : View.OnTouchListener {
            private var x1: Float = 0.toFloat()
            private var x2: Float = 0.toFloat()
            val MIN_DISTANCE = 150

            override fun onTouch(p0: View, event: MotionEvent): Boolean {
                when(event.action) {
                    MotionEvent.ACTION_DOWN -> x1 = event.x

                    MotionEvent.ACTION_UP -> {
                        x2 = event.x
                        Log.d("swipe", "${Math.abs(x2 - x1)} confirmed!")

                    }
                }
                return true
            }
        })
            holder.bindForecast(cities[position])
    }

    override fun getItemCount() = cities.size

    fun addCityForecast(forecast: CityForecast) {
        cities.add(forecast)
        notifyItemInserted(cities.size - 1)
    }

    class ViewHolder(
            public val view: View,
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