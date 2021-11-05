package com.sweet_companion_bot.botapi.handlers;

import com.sweet_companion_bot.service.LocaleMessageService;
import com.sweet_companion_bot.service.MainMenuService;
import com.sweet_companion_bot.service.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.lang.Integer.parseInt;

@Slf4j
@Component
public class MenuHandler {

    LocaleMessageService localeMessageService;
    MainMenuService mainMenuService;

    public MenuHandler(LocaleMessageService localeMessageService, MainMenuService mainMenuService) {
        this.localeMessageService = localeMessageService;
        this.mainMenuService = mainMenuService;
    }

    public String getMenuReply(String inputText) {

        String reply;
        int randomReplyNumberForButton1 = Util.getRandomInt(1, parseInt(localeMessageService.getMessage("button.1.reply.total")));
        int randomReplyNumberForButton2 = Util.getRandomInt(1, parseInt(localeMessageService.getMessage("button.2.reply.total")));
        int randomReplyNumberForButton3 = Util.getRandomInt(1, parseInt(localeMessageService.getMessage("button.3.reply.total")));

        log.info("MenuHandler --- getMenuReply(): Random reply numbers for button1: {}, button2: {}, button3: {}", randomReplyNumberForButton1, randomReplyNumberForButton2, randomReplyNumberForButton3);

        if (inputText.equals(localeMessageService.getMessage("menu.button.1"))) {
            reply = "button.1.reply." + randomReplyNumberForButton1;
        } else if (inputText.equals(localeMessageService.getMessage("menu.button.2"))) {
            reply = "button.2.reply." + randomReplyNumberForButton2;
        } else if (inputText.equals(localeMessageService.getMessage("menu.button.3"))) {
            reply = "button.3.reply." + randomReplyNumberForButton3;
        } else reply = "reply.error";

        return reply;
    }
}
