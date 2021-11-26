package com.sweet_companion_bot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@PropertySource("classpath:messages_en.properties")
public class QueryService {

    @Value("#{'${categories.list.animals}'.split(', ')}")
    private List<String> animalsCategory;

    @Value("#{'${categories.list.nature}'.split(', ')}")
    private List<String> natureCategory;

    @Value("#{'${categories.list.girls}'.split(', ')}")
    private List<String> girlsCategory;

    @Value("#{'${categories.list.men}'.split(', ')}")
    private List<String> menCategory;

    public String getQueryFromCategory(String category) {

        String query;
        int n = Util.getRandomInt(0, 4);

        switch(category) {
            case "Animals":
                query = String.join("", "cute+", animalsCategory.get(n));
                break;
            case "Nature":
                query = String.join("", "", natureCategory.get(n));
                break;
            case "Girls":
                query = String.join("", girlsCategory.get(n), "+girl");
                break;
            case "Men":
                query = String.join("", menCategory.get(n), "+man");
                break;
            default:
                query = "cute";
                break;
        }
        return query;
    }
}
