package com.sweet_companion_bot.botapi.handlers;

import com.sweet_companion_bot.service.MainMenuService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;


@Component
public class StartHandler {

    MainMenuService mainMenuService;

    public StartHandler(MainMenuService mainMenuService) {
        this.mainMenuService = mainMenuService;
    }

    public BotApiMethod<?> getStartReply(String chatId) {
        BotApiMethod<?> callBackAnswer = mainMenuService.getMainMenuMessage(chatId, "reply.menu");
        return callBackAnswer;
    }


}
