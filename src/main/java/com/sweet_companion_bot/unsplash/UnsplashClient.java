package com.sweet_companion_bot.unsplash;

import com.google.gson.FieldNamingPolicy;
import com.sangupta.jerry.http.service.HttpService;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.GsonUtils;
import com.sangupta.jerry.util.UriUtils;
import com.sweet_companion_bot.service.QueryService;
import com.sweet_companion_bot.unsplash.model.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class UnsplashClient {

    @Autowired
    QueryService queryService;

//    /**
//     * Base URL for the API
//     */
    protected String baseUrl = "https://api.unsplash.com";

//    /**
//     * {@link HttpService} to use
//     */
    private HttpService httpService;

//    /**
//     * The clientID
//     */
    private final String clientID;

//    /**
//     * Constructor
//     *
//     * @param clientID the client ID to use. Cannot be <code>null</code>
//     */
    public UnsplashClient(@Value("${unsplash.chatId}") String clientID) {
        if(AssertUtils.isEmpty(clientID)) {
            throw new IllegalArgumentException("ClientID cannot be null/empty");
        }
        this.clientID = clientID;
    }

//    /**
//     * Get photos from the site.
//     *
//     * @param page
//     * @param perPage
//     * @param sort
//     * @return
//     */
//    public UnsplashImage[] getPhotos(int page, int perPage, UnsplashSort sort) {
//        String url = UriUtils.addWebPaths(this.baseUrl, "/photos?page" + page + "&per_page=" + perPage + "&order_by=" + sort.toString().toLowerCase());
//        return getJSON(url, UnsplashImage[].class);
//    }
//
//    /**
//     * Find all curated photos.
//     *
//     * @param page
//     * @param perPage
//     * @param sort
//     * @return
//     */
//    public UnsplashImage[] getCuratedPhotos(int page, int perPage, UnsplashSort sort) {
//        String url = UriUtils.addWebPaths(this.baseUrl, "/photos/curated?page" + page + "&per_page=" + perPage + "&order_by=" + sort.toString().toLowerCase());
//        return getJSON(url, UnsplashImage[].class);
//    }
//
//    /**
//     * Get details of a given photo.
//     *
//     * @param photoID
//     * @return
//     */
//    public UnsplashImage getPhoto(String photoID) {
//        String url = UriUtils.addWebPaths(this.baseUrl, "/photos/" + photoID);
//        return getJSON(url, UnsplashImage.class);
//    }
//
//    public UnsplashImage getPhoto(String photoID, int width, int height, int[] rect) {
//        String url = UriUtils.addWebPaths(this.baseUrl, "/photos/" + photoID);
//
//        boolean added = false;
//        if(width > 0) {
//            added = true;
//            url = url + "?w=" + width;
//        }
//
//        if(height > 0) {
//            url = url + (added ? "&" : "?");
//            url = url + "h=" + height;
//            added = true;
//        }
//
//        if(AssertUtils.isNotEmpty(rect) || rect.length == 4) {
//            url = url + (added ? "&" : "?");
//            url = "rect=" + rect[0] + "," + rect[1] + "," + rect[2] + "," + rect[3];
//        }
//
//        return getJSON(url, UnsplashImage.class);
//    }
//
//    /**
//     * Get a random photo
//     *
//     * @return
//     */
    public UnsplashImage getRandomPhoto(String category) {
        String query = queryService.getQueryFromCategory(category);
        String orientation = queryService.getRandomOrientation();
        String collections = queryService.getCollectionsFromCategory(category);

        String url = String.join("", this.baseUrl, "/photos/random?query=", query, "&", orientation, "&", collections);
        log.info("UnsplashClient --- getRandomPhoto() : generated url for picture: {}", url);
        return getJSON(url, UnsplashImage.class);
    }

//    public UnsplashImage[] getRandomPhoto(String collections, String featured, String username, String query, int width, int height, UnsplashOrientation orientation, int count) {
//        String url = UriUtils.addWebPaths(this.baseUrl, "/photos/random");
//        UrlManipulator manipulator = new UrlManipulator(url);
//
//        if(AssertUtils.isNotEmpty(collections)) {
//            manipulator.setQueryParam("collections", collections);
//        }
//
//        if(AssertUtils.isNotEmpty(featured)) {
//            manipulator.setQueryParam("featured", featured);
//        }
//
//        if(AssertUtils.isNotEmpty(username)) {
//            manipulator.setQueryParam("username", username);
//        }
//
//        if(AssertUtils.isNotEmpty(query)) {
//            manipulator.setQueryParam("query", query);
//        }
//
//        if(width > 0) {
//            manipulator.setQueryParam("w", String.valueOf(width));
//        }
//
//        if(height > 0) {
//            manipulator.setQueryParam("h", String.valueOf(height));
//        }
//
//        if(orientation != null) {
//            manipulator.setQueryParam("orientation", orientation.toString().toLowerCase());
//        }
//
//        if(count > 0) {
//            manipulator.setQueryParam("count", String.valueOf(count));
//        }
//
//        // only if count is specified that we will get an array
//        if(count <= 0) {
//            UnsplashImage image = this.getJSON(url, UnsplashImage.class);
//
//            return new UnsplashImage[] { image };
//        }
//
//        return this.getJSON(url, UnsplashImage[].class);
//    }

//    /**
//     * Get a photo's statistics.
//     *
//     * @param photoID
//     * @return
//     */
    public UnsplashPhotoStatistic getPhotoStatistics(String photoID) {
        String url = UriUtils.addWebPaths(this.baseUrl, "/photos/" + photoID + "/statistics");
        return getJSON(url, UnsplashPhotoStatistic.class);
    }

//    /**
//     * Hit the given URL as a HTTP GET request and parse the JSON response
//     * into the provided strongly typed object.
//     *
//     * @param url
//     * @param classOfT
//     * @return
//     */

    @SneakyThrows
    private <T> T getJSON(String url, Class<T> classOfT){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet downloadFileGetRequest = new HttpGet(url);
        downloadFileGetRequest.setHeader("Authorization", "Client-ID " + this.clientID);

        CloseableHttpResponse response;
        response = httpClient.execute(downloadFileGetRequest);

        InputStream inputStream = response.getEntity().getContent();

        String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        return GsonUtils.getGson(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).fromJson(json, classOfT);
    }

//    @SneakyThrows
//    public InputStream getPhotoDirectly(String url) { // almost repeats getJSON method. no need
//
//        CloseableHttpClient httpClient = HttpClientBuilder.create() // no need to disable cookies
//                                                          .disableCookieManagement()
//                                                          .build();
//
//        HttpGet downloadFileGetRequest = new HttpGet(url);
//        downloadFileGetRequest.setHeader("Authorization", "Client-ID " + this.clientID);
//
//        CloseableHttpResponse response;
//        response = httpClient.execute(downloadFileGetRequest);
//
//        InputStream inputStream = response.getEntity().getContent();
//
//        return inputStream;
//    }

    // Usual accessors follow

//    /**
//     * @return the httpService
//     */
//    public HttpService getHttpService() {
//        return httpService;
//    }
//
//    /**
//     * @param httpService the httpService to set
//     */
//    public void setHttpService(HttpService httpService) {
//        this.httpService = httpService;
//    }

}