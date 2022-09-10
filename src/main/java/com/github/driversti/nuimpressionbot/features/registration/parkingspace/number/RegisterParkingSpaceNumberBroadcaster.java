package com.github.driversti.nuimpressionbot.features.registration.parkingspace.number;

import com.github.driversti.nuimpressionbot.common.AbstractBroadcaster;
import com.github.driversti.nuimpressionbot.common.Constants;
import com.github.driversti.nuimpressionbot.common.UpdateHandler;
import com.github.driversti.nuimpressionbot.features.registration.RegistrationProcessStorage;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegisterParkingSpaceNumberBroadcaster
    extends AbstractBroadcaster
    implements UpdateHandler {

  private final RegistrationProcessStorage processStorage;

  public RegisterParkingSpaceNumberBroadcaster(
      ApplicationEventPublisher publisher,
      RegistrationProcessStorage processStorage) {
    super(publisher);
    this.processStorage = processStorage;
  }

  @Override
  public void handle(Update update) {
    Optional<Long> chatIdOpt = findChatId(update);
    if (chatIdOpt.isEmpty()) {
      return;
    }
    Long chatId = chatIdOpt.get();
    Optional<Integer> numberOpt = Optional.ofNullable(update.callbackQuery())
        .map(CallbackQuery::data)
        .filter(data -> data.startsWith(Constants.REG_PS_NUMBER_PREFIX))
        .map(prefixed -> prefixed.substring(Constants.REG_PS_NUMBER_PREFIX.length()))
        .map(Integer::parseInt);
    if (numberOpt.isEmpty()) {
      return;
    }
    broadcast(new RegisterParkingSpaceNumberEvent(chatId, numberOpt.get(), messageId(update)));
  }
}
