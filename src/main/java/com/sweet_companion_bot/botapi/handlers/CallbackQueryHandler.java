//package com.sweet_companion_bot.botapi.handlers;
//
//import com.sweet_companion_bot.service.InlineButtonsService;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
//import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
//
//@Component
//public class CallbackQueryHandler {
//    private InlineButtonsService inlineButtonsService;
//
//    public CallbackQueryHandler(InlineButtonsService inlineButtonsService) {
//        this.inlineButtonsService = inlineButtonsService;
//    }
//
//    public BotApiMethod<?> handleCallbackQuery(CallbackQuery buttonQuery, String chatId) {
//
//        System.out.println(buttonQuery);
//        BotApiMethod<?> callBackAnswer = null;
//
//        if (buttonQuery.getData().equals("inlineButton1")) {
//            callBackAnswer = new SendMessage(chatId, "You pressed button_1. This is a simple answer.");
//        } else if (buttonQuery.getData().equals("inlineButton2")) {
//            callBackAnswer = sendAnswerCallbackQuery("You pressed button_2! No alert. Now you can press any button again.", false, buttonQuery);
//        } else if (buttonQuery.getData().equals("inlineButton3")) {
//            callBackAnswer = sendAnswerCallbackQuery("You pressed button_3! Alert! Now you can press any button again.", true, buttonQuery);
//        } else if (buttonQuery.getData().equals("inlineButton4")) {
//            SendMessage callBackAnswer1 = new SendMessage(chatId, "Answer again, please.");
//            callBackAnswer1.setReplyMarkup(inlineButtonsService.getInlineMessageButtons());
//            callBackAnswer =  callBackAnswer1;
//        }
//
//        return callBackAnswer;
//
//    }
//
//    private AnswerCallbackQuery sendAnswerCallbackQuery(String text, boolean alert, CallbackQuery callbackQuery) {
//
//        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
//        answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
//        answerCallbackQuery.setShowAlert(alert);
//        answerCallbackQuery.setText(text);
//
//        return answerCallbackQuery;
//    }
//
//
//}
