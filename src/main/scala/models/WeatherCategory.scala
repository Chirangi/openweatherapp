package models

object WeatherCategory extends Enumeration {
  type WeatherCategory = Value

  val Hot, Warm, Moderate, Chilly, Cold = Value

  def categorizeTemperature(tempFahrenheit: Int): WeatherCategory = {
    tempFahrenheit match {
      case t if t >= 80             => Hot
      case t if t >= 65 && t < 80   => Warm
      case t if t >= 45 && t < 65   => Moderate
      case t if t >= 32 && t < 45   => Chilly
      case t if t < 32              => Cold
      case _ => throw new IllegalArgumentException("Invalid temperature value")
    }
  }

  def getCategoryDescription(category: WeatherCategory): String = {
    category match {
      case Hot       => "hot"
      case Warm      => "warm"
      case Moderate  => "moderate"
      case Chilly    => "chilly"
      case Cold      => "cold"
    }
  }
}
