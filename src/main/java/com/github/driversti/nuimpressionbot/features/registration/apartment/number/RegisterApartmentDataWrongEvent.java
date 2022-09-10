package com.github.driversti.nuimpressionbot.features.registration.apartment.number;

import com.github.driversti.nuimpressionbot.common.Event;

public record RegisterApartmentDataWrongEvent(Long chatId, Integer messageId) implements Event {

}
