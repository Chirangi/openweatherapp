package client

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s._
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.client.Client

object OpenWeatherClient extends IOApp {

  //NYC lat and lon, can change these to get weather of other cities
  private val lat = "40.0"
  private val lon = "-74.0"

  override def run(args: List[String]): IO[ExitCode] =
    BlazeClientBuilder[IO].resource.use { client =>
      val uri = Uri.uri("http://localhost:8080/weather").withQueryParam("lat", lat).withQueryParam("lon", lon)
      retrieveWeather(client, uri).flatMap { response =>
        IO(println(response))
      }.as(ExitCode.Success)
    }

  private def retrieveWeather(client: Client[IO], uri: Uri): IO[String] =
    client.expect[String](uri)
}
