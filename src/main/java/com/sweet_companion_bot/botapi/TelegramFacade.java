package com.sweet_companion_bot.botapi;

import com.sweet_companion_bot.TelegramBot;
import com.sweet_companion_bot.botapi.handlers.CallbackQueryHandler;
import com.sweet_companion_bot.service.InlineButtonsService;
import com.sweet_companion_bot.botapi.handlers.StartHandler;
import com.sweet_companion_bot.botapi.handlers.MenuHandler;
import com.sweet_companion_bot.service.ReplyMessageService;
import com.sweet_companion_bot.service.UploadFileService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class TelegramFacade {
    private ReplyMessageService replyMessageService; // было бы хорошо убрать и сделать handler
    private StartHandler startHandler;
    private MenuHandler menuHandler;
    private CallbackQueryHandler callbackQueryHandler;
    private UploadFileService uploadFileService; // было бы хорошо убрать и сделать handler

    public TelegramFacade(ReplyMessageService replyMessageService, StartHandler startHandler, MenuHandler menuHandler, CallbackQueryHandler callbackQueryHandler, UploadFileService uploadFileService) {
        this.replyMessageService = replyMessageService;
        this.startHandler = startHandler;
        this.menuHandler = menuHandler;
        this.callbackQueryHandler = callbackQueryHandler;
        this.uploadFileService = uploadFileService;
    }

    @SneakyThrows
    public BotApiMethod<?> handleUpdate(Update update) {
        BotApiMethod<?> replyMessage = null;

        Message message = update.getMessage();

        if (message != null) {
            Document document = update.getMessage().getDocument();
            if (document != null) {
                SendMessage replyAfterUploadFile = uploadFileService.uploadFileAndReply(update);
                return replyAfterUploadFile;
            }
        }

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            System.out.println("New callbackQuery from User: " + update.getCallbackQuery().getFrom().getUserName() + ", userId: " + callbackQuery.getFrom().getId() + ", with data: " +  update.getCallbackQuery().getData());
            return processCallbackQuery(callbackQuery);
        }

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
        BotApiMethod<?> callBackAnswer = callbackQueryHandler.handleCallbackQuery(buttonQuery, chatId);

        return callBackAnswer;
    }

}
