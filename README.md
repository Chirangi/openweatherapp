
# OpenWeather API Project
Given a latitude and longitude, it returns the temperature, what it feels like outside, weather conditions, and any weather alerts.\
This project utilized the Typelevel ecosystem (http4s, cats-effect, circe)

## Setup and Environment Variables

Easiest way would be to import the project in IntelliJ. Reload the build.sbt, add the apikey\
`WEATHER_API_KEY=your_api_key_here`\
add it to the OpenWeatherServer run configuration environment variables.\
To start the server, run the OpenWeatherServer config. As a bonus, I created OpenWeatherClient for my own ease of testing.

If you don't want to use IntelliJ, set the api key in the system via bash and check it's there
```bash
$ export WEATHER_API_KEY=value
$ echo $WEATHER_API_KEY
```
then
1. Download the project folder, go to the root

2. ```bash 
   $ sbt
   ...
   $ sbt> compile
   ...
   $ sbt> run
5. select the server.OpenWeatherServer option

The server is now running! Let's call it

## Usage/Examples

The endpoint is as follows:
```http
http://localhost:8080/weather?lat={latitutde}&lon=-{longitude}
```
replace the latitude with a value between -90 and 90 and the longitude with a value between -180 and 180\
Let's try calling this for New York City: (40, -74) and Dallas (32, -97)

In terminal
```bash
$ curl "http://localhost:8080/weather?lat=40&lon=-74"
$ Current Weather: 49 degrees Fahrenheit, it feels moderate with overcast clouds outside.
Weather Alert: Small Craft Advisory, Description: * WHAT...North winds 10 to 15 kt with gusts up to 25 kt and seas
3 to 5 ft.

* WHERE...Coastal waters from Sandy Hook to Manasquan Inlet NJ
out 20 nm and Coastal waters from Manasquan Inlet to Little
Egg Inlet NJ out 20 nm.

* WHEN...Until 10 PM EDT this evening.

* IMPACTS...Conditions will be hazardous to small craft.
  
$ curl "http://localhost:8080/weather?lat=32&lon=-97"
$ Current Weather: 58 degrees Fahrenheit, it feels moderate with overcast clouds outside.
  No weather alerts.
```
Endpoint also be accessed via web browser or by the client app.

## Project Structure
### Models
WeatherCategory provides a description for the "feels like" temperature. Hot/moderate/cold was not granular enough in my opinion, so I added warm and chilly as extra descriptors.\
WeatherModels has the case classes for modeling the current data and alerts. It is here that I do the conversions from Kelvin.
### Server
Server file runs the server.\
Service file has all the logic for URI building, input checking, JSON parsing, and response handling.
### Client
Bonus lightweight client I added so testing would be easier.
### Resources
Sample JSON responses for the weather in NYC and Dallas on April 6, 2024.
### Test
Some testing on the temperature translations and message building

## Future Optimizations

1. I am not a fan of the way I currently display weather alerts. I will need to find locations with alerts that aren't nautical and think of a way to decompose the message to something more relatable.
2. Add unit tests for the server/JSON parsing - In an effort to be transparent about time spent: I did not want to spend more than a few hours on this over the weekend, and was having some issues getting Mockito to work for the service. I instead elected to add checks on the endpoint inputs to make sure they're valid (numeric only, and within the allowed range)
3. Add some more detailed server side logs