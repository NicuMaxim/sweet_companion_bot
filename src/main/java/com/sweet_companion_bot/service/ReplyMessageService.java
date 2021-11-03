package com.sweet_companion_bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Locale;

@Service
public class ReplyMessageService {

    private LocaleMessageService localeMessageService;

    public ReplyMessageService(LocaleMessageService localeMessageService) {
        this.localeMessageService = localeMessageService;
    }

    public void setLocaleLanguageIfAvailable(Message message) {

        String localeTag = message.getFrom().getLanguageCode();

        if (localeTag.equals("ru") || localeTag.equals("en")) {
            localeMessageService.setLocale(Locale.forLanguageTag(localeTag));
        }
    }
}
