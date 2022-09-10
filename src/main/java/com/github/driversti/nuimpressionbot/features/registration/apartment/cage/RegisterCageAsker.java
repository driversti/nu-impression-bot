package com.github.driversti.nuimpressionbot.features.registration.apartment.cage;

import static com.github.driversti.nuimpressionbot.common.Constants.REG_CAGE_PREFIX;

import com.github.driversti.nuimpressionbot.building.Cage;
import com.github.driversti.nuimpressionbot.features.registration.RegistrationConsentReceivedEvent;
import com.github.driversti.nuimpressionbot.keyboard.InlineKeyboardBuilder;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterCageAsker {

  private final TelegramBot bot;

  @EventListener
  public void ask(RegistrationConsentReceivedEvent event) {
    String message = "In what cage is your apartment?";
    bot.execute(new SendMessage(event.chatId(), message)
        .replyMarkup(cageKeyboard()));
  }

  private Keyboard cageKeyboard() {
    return InlineKeyboardBuilder.builder()
        .row()
        .button("A", REG_CAGE_PREFIX + Cage.A.name())
        .button("B", REG_CAGE_PREFIX + Cage.B.name())
        .button("C", REG_CAGE_PREFIX + Cage.C.name())
        .button("D", REG_CAGE_PREFIX + Cage.D.name())
        .button("E", REG_CAGE_PREFIX + Cage.E.name())
        .button("F", REG_CAGE_PREFIX + Cage.F.name())
        .button("G", REG_CAGE_PREFIX + Cage.G.name())
        .endRow()
        .build();
  }
}
