package com.github.driversti.nuimpressionbot.features.registration;

import static com.github.driversti.nuimpressionbot.common.Constants.REG_CONSENT;

import com.github.driversti.nuimpressionbot.common.AbstractBroadcaster;
import com.github.driversti.nuimpressionbot.common.UpdateHandler;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegistrationConsentReceivedBroadcaster
    extends AbstractBroadcaster
    implements UpdateHandler {

  private final RegistrationProcessStorage processStorage;

  public RegistrationConsentReceivedBroadcaster(
      ApplicationEventPublisher publisher, RegistrationProcessStorage processStorage) {
    super(publisher);
    this.processStorage = processStorage;
  }

  @Override
  public void handle(Update update) {
    boolean consent = Optional.ofNullable(update.callbackQuery())
        .map(CallbackQuery::data)
        .map(it -> it.equals(REG_CONSENT))
        .orElse(false);
    if (consent) {
      log.info("User gave a consent for registration");
      broadcast(new RegistrationConsentReceivedEvent(getChatId(update)));
    }
  }
}
