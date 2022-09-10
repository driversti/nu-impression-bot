package com.github.driversti.nuimpressionbot.features.registration.apartment.number;

import static com.github.driversti.nuimpressionbot.common.Constants.REG_AP_CORRECTNESS_CONFIRMATION_PREFIX;

import com.github.driversti.nuimpressionbot.common.AbstractBroadcaster;
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
public class RegisterApartmentCorrectnessBroadcaster
    extends AbstractBroadcaster
    implements UpdateHandler {

  private final RegistrationProcessStorage processStorage;

  public RegisterApartmentCorrectnessBroadcaster(
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
    Optional<String> answerOpt = Optional.ofNullable(update.callbackQuery())
        .map(CallbackQuery::data)
        .filter(data -> data.startsWith(REG_AP_CORRECTNESS_CONFIRMATION_PREFIX))
        .map(prefixed -> prefixed.substring(REG_AP_CORRECTNESS_CONFIRMATION_PREFIX.length()));
    if (answerOpt.isEmpty()) {
      return;
    }
    String yesOrNo = answerOpt.get();
    validate(yesOrNo);
    boolean dataCorrect = yesOrNo.equalsIgnoreCase("yes");
    Event event = dataCorrect ?
        new RegisterApartmentDataCorrectEvent(chatId, messageId(update)) :
        new RegisterApartmentDataWrongEvent(chatId, messageId(update));
    broadcast(event);
  }

  private void validate(String yesOrNo) {
    if (!yesOrNo.equalsIgnoreCase("yes") && !yesOrNo.equalsIgnoreCase("no")) {
      throw new IllegalArgumentException("\"yes\" or \"no\" expected!");
    }
  }
}
