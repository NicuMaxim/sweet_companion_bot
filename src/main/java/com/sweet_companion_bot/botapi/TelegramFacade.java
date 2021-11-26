package com.sweet_companion_bot.botapi;

import com.sweet_companion_bot.botapi.handlers.CallbackQueryHandler;
import com.sweet_companion_bot.botapi.handlers.MessageHandler;
import com.sweet_companion_bot.botapi.handlers.MenuHandler;
import com.sweet_companion_bot.service.InlineButtonService;
import com.sweet_companion_bot.service.LocaleMessageService;
import com.sweet_companion_bot.service.MainMenuService;
import com.sweet_companion_bot.service.PhotoMessageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Slf4j
@Component
public class TelegramFacade {
    private MainMenuService mainMenuService;
    private MessageHandler messageHandler;
    private MenuHandler menuHandler;
    private LocaleMessageService localeMessageService;
    private PhotoMessageService photoMessageService;
    private CallbackQueryHandler callbackQueryHandler;
    private InlineButtonService inlineButtonService;


    public TelegramFacade(MainMenuService mainMenuService, MessageHandler messageHandler, MenuHandler menuHandler, LocaleMessageService localeMessageService, PhotoMessageService photoMessageService, CallbackQueryHandler callbackQueryHandler, InlineButtonService inlineButtonService) {
        this.mainMenuService = mainMenuService;
        this.messageHandler = messageHandler;
        this.menuHandler = menuHandler;
        this.localeMessageService = localeMessageService;
        this.photoMessageService = photoMessageService;
        this.callbackQueryHandler = callbackQueryHandler;
        this.inlineButtonService = inlineButtonService;
    }

    @SneakyThrows
    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage = null;

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("TelegramFacade --- handleUpdate(): New message with Callback Query from User: {}, userId: {}, chatId: {} with text: {}, with data: {}", callbackQuery.getFrom().getUserName(), callbackQuery.getFrom().getId(), callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getText(), callbackQuery.getData());
            return handleInputCallbackQuery(callbackQuery);
        }

        Message message = update.getMessage();

        if (message != null) {
            localeMessageService.setLocaleLanguageIfAvailable(message);

            if (message.hasText()) {
                log.info("TelegramFacade --- handleUpdate(): New message from User: {}, userId: {}, chatId: {} with text: {}", message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
                replyMessage = handleInputMessage(message);
            } else {
                log.info("TelegramFacade --- handleUpdate(): No Text Error. New message from User: {}, userId: {}, chatId: {} without text.", message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId());
                replyMessage = mainMenuService.getMainMenuMessage(message.getFrom().getId().toString());
                replyMessage.setText("reply.error");
            }
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {

        final String chatId = message.getFrom().getId().toString();
        String inputText = message.getText();
        String replyMessage;

        // пытаюсь избежать ошибки при смене языка резкой, но без того чтобы setLocale делать, не могу получить оба варианта сообщения.
//        String test1 = localeMessageService.getMessage("menu.button.1", Locale.ENGLISH);
//        localeMessageService.setLocale(Locale.forLanguageTag("ru"));
//        String test2 = localeMessageService.getMessage("menu.button.1");
//        System.out.println("en : " + test1 + ". ru : "+ test2);

        if (inputText.equals(localeMessageService.getMessage("menu.button.1"))
                || inputText.equals(localeMessageService.getMessage("menu.button.2"))
                || inputText.equals(localeMessageService.getMessage("menu.button.3"))
                || inputText.equals(localeMessageService.getMessage("menu.button.4"))
                || inputText.equals(localeMessageService.getMessage("menu.button.5"))) {
            replyMessage = menuHandler.getMenuReply(inputText);

        } else {
            replyMessage = messageHandler.getMessageReply(inputText);
        }

        // Random image
        if (replyMessage.contains("button.3.reply.")) {
            String errorMessage = photoMessageService.sendImage(chatId, "");
            if (!errorMessage.equals("")) {
                replyMessage = errorMessage;
            }
        }

        SendMessage replyWithMenu = mainMenuService.getMainMenuMessage(chatId);
        try {
            replyWithMenu.setText(localeMessageService.getMessage(replyMessage));
        } catch (NoSuchMessageException e) {
            e.printStackTrace();
            replyWithMenu.setText(localeMessageService.getMessage("reply.exception.3"));
        }

        // Choose Category
        if (replyWithMenu.getText().equals(localeMessageService.getMessage("button.4.reply.1"))) {
            replyWithMenu.setReplyMarkup(inlineButtonService.getInlineMessageButtons(true));
        }

        //log.info("TelegramFacade --- handleInputMessage(): Reply will be send to User: {}", replyWithMenu);
        return replyWithMenu;
    }

    private BotApiMethod<?> handleInputCallbackQuery(CallbackQuery buttonQuery) {
        BotApiMethod<?> callBackAnswer = callbackQueryHandler.handleCallbackQuery(buttonQuery);

        if (callBackAnswer == null) {
            SendMessage errorMessage = new SendMessage();
            errorMessage.setText(localeMessageService.getMessage("reply.exception.4"));
            return errorMessage;
        }

        return callBackAnswer;
    }
}
