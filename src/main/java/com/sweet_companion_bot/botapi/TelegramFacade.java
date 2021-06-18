package com.sweet_companion_bot.botapi;

import com.sweet_companion_bot.botapi.handlers.InlineButtonsHandler;
import com.sweet_companion_bot.botapi.handlers.StartHandler;
import com.sweet_companion_bot.botapi.handlers.MenuHandler;
import com.sweet_companion_bot.service.MainMenuService;
import com.sweet_companion_bot.service.ReplyMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramFacade {
    private ReplyMessageService replyMessageService;
    private StartHandler startHandler;
    private MenuHandler menuHandler;
    private InlineButtonsHandler inlineButtonsHandler;

    public TelegramFacade(ReplyMessageService replyMessageService, StartHandler startHandler, MenuHandler menuHandler, InlineButtonsHandler inlineButtonsHandler) {
        this.replyMessageService = replyMessageService;
        this.startHandler = startHandler;
        this.menuHandler = menuHandler;
        this.inlineButtonsHandler = inlineButtonsHandler;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        BotApiMethod<?> replyMessage = null;

        if (update.hasMessage())
        System.out.println(update.getMessage().getFrom().getLanguageCode());


        if(update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            System.out.println("New callbackQuery from User: " + update.getCallbackQuery().getFrom().getUserName() + ", userId: " + callbackQuery.getFrom().getId() + ", with data: " +  update.getCallbackQuery().getData());
            return processCallbackQuery(callbackQuery);
        }

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            System.out.println("New message from User: " + message.getFrom().getUserName() + ", userId: " + message.getFrom().getId() + ", chatId: " + message.getChatId() + " with text: " + message.getText());
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }


    private BotApiMethod<?> handleInputMessage(Message message) {

        final String chatId = message.getFrom().getId().toString();
        String inputMsg = message.getText();
        BotApiMethod<?> replyMessage;

        switch (inputMsg) {
            case "/start":
                replyMessage = startHandler.getStartReply(chatId);
                break;
            case "Ask me a question":
            case "Button 2":
            case "Wide Button 3":
                replyMessage = menuHandler.getMenuReply(chatId, message);
                break;
            default:
                replyMessage = replyMessageService.getReplyMessage(chatId, message);
                break;
        }




        return replyMessage;
    }


    private BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final String chatId = buttonQuery.getMessage().getChatId().toString();

        System.out.println(buttonQuery);

        BotApiMethod<?> callBackAnswer = null;

        if (buttonQuery.getData().equals("inlineButton1")) {
            callBackAnswer = new SendMessage(chatId, "You pressed button_1. This is a simple answer.");
        } else if (buttonQuery.getData().equals("inlineButton2")) {
            callBackAnswer = sendAnswerCallbackQuery("You pressed button_2! No alert. Now you can press any button again.", false, buttonQuery);
        } else if (buttonQuery.getData().equals("inlineButton3")) {
            callBackAnswer = sendAnswerCallbackQuery("You pressed button_3! Alert! Now you can press any button again.", true, buttonQuery);
        } else if (buttonQuery.getData().equals("inlineButton4")) {
            SendMessage callBackAnswer1 = new SendMessage(chatId, "Answer again, please.");
            callBackAnswer1.setReplyMarkup(inlineButtonsHandler.getInlineMessageButtons());
            callBackAnswer =  callBackAnswer1;
        }

        return callBackAnswer;
    }


    private AnswerCallbackQuery sendAnswerCallbackQuery(String text, boolean alert, CallbackQuery callbackQuery) {

        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
        answerCallbackQuery.setShowAlert(alert);
        answerCallbackQuery.setText(text);

        return answerCallbackQuery;
    }
}
