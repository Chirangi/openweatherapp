package models

case class CurrentWeather(temp: Double, feels_like: Double, weather: List[WeatherCondition]) {

  //convert from Kelvin to Fahrenheit
  def tempFahrenheit: Int = ((temp - 273.15) * 9 / 5 + 32).toInt

  def feelsLikeFahrenheit: Int = ((feels_like - 273.15) * 9 / 5 + 32).toInt

  val category = WeatherCategory.categorizeTemperature(feelsLikeFahrenheit)
  val categoryDescription = WeatherCategory.getCategoryDescription(category)
}

case class WeatherCondition(description: String)

case class WeatherAlert(event: String, description: String)

case class OpenWeatherResponse(current: CurrentWeather, alerts: Option[List[WeatherAlert]])
