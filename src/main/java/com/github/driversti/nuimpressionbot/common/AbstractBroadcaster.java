package com.github.driversti.nuimpressionbot.common;

import com.github.driversti.nuimpressionbot.exceptions.UserIdNotFoundException;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
public abstract class AbstractBroadcaster implements Broadcaster {

  protected final ApplicationEventPublisher publisher;

  @Override
  public void broadcast(Event event) {
    publisher.publishEvent(event);
  }

  protected Optional<Long> findChatId(Update update) {
    return Optional.ofNullable(update.callbackQuery())
        .map(CallbackQuery::from)
        .map(User::id);
  }

  protected Long getChatId(Update update) {
    return Optional.ofNullable(update.callbackQuery())
        .map(CallbackQuery::from)
        .map(User::id)
        .orElseThrow(() -> new UserIdNotFoundException(update.toString()));
  }

  protected Optional<String> findData(Update update) {
    return Optional.ofNullable(update.callbackQuery())
        .map(CallbackQuery::data);
  }

  protected Integer messageId(Update update) {
    return update.callbackQuery().message().messageId();
  }
}
