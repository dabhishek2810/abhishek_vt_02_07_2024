package com.abhishek.url.controller;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.abhishek.url.service.UrlService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody String longUrl) {
        String shortUrl = urlService.shortenUrl(longUrl);
        return ResponseEntity.ok(shortUrl);
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateShortUrl(@RequestBody Map<String, String> request) {
        String shortUrl = request.get("shortUrl");
        String newLongUrl = request.get("newLongUrl");
        boolean success = urlService.updateShortUrl(shortUrl, newLongUrl);
        return ResponseEntity.ok(success);
    }

    @GetMapping("/{shortUrl}")
    public void redirectToFullUrl(HttpServletResponse response, @PathVariable String shortUrl) {
        try {
            String longUrl = urlService.getLongUrl(shortUrl);
            if (longUrl != null) {
                response.sendRedirect(longUrl);
            } else {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL not found", e);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not redirect to the full URL", e);
        }
    }

    @PostMapping("/update-expiry")
    public ResponseEntity<Boolean> updateExpiry(@RequestBody Map<String, Object> request) {
        String shortUrl = (String) request.get("shortUrl");
        int daysToAdd = (int) request.get("daysToAdd");
        boolean success = urlService.updateExpiry(shortUrl, daysToAdd);
        return ResponseEntity.ok(success);
    }
}
