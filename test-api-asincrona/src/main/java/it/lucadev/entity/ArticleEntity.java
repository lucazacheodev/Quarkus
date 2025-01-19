package it.lucadev.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Float price;
    private String description;
    private String picture;


    public ArticleEntity() {
    }

    public ArticleEntity(Long id, String name, Float price, String description, String picture) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.picture = picture;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public ArticleEntity id(Long id) {
        setId(id);
        return this;
    }

    public ArticleEntity name(String name) {
        setName(name);
        return this;
    }

    public ArticleEntity price(Float price) {
        setPrice(price);
        return this;
    }

    public ArticleEntity description(String description) {
        setDescription(description);
        return this;
    }

    public ArticleEntity picture(String picture) {
        setPicture(picture);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ArticleEntity)) {
            return false;
        }
        ArticleEntity articleEntity = (ArticleEntity) o;
        return Objects.equals(id, articleEntity.id) && Objects.equals(name, articleEntity.name) && Objects.equals(price, articleEntity.price) && Objects.equals(description, articleEntity.description) && Objects.equals(picture, articleEntity.picture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, description, picture);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            ", description='" + getDescription() + "'" +
            ", picture='" + getPicture() + "'" +
            "}";
    }
    
}
