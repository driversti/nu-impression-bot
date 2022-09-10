package com.github.driversti.nuimpressionbot.features.registration.apartment.number;

import com.github.driversti.nuimpressionbot.common.AbstractBroadcaster;
import com.github.driversti.nuimpressionbot.common.Constants;
import com.github.driversti.nuimpressionbot.common.UpdateHandler;
import com.github.driversti.nuimpressionbot.features.registration.RegistrationProcessStorage;
import com.pengrad.telegrambot.model.Update;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegisterApartmentNumberBroadcaster
    extends AbstractBroadcaster
    implements UpdateHandler {

  private final RegistrationProcessStorage processStorage;

  // TODO: add linter
  public RegisterApartmentNumberBroadcaster(
      ApplicationEventPublisher publisher, RegistrationProcessStorage processStorage) {
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
    if (!processStorage.expectsApartment(chatId)) {
      return;
    }
    Optional<Integer> apartmentOpt = findData(update)
        .filter(data -> data.startsWith(Constants.REG_AP_PREFIX))
        .map(data -> data.substring(Constants.REG_AP_PREFIX.length()))
        .map(Integer::parseInt);
    if (apartmentOpt.isEmpty()) {
      return;
    }

    broadcast(
        new RegisterApartmentNumberReceivedEvent(chatId, apartmentOpt.get(), messageId(update)));
  }
}
