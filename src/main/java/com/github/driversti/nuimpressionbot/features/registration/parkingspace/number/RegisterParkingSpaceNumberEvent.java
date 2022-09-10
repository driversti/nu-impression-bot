package com.github.driversti.nuimpressionbot.features.registration.parkingspace.number;

import com.github.driversti.nuimpressionbot.common.Event;

public record RegisterParkingSpaceNumberEvent(Long chatId, Integer number, Integer messageId)
    implements Event {

}
