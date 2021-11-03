//package com.sweet_companion_bot.service;
//
//import lombok.SneakyThrows;
//import org.apache.commons.io.FileUtils;
//import org.json.JSONObject;
//import org.springframework.stereotype.Service;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//import java.io.*;
//import java.net.URL;
//
//@Service
//public class UploadFileService {
//
//    // сделать отдельный handler - к нему обращается фасад. а уже handler обращается к сервису
//
//
//    @SneakyThrows
//    public SendMessage uploadFileAndReply(Update update) {
//
//        String fileName = update.getMessage().getDocument().getFileName();
//        String fileId = update.getMessage().getDocument().getFileId();
//        String chatId = update.getMessage().getChatId().toString();
//        String token = "1811197227:AAHqi2RhocL33aHrA7GISeH3F2zdA84vlFQ";  // надо не хардкодить его, а красиво брать
//
//        downloadUploadedFile(fileName, fileId, token);
//
//        String testText = "File name: " + fileName + " and file Id: " + fileId + " uploaded successfully!";
//        System.out.println(testText);
//        SendMessage replyAfterUploadFile = new SendMessage(chatId, testText);
//
//        return replyAfterUploadFile;
//    }
//
//
//    @SneakyThrows
//    private void downloadUploadedFile(String fileName, String fileId, String token) {
//
//        URL url = new URL ("https://api.telegram.org/bot" + token +"/getFile?file_id=" + fileId);
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
//        String getFileResponse = br.readLine();
//        System.out.println(getFileResponse);
//
//        JSONObject jsonResult = new JSONObject(getFileResponse);
//        String filePath = jsonResult.getJSONObject("result").getString("file_path");
//
//        File localFile = new File("src/main/resources/uploaded_files/" + fileName);
//        InputStream inputStream = new URL("https://api.telegram.org/file/bot" + token + "/" + filePath).openStream();
//
//        FileUtils.copyInputStreamToFile(inputStream, localFile);
//
//        br.close();
//        inputStream.close();
//    }
//}
