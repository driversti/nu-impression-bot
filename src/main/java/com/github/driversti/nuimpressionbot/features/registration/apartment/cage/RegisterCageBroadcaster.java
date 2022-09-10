package com.github.driversti.nuimpressionbot.features.registration.apartment.cage;

import static com.github.driversti.nuimpressionbot.common.Constants.REG_CAGE_PREFIX;

import com.github.driversti.nuimpressionbot.building.Cage;
import com.github.driversti.nuimpressionbot.common.AbstractBroadcaster;
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
public class RegisterCageBroadcaster
    extends AbstractBroadcaster
    implements UpdateHandler {

  private final RegistrationProcessStorage processStorage;

  public RegisterCageBroadcaster(ApplicationEventPublisher publisher,
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
    if (!processStorage.expectsCage(chatId)) {
      return;
    }
    boolean isCagePresent = Optional.ofNullable(update.callbackQuery())
        .map(CallbackQuery::data)
        .stream()
        .anyMatch(it -> it.startsWith(REG_CAGE_PREFIX));
    if (isCagePresent) {
      prepareAndSendEvent(update);
    }
  }

  // TODO: the method not only prepares the event and sends it, but also takes care of parsing Cage enum
  private void prepareAndSendEvent(Update update) {
    Long chatId = update.callbackQuery().from().id();
    String cageLetter = update.callbackQuery().data().substring(REG_CAGE_PREFIX.length());
    try {
      Cage cage = Cage.valueOf(cageLetter);
      broadcast(new RegisterCageReceivedEvent(chatId, cage, messageId(update)));
      //processManager.addCage(chatId, cage);
    } catch (IllegalArgumentException e) {
      log.warn("The cage {} is not expected!", cageLetter, e);
      // TODO: notify the Developer?
    }
  }
}
