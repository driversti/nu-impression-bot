package com.github.driversti.nuimpressionbot.features.registration.parkingspace.number;

import com.github.driversti.nuimpressionbot.features.registration.RegistrationProcessStorage;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterParkingSpaceNumberListener {

  private final TelegramBot bot;
  private final RegistrationProcessStorage processStorage;

  @EventListener
  public void process(RegisterParkingSpaceNumberEvent event) {
    Long chatId = event.chatId();
    log.info("User's {} parking space number: {}", chatId, event.number());
    String message = "Received!";
    bot.execute(new SendMessage(chatId, message)
        .replyMarkup(new ReplyKeyboardRemove()));
  }
}
