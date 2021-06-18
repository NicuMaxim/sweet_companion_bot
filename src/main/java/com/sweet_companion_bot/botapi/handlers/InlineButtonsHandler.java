package com.sweet_companion_bot.botapi.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineButtonsHandler {

    public InlineKeyboardMarkup getInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton button1 = new InlineKeyboardButton("inlineButtonName.1");
        InlineKeyboardButton button2 = new InlineKeyboardButton("inlineButtonName.2");
        InlineKeyboardButton button3 = new InlineKeyboardButton("inlineButtonName.3");
        InlineKeyboardButton button4 = new InlineKeyboardButton("inlineButtonName.4");

        button1.setCallbackData("inlineButton1");
        button2.setCallbackData("inlineButton2");
        button3.setCallbackData("inlineButton3");
        button4.setCallbackData("inlineButton4");

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


}
