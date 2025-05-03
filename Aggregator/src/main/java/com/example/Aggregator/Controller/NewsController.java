package com.example.Aggregator.Controller;

import com.example.Aggregator.DTO.NewsArticleDto;
import com.example.Aggregator.Entity.Favorite;
import com.example.Aggregator.Services.NewsService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/{fromDate}")
    public List<NewsArticleDto> getNews(@RequestHeader("Authorization") String token, @PathParam("fromDate") String fromDate) {
        return newsService.fetchNews(token.substring(7), fromDate);
    }

    @PostMapping("/User/{id}/favorites/{author}")
    public List<Favorite> addFavoritesByAuthorAndDate(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long userId,
            @PathVariable("author") String author,
            @RequestParam("fromDate") String fromDate,
            @RequestParam("title") String title) {
        return newsService.addFavoritesByAuthorAndDate(token.substring(7), userId, author, fromDate, title);
    }
}
