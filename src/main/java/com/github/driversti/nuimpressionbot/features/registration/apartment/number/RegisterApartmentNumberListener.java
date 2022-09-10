package com.github.driversti.nuimpressionbot.features.registration.apartment.number;

import static java.lang.String.format;

import com.github.driversti.nuimpressionbot.common.Constants;
import com.github.driversti.nuimpressionbot.features.registration.RegistrationProcessStorage;
import com.github.driversti.nuimpressionbot.keyboard.InlineKeyboardBuilder;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.EditMessageText;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
// TODO: find a better name
public class RegisterApartmentNumberListener {

  private final TelegramBot bot;
  private final RegistrationProcessStorage processStorage;

  @EventListener
  public void process(RegisterApartmentNumberReceivedEvent event) {
    log.info("User chose apartment {}", event.apartment());
    Long chatId = event.chatId();
    String message = format("So, you live in the cage %s, on floor %s, in apartment %s. Is this correct?",
        processStorage.cageFor(chatId), processStorage.floorFor(chatId).asNumber(),
        processStorage.apartmentFor(chatId));
    bot.execute(new EditMessageText(chatId, event.messageId(), message)
        .replyMarkup(apartmentCorrectnessConfirmationKeyboard()));
  }

  private InlineKeyboardMarkup apartmentCorrectnessConfirmationKeyboard() {
    return InlineKeyboardBuilder.builder()
        .row()
        .button("Yes", Constants.REG_AP_CORRECTNESS_CONFIRMATION_PREFIX + "yes")
        .button("Edit", Constants.REG_AP_CORRECTNESS_CONFIRMATION_PREFIX + "no")
        .endRow()
        .build();
  }
}
