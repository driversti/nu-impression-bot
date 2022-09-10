package com.github.driversti.nuimpressionbot;

import com.github.driversti.nuimpressionbot.common.UpdateHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BotRunner {

  private final TelegramBot bot;
  private final Collection<UpdateHandler> updateHandlers;

  @EventListener(ApplicationReadyEvent.class)
  public void handleRequests() {
    bot.setUpdatesListener(updates -> {
      updates.forEach(this::handleRequest);
      return UpdatesListener.CONFIRMED_UPDATES_ALL;
    });
  }

  void handleRequest(Update update) {
    updateHandlers.forEach(broadcaster -> broadcaster.handle(update));
  }
}
