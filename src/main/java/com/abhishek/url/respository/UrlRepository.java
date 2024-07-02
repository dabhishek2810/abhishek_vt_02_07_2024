package com.abhishek.url.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abhishek.url.entity.URL;



@Repository
public interface UrlRepository extends JpaRepository<URL, Long> {
    Optional<URL> findByShortUrl(String shortUrl);
}
