package com.github.driversti.nuimpressionbot.features.registration.parkingspace;

import static com.github.driversti.nuimpressionbot.common.Constants.REG_PARKING_SPACE_PREFIX;

import com.github.driversti.nuimpressionbot.features.registration.apartment.number.RegisterApartmentDataCorrectEvent;
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
public class RegisterParkingSpaceAsker {

  private final TelegramBot bot;

  @EventListener
  public void ask(RegisterApartmentDataCorrectEvent event) {
    String message = "Do you have a parking space?";
    bot.execute(new EditMessageText(event.chatId(), event.messageId(), message)
        .replyMarkup(hasOrNoParkingPlaceKeyboard()));
  }

  private InlineKeyboardMarkup hasOrNoParkingPlaceKeyboard() {
    return InlineKeyboardBuilder.builder()
        .row()
        .button("Yes", REG_PARKING_SPACE_PREFIX + "yes")
        .button("No", REG_PARKING_SPACE_PREFIX + "no")
        .endRow()
        .build();
  }
}
