package com.github.driversti.nuimpressionbot.features.start;

import static com.github.driversti.nuimpressionbot.common.Constants.START_COMMAND;

import com.github.driversti.nuimpressionbot.common.AbstractBroadcaster;
import com.github.driversti.nuimpressionbot.common.UpdateHandler;
import com.github.driversti.nuimpressionbot.events.StartCommandReceivedEvent;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import java.util.Optional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class StartCommandBroadcaster
    extends AbstractBroadcaster
    implements UpdateHandler {

  public StartCommandBroadcaster(ApplicationEventPublisher publisher) {
    super(publisher);
  }

  @Override
  public void handle(Update update) {
    boolean isStartCommand = Optional.ofNullable(update.message())
        .map(Message::text)
        .map(it -> it.equals(START_COMMAND))
        .orElse(false);
    if (isStartCommand) {
      broadcast(new StartCommandReceivedEvent(update));
    }
  }
}
