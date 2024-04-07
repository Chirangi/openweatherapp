package models

import models.WeatherCategory.WeatherCategory

case class CurrentWeather(temp: Double, feels_like: Double, weather: List[WeatherCondition]) {

  //convert from Kelvin to Fahrenheit
  def tempFahrenheit: Int = ((temp - 273.15) * 9 / 5 + 32).toInt
  def feelsLikeFahrenheit: Int = ((feels_like - 273.15) * 9 / 5 + 32).toInt

  val category: WeatherCategory = WeatherCategory.categorizeTemperature(feelsLikeFahrenheit)
  val categoryDescription: String = WeatherCategory.getCategoryDescription(category)
}

case class WeatherCondition(description: String)

case class WeatherAlert(event: String, description: String)

case class OpenWeatherResponse(current: CurrentWeather, alerts: Option[List[WeatherAlert]])

object OpenWeatherResponse {
  def formatResponse(weatherResponse: OpenWeatherResponse): String = {
    val current = weatherResponse.current
    val alerts = weatherResponse.alerts.getOrElse(List.empty)
    val currentDetails =
      s"Current Weather: ${current.tempFahrenheit} degrees Fahrenheit, " +
        s"it feels ${current.categoryDescription} " +
        s"with ${current.weather.headOption.map(_.description).getOrElse("")} outside."
    val alertDetails =
      if (alerts.isEmpty) "No weather alerts."
      else alerts.map(a => s"Weather Alert: ${a.event}, Description: ${a.description}").mkString("\n")
    s"$currentDetails\n$alertDetails"
  }
}
