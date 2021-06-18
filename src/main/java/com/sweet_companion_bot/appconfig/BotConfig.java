package com.sweet_companion_bot.appconfig;

import com.sweet_companion_bot.TelegramBot;
import com.sweet_companion_bot.botapi.TelegramFacade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;


@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String webHookPath;
    private String botUserName;
    private String botToken;

    @Bean
    public TelegramBot myTelegramBot(TelegramFacade telegramFacade) {

        TelegramBot myTelegramBot = new TelegramBot(telegramFacade);
        myTelegramBot.setWebHookPath(webHookPath);
        myTelegramBot.setBotToken(botToken);
        myTelegramBot.setBotUserName(botUserName);

        return myTelegramBot;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
