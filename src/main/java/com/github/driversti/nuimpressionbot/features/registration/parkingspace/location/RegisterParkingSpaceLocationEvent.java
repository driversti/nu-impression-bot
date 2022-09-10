package com.github.driversti.nuimpressionbot.features.registration.parkingspace.location;

import com.github.driversti.nuimpressionbot.common.Event;
import com.github.driversti.nuimpressionbot.common.ParkingSpaceLocation;

public record RegisterParkingSpaceLocationEvent(
    Long chatId, ParkingSpaceLocation location, Integer messageId) implements Event {

}
