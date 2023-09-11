package hu.bme.aut.retelab2.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ad {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    private int price;
    @CreationTimestamp
    private LocalDateTime creationDateTime;

    private String secretCode;

    @ElementCollection
    private List<String> tags = new ArrayList<>();

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public LocalDateTime getCreationDateTime() { return creationDateTime; }
    public void setCreationDateTime(LocalDateTime creationDateTime) { this.creationDateTime = creationDateTime; }
    public String getSecretCode() {
        return secretCode;
    }
    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}