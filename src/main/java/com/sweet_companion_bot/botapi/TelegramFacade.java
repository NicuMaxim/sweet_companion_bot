package com.sweet_companion_bot.botapi;

import com.sweet_companion_bot.botapi.handlers.MessageHandler;
import com.sweet_companion_bot.botapi.handlers.MenuHandler;
import com.sweet_companion_bot.service.LocaleMessageService;
import com.sweet_companion_bot.service.MainMenuService;
import com.sweet_companion_bot.service.ReplyMessageService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class TelegramFacade {
    private MainMenuService mainMenuService;
    private MessageHandler messageHandler;
    private MenuHandler menuHandler;
    private LocaleMessageService localeMessageService;
    private ReplyMessageService replyMessageService;


    public TelegramFacade(MainMenuService mainMenuService, MessageHandler messageHandler, MenuHandler menuHandler, LocaleMessageService localeMessageService, ReplyMessageService replyMessageService) {
        this.mainMenuService = mainMenuService;
        this.messageHandler = messageHandler;
        this.menuHandler = menuHandler;
        this.localeMessageService = localeMessageService;
        this.replyMessageService = replyMessageService;
    }

    @SneakyThrows
    public BotApiMethod<?> handleUpdate(Update update) {
        BotApiMethod<?> replyMessage = null;
        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            System.out.println("New message from User: " + message.getFrom().getUserName() + ", userId: " + message.getFrom().getId() + ", chatId: " + message.getChatId() + " with text: " + message.getText());

            replyMessageService.setLocaleLanguageIfAvailable(message);
            replyMessage = handleInputMessage(message);
        }
        return replyMessage;
    }

    private BotApiMethod<?> handleInputMessage(Message message) {

        final String chatId = message.getFrom().getId().toString();
        String inputText = message.getText();
        String replyMessage;

        if (inputText.equals(localeMessageService.getMessage("menu.button.1"))
                || inputText.equals(localeMessageService.getMessage("menu.button.2"))) {
            replyMessage = menuHandler.getMenuReply(inputText);

        } else {
            replyMessage = messageHandler.getReplyMessage(message);
        }

        BotApiMethod<?> replyWithMenu = mainMenuService.getMainMenuMessage(chatId, replyMessage);

        return replyWithMenu;
    }
}
