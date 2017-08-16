package com.example.greg.weatherapp.dataMapping

/**
 * Created by greg on 16/08/2017.
 */
interface Command<T> {
    fun execute(): T
}