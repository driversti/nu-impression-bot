package com.github.driversti.nuimpressionbot.configs;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfiguration {

  @Bean
  TelegramBot telegramBot(@Value("${app.bot.token}") String botToken) {
    return new TelegramBot(botToken);
  }
}
