package com.github.driversti.nuimpressionbot.features.registration.parkingspace;

import com.github.driversti.nuimpressionbot.common.Event;

public record RegisterUserOwnsParkingSpaceEvent(Long chatId, Integer messageId)
    implements Event {

}
