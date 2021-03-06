package com.rhobbs.simulator.aircraft;

import com.rhobbs.simulator.WeatherTower;
import com.rhobbs.simulator.weather.Coordinates;
import com.rhobbs.simulator.weather.CoordinatesFactory;

import java.util.logging.Logger;

public class JetPlane extends Aircraft implements Flyable {

  private WeatherTower weatherTower;

  JetPlane(String name, Coordinates coordinates) {
    super(name, coordinates);
    this.type = "JetPlane";
  }

  @Override
  public void updateConditions() {
    int lon = this.coordinates.getLongitude();
    int lat = this.coordinates.getLatitude();
    int height = this.coordinates.getHeight();
    String message = "";

    switch (weatherTower.getWeather(this.coordinates)) {
      case "sun":
        lon += 10;
        height += 2;
        message = "Suns out, guns out.";
        break;
      case "rain":
        lon += 5;
        message = "I pitty the fool flying a baloon in this rain.";
        break;
      case "fog":
        lon += 1;
        message = "Can't see a thing in this fog.";
        break;
      case "snow":
        height += -12;
        message = "Need more speed, this snow is getting heavy.";
        break;
      default: break;
    }
    Logger.getLogger("simulation").info(
            this.type  + "#" + this.name +
            "(" + this.id + "): " + message);
    if (height > 100) {
      height = 100;
    } else if (height  <= 0) {
      height = 0;
      this.coordinates = CoordinatesFactory.newCoordinates(lon, lat, height);
      this.weatherTower.unregister(this);
      Logger.getLogger("simulation").info(
            "Tower says: " + this.type + "#" + this.name +
                    "(" + this.id + ") unregistered from weather tower."
      );
      this.coordinates = CoordinatesFactory.newCoordinates(lon, lat, height);
      Logger.getLogger("simulation").info(
              this.type  + "#" + this.name +
                      "(" + this.id + ") landing."
      );
      return ;
    }
    this.coordinates = CoordinatesFactory.newCoordinates(lon, lat, height);
  }

  @Override
  public void registerTower(WeatherTower weatherTower) {
    this.weatherTower = weatherTower;
    this.weatherTower.register(this);
    Logger.getLogger("simulation").info(
            "Tower says: " + this.type + "#" + this.name +
                    "(" + this.id + ") registered to weather tower."
    );
  }
}
