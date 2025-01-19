package it.lucadev.service;

import java.util.List;

import io.smallrye.mutiny.Uni;
import it.lucadev.article_api.model.ArticleDTO;


public interface ArticleService {

    public Uni<ArticleDTO> createArticle(ArticleDTO dto);

    public Uni<Boolean> deleteArticleById(Long id);

    public Uni<List<ArticleDTO>> getAllArticles();

    public Uni<ArticleDTO> getArticleById(Long id);

    public Uni<ArticleDTO> updateArticleById(Long id, ArticleDTO articleDTO);

}
