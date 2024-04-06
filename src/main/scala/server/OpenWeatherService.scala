package server

import cats.effect.IO
import io.circe.generic.auto._
import io.circe.parser._
import models.{CurrentWeather, OpenWeatherResponse}
import org.http4s._
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.client.Client
import org.http4s.dsl.io._
import org.http4s.implicits._

object LatParam extends QueryParamDecoderMatcher[Double]("lat")

object LongParam extends QueryParamDecoderMatcher[Double]("lon")

object OpenWeatherService {

  private val apiKey = System.getenv("WEATHER_API_KEY")
  private val baseUri = uri"https://api.openweathermap.org/data/3.0/onecall?"
  //we don't need anything other than current weather and alerts
  private val exclusions = List("minutely", "hourly", "daily")

  private def buildUri(lat: Double, lon: Double): Uri = {
    val params = Map(
      "lat" -> lat.toString,
      "lon" -> lon.toString,
      "exclude" -> exclusions.mkString(","),
      "appid" -> apiKey
    )
    baseUri.withQueryParams(params)
  }

  val service: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "weather" :? LatParam(lat) +& LongParam(lon) =>
      BlazeClientBuilder[IO].resource.use { client =>
        getWeather(lat, lon, client).flatMap { weatherResponse =>
          val current = weatherResponse.current
          val alerts = weatherResponse.alerts.getOrElse(List.empty)
          val currentDetails =
            s"Current Weather: ${current.tempFahrenheit} degrees Fahrenheit, it feels ${current.categoryDescription} with ${current.weather.headOption.map(_.description).getOrElse("")} outside."
          val alertDetails =
            if (alerts.isEmpty) "No weather alerts."
            else alerts.map(a => s"Weather Alert: ${a.event}, Description: ${a.description}").mkString("\n")
          Ok(s"$currentDetails\n$alertDetails")
        }
      }
  }

  private def getWeather(lat: Double, lon: Double, client: Client[IO]): IO[OpenWeatherResponse] = {
    val uri = OpenWeatherService.buildUri(lat, lon)
    client.expect[String](uri).flatMap { response =>
      IO.fromEither(decode[OpenWeatherResponse](response))
    }.handleErrorWith { _ =>
      IO.pure(OpenWeatherResponse(CurrentWeather(0.0, 0.0, List.empty), None))
    }
  }

}
