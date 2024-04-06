class SampleRequests {

  class JSON {
    val New_York =
      """{
        "lat": 40,
        "lon": -74,
        "timezone": "America/New_York",
        "timezone_offset": -14400,
        "current": {
          "dt": 1712427845,
          "sunrise": 1712399463,
          "sunset": 1712445903,
          "temp": 284.34,
          "feels_like": 282.77,
          "pressure": 1008,
          "humidity": 48,
          "dew_point": 273.75,
          "uvi": 3.91,
          "clouds": 95,
          "visibility": 10000,
          "wind_speed": 2.24,
          "wind_deg": 44,
          "wind_gust": 6.26,
          "weather": [
            {
              "id": 804,
              "main": "Clouds",
              "description": "overcast clouds",
              "icon": "04d"
            }
          ]
        },
        "alerts": [
          {
            "sender_name": "NWS Mount Holly NJ",
            "event": "Small Craft Advisory",
            "start": 1712390160,
            "end": 1712455200,
            "description": "* WHAT...Northwest winds 15 to 20 kt with gusts up to 25 kt.\n\n* WHERE...Coastal waters from Sandy Hook to Manasquan Inlet NJ\nout 20 nm and Coastal waters from Manasquan Inlet to Little\nEgg Inlet NJ out 20 nm.\n\n* WHEN...Until 10 PM EDT this evening.\n\n* IMPACTS...Conditions will be hazardous to small craft.",
            "tags": [
              "Marine event"
            ]
          }
        ]
      }"""

    val Dallas =
      """{
        "lat": 32,
        "lon": -97,
        "timezone": "America/Chicago",
        "timezone_offset": -18000,
        "current": {
          "dt": 1712427951,
          "sunrise": 1712405351,
          "sunset": 1712451052,
          "temp": 296.47,
          "feels_like": 296.63,
          "pressure": 1012,
          "humidity": 68,
          "dew_point": 290.24,
          "uvi": 9.12,
          "clouds": 100,
          "visibility": 10000,
          "wind_speed": 8.75,
          "wind_deg": 180,
          "wind_gust": 10.8,
          "weather": [
            {
              "id": 804,
              "main": "Clouds",
              "description": "overcast clouds",
              "icon": "04d"
            }
          ]
        }
      }"""
  }

}
