package com.github.driversti.nuimpressionbot.features.registration.apartment.number;

import com.github.driversti.nuimpressionbot.common.Event;

public record RegisterApartmentDataCorrectEvent(Long chatId, Integer messageId) implements Event {

}
