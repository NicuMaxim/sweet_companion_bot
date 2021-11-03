package com.sweet_companion_bot.botapi.handlers;

//import com.sweet_companion_bot.service.InlineButtonsService;
import com.sweet_companion_bot.service.LocaleMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MenuHandler {

//    InlineButtonsService inlineButtonsService;
    LocaleMessageService localeMessageService;

    public MenuHandler(LocaleMessageService localeMessageService) {
        this.localeMessageService = localeMessageService;
    }

    public BotApiMethod<?> getMenuReply(String chatId, String messageText) {

        String reply;
        String a = localeMessageService.getMessage("menu.button.1");
        String b = localeMessageService.getMessage("menu.button.2");

        if (messageText.equals(a)) {
            reply = localeMessageService.getMessage("reply.1");
        } else if (messageText.equals(localeMessageService.getMessage("menu.button.2"))) {
            reply = localeMessageService.getMessage("reply.2");;
        } else reply = localeMessageService.getMessage("reply.3");;



//        boolean inlineButton = false;

//        switch (messageText) {
//            case a:
//                reply = "reply.1";
////                inlineButton = true;
//                break;
//            case "Отличный день, чтобы ...":
//                reply = "reply.4";
//                break;
////            case "Wide Button 3":
////                reply = "You pressed Wide Button 3";
////                break;
//        }

        SendMessage menuReply = new SendMessage(chatId, reply);

//        if (inlineButton) {
//            menuReply.setReplyMarkup(inlineButtonsService.getInlineMessageButtons());
//        }

        return menuReply;
    }

}
