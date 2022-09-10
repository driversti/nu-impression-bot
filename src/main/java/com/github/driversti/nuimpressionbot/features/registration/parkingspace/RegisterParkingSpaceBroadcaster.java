package com.github.driversti.nuimpressionbot.features.registration.parkingspace;

import com.github.driversti.nuimpressionbot.common.AbstractBroadcaster;
import com.github.driversti.nuimpressionbot.common.Constants;
import com.github.driversti.nuimpressionbot.common.Event;
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
public class RegisterParkingSpaceBroadcaster
    extends AbstractBroadcaster
    implements UpdateHandler {

  private final RegistrationProcessStorage processStorage;

  public RegisterParkingSpaceBroadcaster(
      ApplicationEventPublisher publisher,
      RegistrationProcessStorage processManager) {
    super(publisher);
    this.processStorage = processManager;
  }

  @Override
  public void handle(Update update) {
    Optional<Long> chatIdOpt = findChatId(update);
    if (chatIdOpt.isEmpty()) {
      return;
    }
    Long chatId = chatIdOpt.get();
    Optional<String> optional = Optional.ofNullable(update.callbackQuery())
        .map(CallbackQuery::data)
        .filter(data -> data.startsWith(Constants.REG_PARKING_SPACE_PREFIX))
        .map(prefixed -> prefixed.substring(Constants.REG_PARKING_SPACE_PREFIX.length()));
    if (optional.isEmpty()) {
      return;
    }
    String yesOrNo = optional.get();
    validate(yesOrNo);
    boolean owning = yesOrNo.equalsIgnoreCase("yes");
    Event event = owning ?
        new RegisterUserOwnsParkingSpaceEvent(chatId, messageId(update)) :
        new RegisterUserDoesntOwnParkingSpaceEvent(chatId, messageId(update));
    broadcast(event);
  }

  private void validate(String yesOrNo) {
    if (!yesOrNo.equalsIgnoreCase("yes") && !yesOrNo.equalsIgnoreCase("no")) {
      throw new IllegalArgumentException("\"yes\" or \"no\" expected!");
    }
  }
}
