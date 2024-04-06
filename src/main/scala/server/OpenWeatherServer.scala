package server

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits._

object OpenWeatherServer extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(OpenWeatherService.service.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}