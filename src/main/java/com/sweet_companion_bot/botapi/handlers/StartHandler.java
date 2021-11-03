package com.sweet_companion_bot.botapi.handlers;

import com.sweet_companion_bot.service.LocaleMessageService;
import com.sweet_companion_bot.service.MainMenuService;
import com.sweet_companion_bot.service.ReplyMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;


@Component
public class StartHandler {

    MainMenuService mainMenuService;
    ReplyMessageService replyMessageService;

    public StartHandler(MainMenuService mainMenuService, ReplyMessageService replyMessageService) {
        this.mainMenuService = mainMenuService;
        this.replyMessageService = replyMessageService;
    }

    public BotApiMethod<?> getStartReply(String chatId, Message message) {

//        String username = message.getFrom().getUserName();
 //       String replyMessage = "Hi, " + username + "! ";

//            BotApiMethod<?> callBackAnswer = mainMenuService.getMainMenuMessage(chatId, replyMessage);
        BotApiMethod<?> callBackAnswer = mainMenuService.getMainMenuMessage(chatId, "reply.menu");
        return callBackAnswer;
    }


}
