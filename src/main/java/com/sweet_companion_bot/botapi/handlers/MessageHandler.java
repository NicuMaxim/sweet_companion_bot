package com.sweet_companion_bot.botapi.handlers;

import com.sweet_companion_bot.service.MainMenuService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageHandler {

    MainMenuService mainMenuService;

    public MessageHandler(MainMenuService mainMenuService) {
        this.mainMenuService = mainMenuService;
    }

    public String getReplyMessage(Message message) {

        String textMessage = message.getText();
        String replyMessage;

        if (textMessage.equals("/start")) {
            replyMessage = "reply.start";
        } else {
            replyMessage = "reply.error";
        }


        return replyMessage;
    }
}
