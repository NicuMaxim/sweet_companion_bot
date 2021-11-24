package com.sweet_companion_bot.random_compliment;

import com.google.gson.FieldNamingPolicy;
import com.sangupta.jerry.util.GsonUtils;
import com.sweet_companion_bot.random_compliment.model.RandomCompliment;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

// I'm not going to use it because I don't like the random messages I receive from this API
public class RandomComplimentService {

    @SneakyThrows
    public String getRandomCompliment() {

        String url = "https://complimentr.com/api";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response;
        response = httpClient.execute(request);

        InputStream inputStream = response.getEntity().getContent();
        String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        RandomCompliment randomComplimentObject = GsonUtils.getGson(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).fromJson(json, RandomCompliment.class);
        String randomCompliment = randomComplimentObject.compliment;

        return randomCompliment;
    }
}
