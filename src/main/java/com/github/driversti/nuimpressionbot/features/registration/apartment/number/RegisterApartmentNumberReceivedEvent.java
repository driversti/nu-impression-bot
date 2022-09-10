package com.github.driversti.nuimpressionbot.features.registration.apartment.number;

import com.github.driversti.nuimpressionbot.common.Event;

public record RegisterApartmentNumberReceivedEvent(Long chatId, Integer apartment,
                                                   Integer messageId) implements Event {

}
