package com.sweet_companion_bot;

import com.sweet_companion_bot.botapi.TelegramFacade;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class TelegramBot extends TelegramWebhookBot {

    private String webHookPath;
    private String botUserName;
    private String botToken;

    private TelegramFacade telegramFacade;

    public TelegramBot (TelegramFacade telegramFacade) {
        this.telegramFacade = telegramFacade;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
    @Override
    public String getBotUsername() {
        return botUserName;
    }
    @Override
    public String getBotPath() {
        return webHookPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        final BotApiMethod<?> replyMessageToUser = telegramFacade.handleUpdate(update);
        log.info("TelegramBot --- onWebhookUpdateReceived() : Reply will be send to User: {}", replyMessageToUser);
        return replyMessageToUser;
    }


    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }
    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }
    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
}
