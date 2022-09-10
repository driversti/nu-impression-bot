package com.github.driversti.nuimpressionbot.events;

import com.github.driversti.nuimpressionbot.common.Event;
import com.pengrad.telegrambot.model.Update;

public record StartCommandReceivedEvent(Update update) implements Event {

}
