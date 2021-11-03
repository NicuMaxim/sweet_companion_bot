package com.sweet_companion_bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Locale;

@Service
public class ReplyMessageService {

    private LocaleMessageService localeMessageService;

    public ReplyMessageService(LocaleMessageService localeMessageService) {
        this.localeMessageService = localeMessageService;
    }

    public SendMessage getReplyMessage(String chatId, Message message) {

        setLocaleLanguageIfAvailable(message);
//        String username = message.getFrom().getUserName();
//        String text = message.getText();

        SendMessage replyMessage = new SendMessage(chatId, localeMessageService.getMessage("reply.menu"));
        return replyMessage;
    }

    public SendMessage getReplyMessage(String chatId, String replyMessage, Object... args) {
        return new SendMessage(chatId, localeMessageService.getMessage(replyMessage, args));
    }

    public void setLocaleLanguageIfAvailable(Message message) {

        String localeTag = message.getFrom().getLanguageCode();

        if (localeTag.equals("ru") || localeTag.equals("en")) {
            localeMessageService.setLocale(Locale.forLanguageTag(localeTag));
        }
    }
}
