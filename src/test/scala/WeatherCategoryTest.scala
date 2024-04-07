import models.{CurrentWeather, OpenWeatherResponse, WeatherCategory, WeatherCondition}
import models.WeatherCategory._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class WeatherCategoryTest extends AnyFlatSpec with Matchers {

  "categorizeTemperature" should "correctly categorize hot temperatures" in {
    categorizeTemperature(80) shouldBe Hot
    categorizeTemperature(100) shouldBe Hot
  }

  it should "correctly categorize warm temperatures" in {
    categorizeTemperature(79) shouldBe Warm
    categorizeTemperature(65) shouldBe Warm
  }

  it should "correctly categorize moderate temperatures" in {
    categorizeTemperature(45) shouldBe Moderate
    categorizeTemperature(64) shouldBe Moderate
  }

  it should "correctly categorize chilly temperatures" in {
    categorizeTemperature(32) shouldBe Chilly
    categorizeTemperature(44) shouldBe Chilly
  }

  it should "correctly categorize cold temperatures" in {
    categorizeTemperature(0) shouldBe Cold
    categorizeTemperature(31) shouldBe Cold
  }

  "getCategoryDescription" should "return correct descriptions for each category" in {
    getCategoryDescription(Hot) shouldBe "hot"
    getCategoryDescription(Warm) shouldBe "warm"
    getCategoryDescription(Moderate) shouldBe "moderate"
    getCategoryDescription(Chilly) shouldBe "chilly"
    getCategoryDescription(Cold) shouldBe "cold"
  }

  "CurrentWeather" should "convert temperatures from Kelvin to Fahrenheit correctly" in {
    val currentWeather = CurrentWeather(temp = 300, feels_like = 280, List.empty)
    currentWeather.tempFahrenheit shouldEqual 80
    currentWeather.feelsLikeFahrenheit shouldEqual 44
  }

  it should "format response correctly when there are no alerts" in {
    val weather = CurrentWeather(temp = 300, feels_like = 280, List(WeatherCondition("cloud coverage")))
    val response = OpenWeatherResponse(weather, None)
    val formattedResponse = OpenWeatherResponse.formatResponse(response)
    formattedResponse should include("Current Weather: 80 degrees Fahrenheit")
    formattedResponse should include("feels chilly")
    formattedResponse should include("with cloud coverage outside.")
    formattedResponse should include("No weather alerts.")
  }
}