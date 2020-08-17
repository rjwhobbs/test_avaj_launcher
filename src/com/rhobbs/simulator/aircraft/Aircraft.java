package com.rhobbs.simulator.aircraft;

import com.rhobbs.simulator.weather.Coordinates;

public abstract class Aircraft {
  protected long id;
  protected String name;
  protected Coordinates coordinates;

  private static long idCounter = 0;

  protected Aircraft(String name, Coordinates coordinates) {
    System.out.println("Aircraft con called");
    this.name = name;
    this.coordinates = coordinates;
    this.id = nextId();
  }

  public void showCoords() {
    System.out.println(
            this.coordinates.getLongitude() + " " +
            this.coordinates.getLatitude() + " " +
            this.coordinates.getHeight()
    );
  }

  private long nextId() {
    return Aircraft.idCounter += 1;
  }
}