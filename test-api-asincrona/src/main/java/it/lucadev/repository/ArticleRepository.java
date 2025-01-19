package it.lucadev.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import it.lucadev.entity.ArticleEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ArticleRepository implements PanacheRepository<ArticleEntity> {

}
