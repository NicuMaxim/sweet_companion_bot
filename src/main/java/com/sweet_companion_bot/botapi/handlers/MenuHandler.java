package com.sweet_companion_bot.botapi.handlers;

import com.sweet_companion_bot.service.LocaleMessageService;
import com.sweet_companion_bot.service.MainMenuService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class MenuHandler {

    LocaleMessageService localeMessageService;
    MainMenuService mainMenuService;

    public MenuHandler(LocaleMessageService localeMessageService, MainMenuService mainMenuService) {
        this.localeMessageService = localeMessageService;
        this.mainMenuService = mainMenuService;
    }

    public String getMenuReply(String inputText) {

        String reply;

        if (inputText.equals(localeMessageService.getMessage("menu.button.1"))) {
            reply = "reply.1";
        } else if (inputText.equals(localeMessageService.getMessage("menu.button.2"))) {
            reply = "reply.4";
        } else reply = "reply.error";

        return reply;
    }
}
