package com.dewisri.smartfarming.utils

object ApiEndpoint {
    var BASEURL = "http://api.openweathermap.org/data/2.5/"
    var CurrentWeather = "weather?"
    var ListWeather = "forecast?"
    var Daily = "forecast/daily?"
    var apiKey = "8118ed6ee68db2debfaaa5a44c832918"
    var UnitsAppid = "&units=metric&appid=apiKey"
    var UnitsAppidDaily = "&units=metric&cnt=15&appid=apiKey"
}