package com.github.driversti.nuimpressionbot.features.registration.apartment.floor;

import static com.github.driversti.nuimpressionbot.common.Constants.REG_FLOOR_PREFIX;
import static com.github.driversti.nuimpressionbot.common.Floor.FIFTH;
import static com.github.driversti.nuimpressionbot.common.Floor.FIRST;
import static com.github.driversti.nuimpressionbot.common.Floor.FOURTH;
import static com.github.driversti.nuimpressionbot.common.Floor.GROUND;
import static com.github.driversti.nuimpressionbot.common.Floor.SECOND;
import static com.github.driversti.nuimpressionbot.common.Floor.SEVENTH;
import static com.github.driversti.nuimpressionbot.common.Floor.SIXTH;
import static com.github.driversti.nuimpressionbot.common.Floor.THIRD;

import com.github.driversti.nuimpressionbot.features.registration.apartment.cage.RegisterCageReceivedEvent;
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
public class RegisterFloorAsker {

  private final TelegramBot bot;

  @EventListener
  public void ask(RegisterCageReceivedEvent event) {
    String message = "On what floor is your apartment?";
    bot.execute(new EditMessageText(event.chatId(), event.messageId(), message)
        .replyMarkup(floorKeyboard()));
  }

  private InlineKeyboardMarkup floorKeyboard() {
    return InlineKeyboardBuilder.builder()
        .row()
        .button("1", REG_FLOOR_PREFIX + FIRST.asNumber())
        .button("2", REG_FLOOR_PREFIX + SECOND.asNumber())
        .button("3", REG_FLOOR_PREFIX + THIRD.asNumber())
        .button("4", REG_FLOOR_PREFIX + FOURTH.asNumber())
        .button("5", REG_FLOOR_PREFIX + FIFTH.asNumber())
        .button("6", REG_FLOOR_PREFIX + SIXTH.asNumber())
        .button("7", REG_FLOOR_PREFIX + SEVENTH.asNumber())
        .endRow().row()
        .button("Ground", REG_FLOOR_PREFIX + GROUND.asNumber())
        .endRow()
        .build();
  }
}
