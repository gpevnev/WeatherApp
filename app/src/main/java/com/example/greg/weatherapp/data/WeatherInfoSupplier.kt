package com.example.greg.weatherapp.data

import com.google.gson.Gson
import java.io.StringReader
import java.net.URL

/**
 * Created by greg on 16/08/2017.
 */

class ForecastRequest(val ids: List<Int>) {
    companion object {
        private val APP_ID = "9e82aa1db39324e58a3b9f2279e55e46"

        private val URL = "http://api.openweathermap.org/data/2.5/group?"
        private val COMPLETE_URL = "${URL}APPID=${APP_ID}&id="
    }

    fun proceed(): List<ForecastResult> {
        if (ids.size > 20) {
            throw IllegalArgumentException()
        }
        val forecastJsonStr = URL(StringBuilder(COMPLETE_URL).apply {
            ids.forEach {
                append(it)
                append(',')
            }
            deleteCharAt(length - 1)
            append("&units=metric")
        }.toString()).readText()
        val result = Gson().fromJson(forecastJsonStr, Result::class.java).list
        return result
    }
}

data class Result(
        val list: List<ForecastResult>
)

data class ForecastResult(
        val name: String,
        val weather: List<Weather>,
        val main: CurrentState,
        val wind: Wind
)

data class Weather(
        val id: Long,
        val main: String,
        val description: String,
        val icon: String
)

data class Wind(
        val speed: Float
)

data class CurrentState(
        val temp: Float,
        val pressure: Int,
        val humidity: Int
)

