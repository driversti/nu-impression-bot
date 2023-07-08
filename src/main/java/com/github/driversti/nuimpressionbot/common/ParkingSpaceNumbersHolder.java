package com.github.driversti.nuimpressionbot.common;

import java.util.List;
import java.util.stream.IntStream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ParkingSpaceNumbersHolder {

  public List<Integer> numbers(ParkingSpaceLocation location) {
// TODO: consider replacing with strategy
    if (location == ParkingSpaceLocation.OUTSIDE) {
      return createList(51, 113);
    }
    if (location == ParkingSpaceLocation.UNDERGROUND) {
      return createList(1, 130);
    }
    throw new IllegalArgumentException("Location is not expected: " + location);
  }

  private static List<Integer> createList(int startInclusive, int endInclusive) {
    return IntStream.rangeClosed(startInclusive, endInclusive).boxed().toList();
  }
}
