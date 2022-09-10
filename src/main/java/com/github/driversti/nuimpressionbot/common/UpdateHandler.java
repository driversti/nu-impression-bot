package com.github.driversti.nuimpressionbot.common;

import com.pengrad.telegrambot.model.Update;

public interface UpdateHandler {

  void handle(Update update);
}
