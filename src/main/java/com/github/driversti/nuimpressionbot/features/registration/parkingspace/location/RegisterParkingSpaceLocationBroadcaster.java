package com.github.driversti.nuimpressionbot.features.registration.parkingspace.location;

import static com.github.driversti.nuimpressionbot.common.Constants.REG_PS_LOCATION_PREFIX;

import com.github.driversti.nuimpressionbot.common.AbstractBroadcaster;
import com.github.driversti.nuimpressionbot.common.ParkingSpaceLocation;
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
public class RegisterParkingSpaceLocationBroadcaster
    extends AbstractBroadcaster
    implements UpdateHandler {

  private final RegistrationProcessStorage processStorage;

  public RegisterParkingSpaceLocationBroadcaster(
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
    Optional<String> locationOpt = Optional.ofNullable(update.callbackQuery())
        .map(CallbackQuery::data)
        .filter(data -> data.startsWith(REG_PS_LOCATION_PREFIX))
        .map(prefixed -> prefixed.substring(REG_PS_LOCATION_PREFIX.length()));
    if (locationOpt.isEmpty()) {
      return;
    }
    ParkingSpaceLocation location = ParkingSpaceLocation.valueOf(locationOpt.get());
    broadcast(new RegisterParkingSpaceLocationEvent(chatId, location, messageId(update)));
  }
}
