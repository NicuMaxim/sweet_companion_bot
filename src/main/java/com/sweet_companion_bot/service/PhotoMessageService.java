package com.sweet_companion_bot.service;

import com.sweet_companion_bot.unsplash.UnsplashClient;
import com.sweet_companion_bot.unsplash.model.UnsplashImage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;

@Slf4j
@PropertySource("classpath:messages_en.properties")
@Service
public class PhotoMessageService  {

    private String token;
    private String imagesStorage;
    private int numberOfStoredImages;
    private UnsplashClient unsplashClient;

    public PhotoMessageService(@Value("${telegrambot.botToken}") String token,
                               @Value("${telegrambot.imagesStorage}") String imagesStorage,
                               @Value("${numberOfStoredImages}") int numberOfStoredImages,
                               UnsplashClient unsplashClient) {
        this.token = token;
        this.imagesStorage = imagesStorage;
        this.numberOfStoredImages = numberOfStoredImages;
        this.unsplashClient = unsplashClient;
    }

    @SneakyThrows
    private String getImageDownloadLinkFromUnsplash() {
        UnsplashImage unsplashImage = unsplashClient.getRandomPhoto();

        if (unsplashImage.urls != null) {
            String downloadLink = unsplashImage.urls.regular;
            log.info("PhotoMessageService --- getImageFromUnsplash() : Return image download link from Unsplash");
            return downloadLink;
        }
        log.info("PhotoMessageService --- getImageFromUnsplash() : Error. Didn't get a download link from Unsplash");
        return "";
    }

    @SneakyThrows
    public String sendImage(String chatId) {

        String imageName = String.join("", "image", String.valueOf(Util.getRandomInt(1, numberOfStoredImages)), ".png");
        String pathToLocalFile = String.join("", imagesStorage, imageName);
        String url = String.join("", "https://api.telegram.org/bot", token, "/sendPhoto?chat_id=", chatId);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFilePostRequest = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        String errorMessage = "";

        String downloadLink = getImageDownloadLinkFromUnsplash();
        InputStream inputStream = null;

        if (!downloadLink.equals("")) {
            inputStream = new URL(downloadLink).openStream();
        } else {
            try {
                inputStream = new FileInputStream(pathToLocalFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    return errorMessage = "reply.exception.1";
                 }
            log.info("PhotoMessageService --- sendImage(): Didn't get an image from Unsplash. One of the locally stored photos will be sent.");
        }

        builder.addBinaryBody(
                    "photo", // this is the key for param
                    inputStream,
                    ContentType.APPLICATION_OCTET_STREAM,
                    "photoToSend"
        );

        HttpEntity multipart = builder.build();
        uploadFilePostRequest.setEntity(multipart);

        CloseableHttpResponse response;
        response = httpClient.execute(uploadFilePostRequest);

        if (response.getStatusLine().getStatusCode() != 200) {
            log.error("PhotoMessageService --- sendImage(): An error occurred while sending the HTTP request. Status code: {}", response.getStatusLine().getStatusCode());
            return errorMessage = "reply.exception.2";
        }

        HttpEntity responseEntity = response.getEntity();
        String responseString;
        responseString = EntityUtils.toString(responseEntity, "UTF-8");

        log.info("PhotoMessageService --- sendImage(): Response status line: {}", response.getStatusLine());
        log.info("PhotoMessageService --- sendImage(): Response body: {}", responseString);

        return errorMessage;
    }
}