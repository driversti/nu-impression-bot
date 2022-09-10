package com.github.driversti.nuimpressionbot.features.start;

import static com.github.driversti.nuimpressionbot.common.Constants.REG_CONSENT;
import static com.github.driversti.nuimpressionbot.common.Constants.REG_DISSENT;

import com.github.driversti.nuimpressionbot.events.StartCommandReceivedEvent;
import com.github.driversti.nuimpressionbot.keyboard.InlineKeyboardBuilder;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartCommandListener {

  private final TelegramBot bot;

  @EventListener
  public void handleStart(StartCommandReceivedEvent event) {
    log.info("User pressed /start");
    Long chatId = event.update().message().chat().id();
    String username = Optional.ofNullable(event.update().message().chat().username())
        .orElse("user");
    bot.execute(new SendMessage(chatId, prepareMessage(username))
        .replyMarkup(registerKeyboard()));
  }

  private Keyboard registerKeyboard() {
    return InlineKeyboardBuilder.builder()
        .row()
        .button("Sure!", REG_CONSENT)
        .button("Nah", REG_DISSENT)
        .endRow()
        .build();
  }

  private String prepareMessage(String username) {
    String message = """
        Hi %s! I'm happy to see you here ☺
        I could be quite helpful to you.
        Let's see what you can do with my help:
        - chat directly with residents of a particular apartment
        - chat directly with owners of particular parking spaces
        - offer your apartment/parking space for rent
        - get notified whenever someone offers an apartment or parking place for rent
        - get notified whenever a building manager sends a mail
        - more to come...

        I strongly recommend to register your property in order to let others write to you directly whenever someone of our residents will need.
        E.g., you're flooding the neighbors when you're absent.
        Or your storage room might be open.
        Or someone wants to rent your parking space.
                
        Register?
        """;
    return String.format(message, username);
  }
}
