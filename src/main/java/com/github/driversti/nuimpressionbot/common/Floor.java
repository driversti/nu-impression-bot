package com.github.driversti.nuimpressionbot.common;

import lombok.AllArgsConstructor;

// TODO: consider creating class Floor with appropriate number of floors.
@AllArgsConstructor
public enum Floor {
  GROUND(0),
  FIRST(1),
  SECOND(2),
  THIRD(3),
  FOURTH(4),
  FIFTH(5),
  SIXTH(6),
  SEVENTH(7);

  private final int number;

  public int asNumber() {
    return number;
  }

  public static Floor fromNumber(int number) {
    return switch (number) {
      case 0 -> GROUND;
      case 1 -> FIRST;
      case 2 -> SECOND;
      case 3 -> THIRD;
      case 4 -> FOURTH;
      case 5 -> FIFTH;
      case 6 -> SIXTH;
      case 7 -> SEVENTH;
      default -> throw new IllegalArgumentException("Floor " + number + "does not exist!");
    };
  }
}
