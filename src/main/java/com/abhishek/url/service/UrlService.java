package com.abhishek.url.service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhishek.url.entity.URL;
import com.abhishek.url.respository.UrlRepository;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public String shortenUrl(String longUrl) {
        String shortUrl = generateShortUrl();
        Timestamp creationDate = new Timestamp(System.currentTimeMillis());
        Timestamp expiryDate = new Timestamp(creationDate.getTime() + TimeUnit.DAYS.toMillis(300));
        
        URL url = new URL();
        url.setShortUrl(shortUrl);
        url.setLongUrl(longUrl);
        url.setCreationDate(creationDate);
        url.setExpiryDate(expiryDate);
        
        urlRepository.save(url);
        return shortUrl;
    }

    public boolean updateShortUrl(String shortUrl, String newLongUrl) {
        Optional<URL> urlOptional = urlRepository.findByShortUrl(shortUrl);
        if (urlOptional.isPresent()) {
            URL url = urlOptional.get();
            url.setLongUrl(newLongUrl);
            urlRepository.save(url);
            return true;
        }
        return false;
    }

    public String getLongUrl(String shortUrl) {
        Optional<URL> urlOptional = urlRepository.findByShortUrl(shortUrl);
        if (urlOptional.isPresent() && urlOptional.get().getExpiryDate().after(new Timestamp(System.currentTimeMillis()))) {
            return urlOptional.get().getLongUrl();
        }
        return null;
    }

    public boolean updateExpiry(String shortUrl, int daysToAdd) {
        Optional<URL> urlOptional = urlRepository.findByShortUrl(shortUrl);
        if (urlOptional.isPresent()) {
            URL url = urlOptional.get();
            Timestamp newExpiryDate = new Timestamp(url.getExpiryDate().getTime() + TimeUnit.DAYS.toMillis(daysToAdd));
            url.setExpiryDate(newExpiryDate);
            urlRepository.save(url);
            return true;
        }
        return false;
    }

    private String generateShortUrl() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder shortUrl = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortUrl.toString();
    }
}
