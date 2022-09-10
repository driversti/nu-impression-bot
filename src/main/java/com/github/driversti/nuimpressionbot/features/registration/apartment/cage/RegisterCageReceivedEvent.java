package com.github.driversti.nuimpressionbot.features.registration.apartment.cage;

import com.github.driversti.nuimpressionbot.building.Cage;
import com.github.driversti.nuimpressionbot.common.Event;

public record RegisterCageReceivedEvent(Long chatId, Cage cage, Integer messageId)
    implements Event {

}
