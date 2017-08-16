package com.example.greg.weatherapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.greg.weatherapp.dataMapping.RequestForecastCommand
import com.example.greg.weatherapp.view.ForecastListAdapter

import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val citiesIds = listOf(498817, 524901, 5128638, 6359304, 2643743, 615532, 611717, 292223)

        forecast_list.layoutManager = LinearLayoutManager(this)
        doAsync {
            val result =  RequestForecastCommand(citiesIds).execute()
            uiThread {
                forecast_list.adapter = ForecastListAdapter(result, { (city) -> toast(city) })
            }
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
