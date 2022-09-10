package com.github.driversti.nuimpressionbot.features.registration.apartment.floor;

import static com.github.driversti.nuimpressionbot.common.Constants.REG_FLOOR_PREFIX;

import com.github.driversti.nuimpressionbot.common.AbstractBroadcaster;
import com.github.driversti.nuimpressionbot.common.Floor;
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
public class RegistrationFloorBroadcaster
    extends AbstractBroadcaster
    implements UpdateHandler {

  private final RegistrationProcessStorage processStorage;

  public RegistrationFloorBroadcaster(
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
    if (!processStorage.expectsFloor(chatId)) {
      return;
    }
    Optional<Floor> floorOpt = Optional.ofNullable(update.callbackQuery())
        .map(CallbackQuery::data)
        .filter(data -> data.startsWith(REG_FLOOR_PREFIX))
        .map(prefixedFloor -> prefixedFloor.substring(REG_FLOOR_PREFIX.length()))
        .map(Integer::parseInt)
        .map(Floor::fromNumber);
    if (floorOpt.isEmpty()) {
      return;
    }
    Floor floor = floorOpt.get();
    broadcast(new RegisterFloorReceivedEvent(chatId, floor, messageId(update)));
  }
}
