package com.sweet_companion_bot.botapi.handlers;

import com.sangupta.jerry.util.StringUtils;
import com.sweet_companion_bot.service.InlineButtonService;
import com.sweet_companion_bot.service.LocaleMessageService;
import com.sweet_companion_bot.service.PhotoMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class CallbackQueryHandler {

    private InlineButtonService inlineButtonsService;
    private LocaleMessageService localeMessageService;
    private PhotoMessageService photoMessageService;

    public CallbackQueryHandler(InlineButtonService inlineButtonsService, LocaleMessageService localeMessageService, PhotoMessageService photoMessageService) {
        this.inlineButtonsService = inlineButtonsService;
        this.localeMessageService = localeMessageService;
        this.photoMessageService = photoMessageService;
    }

    public BotApiMethod<?> handleCallbackQuery(CallbackQuery buttonQuery) {

        final String chatId = buttonQuery.getMessage().getChatId().toString();
        SendMessage callBackAnswer = null;

        if (buttonQuery.getMessage().getText().equals(localeMessageService.getMessage("button.4.reply.1"))) {
            callBackAnswer = handleFirstDialog(buttonQuery, chatId);

        } else if (buttonQuery.getData().contains("inlineButton.2.")) {
            callBackAnswer = handleSecondDialog(buttonQuery, chatId);
        }
        return callBackAnswer;
    }


    private SendMessage handleFirstDialog(CallbackQuery buttonQuery, String chatId) {

        String category = "";

        if (buttonQuery.getData().equals("inlineButton.1.1")) {
            category = localeMessageService.getMessage("inline_keyboard.1.button.1");
        } else if (buttonQuery.getData().equals("inlineButton.1.2")) {
            category = localeMessageService.getMessage("inline_keyboard.1.button.2");
        } else if (buttonQuery.getData().equals("inlineButton.1.3")) {
            category = localeMessageService.getMessage("inline_keyboard.1.button.3");
        } else if (buttonQuery.getData().equals("inlineButton.1.4")) {
            category = localeMessageService.getMessage("inline_keyboard.1.button.4");
        }

        String errorMessage = photoMessageService.sendImage(chatId, category);
        SendMessage callBackAnswer = prepareCallbackAnswer(errorMessage, chatId, category);

        return callBackAnswer;
    }

    private SendMessage handleSecondDialog(CallbackQuery buttonQuery, String chatId) {

        SendMessage callBackAnswer;

        if (buttonQuery.getData().equals("inlineButton.2.1")) {
            // More
            String chosenCategory = StringUtils.substringBetween(buttonQuery.getMessage().getText(), ": ", ".");
            String errorMessage = photoMessageService.sendImage(chatId, chosenCategory);
            callBackAnswer = prepareCallbackAnswer(errorMessage, chatId, chosenCategory);

        } else if (buttonQuery.getData().equals("inlineButton.2.2")) {
            // Another category
            callBackAnswer = new SendMessage(chatId, localeMessageService.getMessage("button.4.reply.1"));
            callBackAnswer.setReplyMarkup(inlineButtonsService.getFirstInlineMessageButtons());

        } else if (buttonQuery.getData().equals("inlineButton.2.3")) {
            // No, thanks
            callBackAnswer = new SendMessage(chatId, localeMessageService.getMessage("inline_keyboard.dialog.2.1"));
        } else {
            callBackAnswer = new SendMessage(chatId, localeMessageService.getMessage("reply.exception.4"));
        }
        return callBackAnswer;
    }

    private SendMessage prepareCallbackAnswer(String errorMessage, String chatId, String category) {

        SendMessage callBackAnswer;
        String replyText;

        if (!errorMessage.equals("")) {
            replyText = localeMessageService.getMessage(errorMessage);
            callBackAnswer = new SendMessage(chatId, replyText);
        } else {
            replyText = localeMessageService.getMessage("inline_keyboard.dialog.1.part.1") + category + localeMessageService.getMessage("inline_keyboard.dialog.1.part.2");
            callBackAnswer = new SendMessage(chatId, replyText);
            callBackAnswer.setReplyMarkup(inlineButtonsService.getSecondInlineMessageButtons());
        }
        return callBackAnswer;
    }
}
