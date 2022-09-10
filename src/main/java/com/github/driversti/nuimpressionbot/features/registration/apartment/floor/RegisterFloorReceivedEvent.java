package com.github.driversti.nuimpressionbot.features.registration.apartment.floor;

import com.github.driversti.nuimpressionbot.common.Event;
import com.github.driversti.nuimpressionbot.common.Floor;

public record RegisterFloorReceivedEvent(Long chatId, Floor floor, Integer messageId) implements Event {

}
