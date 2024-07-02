package com.abhishek.url.entity;



import java.sql.Timestamp;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class URL {
	
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @Column(unique = true, length = 30)
	    private String shortUrl;

	    @Column(columnDefinition = "TEXT")
	    private String longUrl;

	    private Timestamp creationDate;

	    private Timestamp expiryDate;

	    public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getShortUrl() {
			return shortUrl;
		}

		public void setShortUrl(String shortUrl) {
			this.shortUrl = shortUrl;
		}

		public String getLongUrl() {
			return longUrl;
		}

		public void setLongUrl(String longUrl) {
			this.longUrl = longUrl;
		}

		public Timestamp getCreationDate() {
			return creationDate;
		}

		public void setCreationDate(Timestamp creationDate) {
			this.creationDate = creationDate;
		}

		public Timestamp getExpiryDate() {
			return expiryDate;
		}

		public void setExpiryDate(Timestamp expiryDate) {
			this.expiryDate = expiryDate;
		}

	    
	    
	

}
