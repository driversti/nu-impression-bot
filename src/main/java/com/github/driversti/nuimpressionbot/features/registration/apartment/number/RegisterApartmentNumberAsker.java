package com.github.driversti.nuimpressionbot.features.registration.apartment.number;

import com.github.driversti.nuimpressionbot.building.Cage;
import com.github.driversti.nuimpressionbot.common.BuildingModel;
import com.github.driversti.nuimpressionbot.common.Constants;
import com.github.driversti.nuimpressionbot.common.Floor;
import com.github.driversti.nuimpressionbot.features.registration.RegistrationProcessStorage;
import com.github.driversti.nuimpressionbot.features.registration.apartment.floor.RegisterFloorReceivedEvent;
import com.github.driversti.nuimpressionbot.keyboard.InlineKeyboardBuilder;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.EditMessageText;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterApartmentNumberAsker {

  private final TelegramBot bot;
  private final RegistrationProcessStorage processManager;

  @EventListener
  public void ask(RegisterFloorReceivedEvent event) {
    String message = "What is your apartment number?";
    Long chatId = event.chatId();
    bot.execute(new EditMessageText(chatId, event.messageId(), message)
        .replyMarkup(apartmentKeyboard(chatId, event.floor())));
  }

  private InlineKeyboardMarkup apartmentKeyboard(Long chatId, Floor floor) {
    Cage cage = processManager.cageFor(chatId);
    Collection<Integer> apartments = BuildingModel.apartments(cage, floor);
    InlineKeyboardBuilder row = InlineKeyboardBuilder.builder().row();
    for (Integer apartment : apartments) {
      row.button(apartment, Constants.REG_AP_PREFIX + apartment);
    }
    return row.endRow().build();
  }
}
