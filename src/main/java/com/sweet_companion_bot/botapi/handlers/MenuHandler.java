package com.sweet_companion_bot.botapi.handlers;

import com.sweet_companion_bot.service.InlineButtonsService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MenuHandler {

    InlineButtonsService inlineButtonsService;

    public MenuHandler(InlineButtonsService inlineButtonsService) {
        this.inlineButtonsService = inlineButtonsService;
    }

    public BotApiMethod<?> getMenuReply(String chatId, Message message) {

        String messageText = message.getText();
        String reply = "";
        boolean inlineButton = false;

        switch (messageText) {
            case "Ask me a question":
                reply = "Here is your question:";
                inlineButton = true;
                break;
            case "Button 2":
                reply = "You pressed Button 2";
                break;
            case "Wide Button 3":
                reply = "You pressed Wide Button 3";
                break;
        }
        SendMessage menuReply = new SendMessage(chatId, reply);

        if (inlineButton) {
            menuReply.setReplyMarkup(inlineButtonsService.getInlineMessageButtons());
        }

        return menuReply;
    }

}
