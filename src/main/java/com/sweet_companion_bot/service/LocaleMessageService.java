package com.sweet_companion_bot.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Locale;

@Getter
@Setter
@Service
public class LocaleMessageService {
    private Locale locale;
    private final MessageSource messageSource;

    public LocaleMessageService(@Value("${localeTag}") String localeTag, MessageSource messageSource) {
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(localeTag);
    }

    public String getMessage(String message) {
        return messageSource.getMessage(message, null, locale);
    }
    public String getMessage(String message, Object... args) {
        return messageSource.getMessage(message, args, locale);
    }

    public void setLocaleLanguageIfAvailable(Message message) {

        String localeTag = message.getFrom().getLanguageCode();
        if (localeTag.equals("ru") || localeTag.equals("en")) {
            setLocale(Locale.forLanguageTag(localeTag));
        }
    }

}
