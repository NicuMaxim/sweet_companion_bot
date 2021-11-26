package com.sweet_companion_bot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineButtonService {

    LocaleMessageService localeMessageService;

    public InlineButtonService(LocaleMessageService localeMessageService) {
        this.localeMessageService = localeMessageService;
    }

    public InlineKeyboardMarkup getInlineMessageButtons(boolean isFirstInlineMessage) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        String n;
        if (isFirstInlineMessage) {
            n = "1";
        } else n = "2";

        InlineKeyboardButton button1 = new InlineKeyboardButton(localeMessageService.getMessage("inline_keyboard." + n + ".button.1"));
        InlineKeyboardButton button2 = new InlineKeyboardButton(localeMessageService.getMessage("inline_keyboard." + n + ".button.2"));
        InlineKeyboardButton button3 = new InlineKeyboardButton(localeMessageService.getMessage("inline_keyboard." + n + ".button.3"));

        button1.setCallbackData("inlineButton." + n + ".1");
        button2.setCallbackData("inlineButton." + n + ".2");
        button3.setCallbackData("inlineButton." + n + ".3");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(button1);
        keyboardButtonsRow1.add(button2);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(button3);

        if (isFirstInlineMessage) {
            InlineKeyboardButton button4 = new InlineKeyboardButton(localeMessageService.getMessage("inline_keyboard." + n + ".button.4"));
            button4.setCallbackData("inlineButton." + n + ".4");
            keyboardButtonsRow1.add(button4);
        }

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}