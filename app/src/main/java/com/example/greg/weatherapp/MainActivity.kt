package com.example.greg.weatherapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.util.Log
import android.widget.EditText
import com.example.greg.weatherapp.data.mapping.CityForecast
import com.example.greg.weatherapp.data.mapping.RequestGroupForecastCommand
import com.example.greg.weatherapp.data.mapping.RequestSingleForecastCommand
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
            val result: List<CityForecast> = when {
                citiesIds.size > 1 -> RequestGroupForecastCommand(citiesIds).execute()
                citiesIds.size == 1 -> listOf(RequestSingleForecastCommand(citiesIds[0]).execute())
                else -> listOf()
            }

            uiThread {
                forecast_list.adapter = ForecastListAdapter(result, { (city) -> toast(city) })
            }
        }


        fab.setOnClickListener { view ->
            val builder = AlertDialog.Builder(this@MainActivity)
            val input = EditText(this@MainActivity)
            input.inputType = InputType.TYPE_CLASS_NUMBER
            builder.setView(input)

            builder.setPositiveButton("OK", { _, _ ->
                val newId = Integer.parseInt(input.text.toString())
                doAsync({ thr -> Log.d("error", thr.toString())}) {
                    val result = RequestSingleForecastCommand(newId).execute()
                    uiThread {
                        (forecast_list.adapter as ForecastListAdapter).addCityForecast(result)
                        Snackbar.make(view, "New city $newId added!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()
                    }
                }
            })
            builder.setNegativeButton("CANCEL", { dialog, _ -> dialog.cancel() })

            builder.show()
        }
    }

}
