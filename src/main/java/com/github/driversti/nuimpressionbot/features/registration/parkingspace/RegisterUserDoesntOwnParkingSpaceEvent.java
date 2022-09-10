package com.github.driversti.nuimpressionbot.features.registration.parkingspace;

import com.github.driversti.nuimpressionbot.common.Event;

public record RegisterUserDoesntOwnParkingSpaceEvent(Long chatId, Integer messageId)
    implements Event {

}
