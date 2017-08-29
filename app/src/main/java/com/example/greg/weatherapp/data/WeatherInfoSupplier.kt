package com.example.greg.weatherapp.data

import com.google.gson.Gson
import java.net.URL

/**
 * Created by greg on 16/08/2017.
 */

class ForecastRequest {
    companion object {
        private val APP_ID = "9e82aa1db39324e58a3b9f2279e55e46"

        private val GROUP_URL = "http://api.openweathermap.org/data/2.5/group?"
        private val SINGLE_URL = "http://api.openweathermap.org/data/2.5/weather?"

        private val COMPLETE_SINGLE_URL = "${SINGLE_URL}APPID=${APP_ID}&units=metric&id="
        private val COMPLETE_GROUP_URL = "${GROUP_URL}APPID=${APP_ID}&units=metric&id="
    }

    fun singleRequest(id: Int): ForecastResult = request(COMPLETE_SINGLE_URL + id)

    private inline fun<reified T> request(url: String): T {
        val forecastJsonStr = URL(url).readText()
        return Gson().fromJson(forecastJsonStr, T::class.java)
    }

    fun groupRequest(ids: List<Int>): List<ForecastResult> {
        if (ids.size > 20) {
            throw IllegalArgumentException() // API doesn't like long queries
        }
        return request<Result>(StringBuilder(COMPLETE_GROUP_URL).apply {
            ids.forEach {
                append(it)
                append(',')
            }
            deleteCharAt(length - 1)
        }.toString()).list
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
        val pressure: Float,
        val humidity: Int
)

