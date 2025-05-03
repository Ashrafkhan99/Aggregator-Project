package com.example.Aggregator.Services;

import com.example.Aggregator.DTO.NewsArticleDto;
import com.example.Aggregator.Entity.Favorite;
import com.example.Aggregator.Entity.Preference;
import com.example.Aggregator.Entity.User;
import com.example.Aggregator.Repository.FavoriteRepository;
import com.example.Aggregator.Repository.UserRepository;
import com.example.Aggregator.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class NewsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    private final String API_KEY = "a7464bc1be174ce098ad43830dfc6857";
    private final String NEWS_API_URL = "https://newsapi.org/v2/everything";

    public List<NewsArticleDto> fetchNews(String token, String fromDate) {
        String username = JwtUtil.extractUsername(token);
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Set<Preference> preferences = user.getPreferences();
        if (preferences.isEmpty()) {
            throw new IllegalArgumentException("No preferences found for user");
        }

        RestTemplate restTemplate = new RestTemplate();
        List<NewsArticleDto> articles = new ArrayList<>();

        for (Preference pref : preferences) {
            String url = NEWS_API_URL + "?q=" + pref.getKeyword() + "&from="+ fromDate + "&sortBy=popularity&apiKey=" + API_KEY;
            Map response = restTemplate.getForObject(url, Map.class);
            List<Map<String, Object>> newsList = (List<Map<String, Object>>) response.get("articles");
            for (Map<String, Object> news : newsList) {
                NewsArticleDto article = new NewsArticleDto();
                article.setTitle((String) news.get("title"));
                article.setDescription((String) news.get("description"));
                article.setUrl((String) news.get("url"));
                articles.add(article);
            }
        }

        return articles;
    }

    public List<Favorite> addFavoritesByAuthorAndDate(String token, Long userId, String author, String fromDate, String title) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        RestTemplate restTemplate = new RestTemplate();
        String url = NEWS_API_URL + "?q=" + title + "&from=" + fromDate + "&sortBy=popularity&apiKey=" + API_KEY;

        Map response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> newsList = (List<Map<String, Object>>) response.get("articles");

        List<Favorite> favorites = new ArrayList<>();

        for (Map<String, Object> news : newsList) {
            if (author.equalsIgnoreCase((String) news.get("author"))) {
                Favorite favorite = new Favorite();
                favorite.setTitle((String) news.get("title"));
                favorite.setAuthor(author);
                favorite.setContent((String) news.get("content"));
                favorite.setUrl((String) news.get("url"));
                favorite.setUser(user);
                favorites.add(favoriteRepository.save(favorite));
            }
        }

        return favorites;
    }
}
