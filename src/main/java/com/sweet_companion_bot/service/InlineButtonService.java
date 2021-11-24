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

    public InlineKeyboardMarkup getFirstInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton button1 = new InlineKeyboardButton(localeMessageService.getMessage("inline_keyboard.1.button.1"));
        InlineKeyboardButton button2 = new InlineKeyboardButton(localeMessageService.getMessage("inline_keyboard.1.button.2"));
        InlineKeyboardButton button3 = new InlineKeyboardButton(localeMessageService.getMessage("inline_keyboard.1.button.3"));
        InlineKeyboardButton button4 = new InlineKeyboardButton(localeMessageService.getMessage("inline_keyboard.1.button.4"));

        button1.setCallbackData("inlineButton.1.1");
        button2.setCallbackData("inlineButton.1.2");
        button3.setCallbackData("inlineButton.1.3");
        button4.setCallbackData("inlineButton.1.4");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(button1);
        keyboardButtonsRow1.add(button2);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(button3);
        keyboardButtonsRow1.add(button4);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getSecondInlineMessageButtons() { // this method is almost a copy of the one above
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton button1 = new InlineKeyboardButton(localeMessageService.getMessage("inline_keyboard.2.button.1"));
        InlineKeyboardButton button2 = new InlineKeyboardButton(localeMessageService.getMessage("inline_keyboard.2.button.2"));
        InlineKeyboardButton button3 = new InlineKeyboardButton(localeMessageService.getMessage("inline_keyboard.2.button.3"));

        button1.setCallbackData("inlineButton.2.1");
        button2.setCallbackData("inlineButton.2.2");
        button3.setCallbackData("inlineButton.2.3");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(button1);
        keyboardButtonsRow1.add(button2);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(button3);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}