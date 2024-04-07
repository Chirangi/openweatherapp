package server

import cats.effect.IO
import io.circe.generic.auto._
import io.circe.parser._
import models.OpenWeatherResponse
import org.http4s._
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.client.Client
import org.http4s.dsl.io._
import org.http4s.implicits._

import scala.util.Try

object LatParam extends QueryParamDecoderMatcher[String]("lat")

object LongParam extends QueryParamDecoderMatcher[String]("lon")

object OpenWeatherService {

  private val apiKey = System.getenv("WEATHER_API_KEY")
  private val baseUri = uri"https://api.openweathermap.org/data/3.0/onecall?"
  //we don't need anything other than current weather and alerts
  private val exclusions = List("minutely", "hourly", "daily")

  private def isValidLatitude(lat: Double): Boolean = lat >= -90 && lat <= 90
  private def isValidLongitude(lon: Double): Boolean = lon >= -180 && lon <= 180

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
        getWeather(lat, lon, client).flatMap {
          case Right(weatherResponse) =>
            val responseString = OpenWeatherResponse.formatResponse(weatherResponse)
            Ok(responseString)
          case Left(errorMessage) =>
            BadRequest(errorMessage)
        }
      }
  }

  private def getWeather(latStr: String, lonStr: String, client: Client[IO]): IO[Either[String, OpenWeatherResponse]] = {
    val latResult = Try(latStr.toDouble).toOption
    val lonResult = Try(lonStr.toDouble).toOption

    (latResult, lonResult) match {
      case (Some(lat), Some(lon)) =>
        if (isValidLatitude(lat) && isValidLongitude(lon)) {
          val uri = OpenWeatherService.buildUri(lat, lon)
          client.expect[String](uri).flatMap { response =>
            IO.fromEither(decode[OpenWeatherResponse](response))
              .map(Right(_))
              .handleErrorWith { _ =>
                IO.pure(Left("Failed to decode weather response from OpenWeather"))
              }
          }
        } else {
          IO.pure(Left("Invalid values. Please verify that your:\n" +
            "1) Latitude is between -90 and 90\n" +
            "2) Longitude is between -180 and 180"))
        }
      case (_, _) =>
        IO.pure(Left("Non-numeric input for latitude or longitude."))
    }
  }
}
