package com.sweet_companion_bot.botapi;

import com.sweet_companion_bot.botapi.handlers.MessageHandler;
import com.sweet_companion_bot.botapi.handlers.MenuHandler;
import com.sweet_companion_bot.service.LocaleMessageService;
import com.sweet_companion_bot.service.MainMenuService;
import com.sweet_companion_bot.service.PhotoMessageService;
import com.sweet_companion_bot.service.ReplyMessageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class TelegramFacade {
    private MainMenuService mainMenuService;
    private MessageHandler messageHandler;
    private MenuHandler menuHandler;
    private LocaleMessageService localeMessageService;
    private ReplyMessageService replyMessageService;
    private PhotoMessageService photoMessageService;


    public TelegramFacade(MainMenuService mainMenuService, MessageHandler messageHandler, MenuHandler menuHandler, LocaleMessageService localeMessageService, ReplyMessageService replyMessageService, PhotoMessageService photoMessageService) {
        this.mainMenuService = mainMenuService;
        this.messageHandler = messageHandler;
        this.menuHandler = menuHandler;
        this.localeMessageService = localeMessageService;
        this.replyMessageService = replyMessageService;
        this.photoMessageService = photoMessageService;
    }

    @SneakyThrows
    public BotApiMethod<?> handleUpdate(Update update) {
        BotApiMethod<?> replyMessage = null;
        Message message = update.getMessage();

        if (message != null) {
            replyMessageService.setLocaleLanguageIfAvailable(message);

            if (message.hasText()) {
                log.info("TelegramFacade --- handleUpdate(): New message from User: {}, userId: {}, chatId: {} with text: {}", message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
                replyMessage = handleInputMessage(message);
            } else {
                log.info("TelegramFacade --- handleUpdate(): No Text Error. New message from User: {}, userId: {}, chatId: {} without text.", message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId());
                replyMessage = mainMenuService.getMainMenuMessage(message.getFrom().getId().toString(), "reply.error");
            }
        }
        return replyMessage;
    }

    private BotApiMethod<?> handleInputMessage(Message message) {

        final String chatId = message.getFrom().getId().toString();
        String inputText = message.getText();
        String replyMessage;

        if (inputText.equals(localeMessageService.getMessage("menu.button.1"))
                || inputText.equals(localeMessageService.getMessage("menu.button.2"))
                || inputText.equals(localeMessageService.getMessage("menu.button.3"))) {
            replyMessage = menuHandler.getMenuReply(inputText);

        } else {
            replyMessage = messageHandler.getMessageReply(message);
        }

        if (replyMessage.contains("button.3.reply.")) {
            String errorMessage = photoMessageService.sendImage(chatId);
            if (!errorMessage.equals("")) {
                replyMessage = errorMessage;
            }
        }

        BotApiMethod<?> replyWithMenu = mainMenuService.getMainMenuMessage(chatId, replyMessage);

        log.info("TelegramFacade --- handleInputMessage(): Reply will be send to User: {}", replyWithMenu);
        return replyWithMenu;
    }
}
