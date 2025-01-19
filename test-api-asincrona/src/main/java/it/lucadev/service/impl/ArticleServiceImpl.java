package it.lucadev.service.impl;

import java.util.List;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import it.lucadev.article_api.model.ArticleDTO;
import it.lucadev.entity.ArticleEntity;
import it.lucadev.repository.ArticleRepository;
import it.lucadev.service.ArticleService;

import it.lucadev.util.mapper.ArticleMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@WithTransaction
@ApplicationScoped
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository repo;
    private final ArticleMapper mapper;

    @Inject
    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.repo = articleRepository;
        this.mapper = articleMapper;
    }

    @WithTransaction
    @Override
    public Uni<ArticleDTO> createArticle(ArticleDTO dto) {
        ArticleEntity entity = mapper.dtoToEntity(dto);
        Uni<ArticleEntity> persistedEntityUni = repo.persist(entity);
        Uni<ArticleDTO> persistedDto = persistedEntityUni.onItem().transform(x -> mapper.entityToDto(x));
        return persistedDto;
    }

    @Override
    public Uni<Boolean> deleteArticleById(Long id) {
        return repo.deleteById(id);
    }

    @Override
    public Uni<List<ArticleDTO>> getAllArticles() {
        return repo.findAll().list()
                .onItem().transform(entityList -> entityList.stream()
                        .map(entity -> mapper.entityToDto(entity))
                        .toList());
    }

    @Override
    public Uni<ArticleDTO> getArticleById(Long id) {
        return repo.findById(id).onItem().transform(x -> mapper.entityToDto(x));
    }

    @Override
    public Uni<ArticleDTO> updateArticleById(Long id, ArticleDTO articleDTO) {
        return repo.findById(id)
                .onItem().ifNotNull().transformToUni(existingArticle -> {
                    existingArticle.setName(articleDTO.getName());
                    existingArticle.setPicture(articleDTO.getPicture());
                    existingArticle.setPrice(articleDTO.getPrice());

                    return repo.persist(existingArticle)
                            .onItem().transform(updatedEntity -> mapper.entityToDto(updatedEntity));
                })
                .onItem().ifNull().continueWith(() -> null);
    }
}
