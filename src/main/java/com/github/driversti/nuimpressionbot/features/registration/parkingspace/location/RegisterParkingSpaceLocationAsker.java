package com.github.driversti.nuimpressionbot.features.registration.parkingspace.location;

import static com.github.driversti.nuimpressionbot.common.Constants.REG_PS_LOCATION_PREFIX;
import static com.github.driversti.nuimpressionbot.common.ParkingSpaceLocation.OUTSIDE;
import static com.github.driversti.nuimpressionbot.common.ParkingSpaceLocation.UNDERGROUND;

import com.github.driversti.nuimpressionbot.features.registration.parkingspace.RegisterUserOwnsParkingSpaceEvent;
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
public class RegisterParkingSpaceLocationAsker {

  private final TelegramBot bot;

  @EventListener
  public void ask(RegisterUserOwnsParkingSpaceEvent event) {
    log.info("User {} owns a parking space", event.chatId());
    String message = "Where does you parking space is located?";
    bot.execute(new EditMessageText(event.chatId(), event.messageId(), message)
        .replyMarkup(parkingSpaceLocationKeyboard()));
  }

  private InlineKeyboardMarkup parkingSpaceLocationKeyboard() {
    return InlineKeyboardBuilder.builder()
        .row()
        .button("Outside", REG_PS_LOCATION_PREFIX + OUTSIDE)
        .button("Underground", REG_PS_LOCATION_PREFIX + UNDERGROUND)
        .endRow()
        .build();
  }
}
