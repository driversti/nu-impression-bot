package com.github.driversti.nuimpressionbot.features.registration.parkingspace.number;

import com.github.driversti.nuimpressionbot.common.Constants;
import com.github.driversti.nuimpressionbot.common.ParkingSpaceLocation;
import com.github.driversti.nuimpressionbot.common.ParkingSpaceNumbersHolder;
import com.github.driversti.nuimpressionbot.features.registration.parkingspace.location.RegisterParkingSpaceLocationEvent;
import com.github.driversti.nuimpressionbot.keyboard.InlineKeyboardBuilder;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.response.BaseResponse;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterParkingSpaceNumberAsker {

  private final TelegramBot bot;

  @EventListener
  public void ask(RegisterParkingSpaceLocationEvent event) {
    log.info("User's parking space location: {}", event.location());
    String message = "What is the number of your parking space?";
    BaseResponse response = bot.execute(
        new EditMessageText(event.chatId(), event.messageId(), message)
            .replyMarkup(parkingSpaceNumberKeyboard(event.location())));
    log.info("Response description: {}", response.description());
  }

  private InlineKeyboardMarkup parkingSpaceNumberKeyboard(ParkingSpaceLocation location) {
    InlineKeyboardBuilder builder = InlineKeyboardBuilder.builder().row();
    Collection<Integer> numbers = ParkingSpaceNumbersHolder.numbers(location);
    int numberInRow = 0;
    for (Integer number : numbers) {
      if (numberInRow == 7) {
        builder.endRow().row()
            .button(number, Constants.REG_PS_NUMBER_PREFIX + number);
        numberInRow = 1;
      } else {
        builder.button(number, Constants.REG_PS_NUMBER_PREFIX + number);
        numberInRow++;
      }
    }
    return builder
        .endRow()
        .build();
  }
}
