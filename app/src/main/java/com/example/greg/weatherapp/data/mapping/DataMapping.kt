package com.example.greg.weatherapp.data.mapping

import com.example.greg.weatherapp.data.ForecastRequest
import com.example.greg.weatherapp.data.ForecastResult

/**
 * Created by greg on 16/08/2017.
 */
data class CityForecast(
        val city: String,
        val temp: Int,
        val description: String,
        val humidity: Int,
        val iconUrl: String
)

class ForecastDataMapper {
    fun convertFromDataModel(forecast: List<ForecastResult>): List<CityForecast> {
        return forecast.map {  with(it) {
            CityForecast(
                    name,
                    main.temp.toInt(),
                    weather[0].description,
                    main.humidity,
                    generateIconUrl(weather[0].icon)
            )
            }
        }

    }


    private fun generateIconUrl(iconCode: String) = "http://openweathermap.org/img/w/$iconCode.png"
}

class RequestGroupForecastCommand(val ids: List<Int>) : Command<List<CityForecast>> {
    override fun execute(): List<CityForecast> {
        return ForecastDataMapper().convertFromDataModel(ForecastRequest().groupRequest(ids))
    }
}

class RequestSingleForecastCommand(val id: Int) : Command<CityForecast> {
    override fun execute(): CityForecast {
        return ForecastDataMapper().convertFromDataModel(listOf(ForecastRequest().singleRequest(id)))[0]
    }
}