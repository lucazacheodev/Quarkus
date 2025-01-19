package it.lucadev.controller;

import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import it.lucadev.article_api.api.ArticlesApi;
import it.lucadev.article_api.model.ArticleDTO;
import it.lucadev.service.ArticleService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.Response;

@NonBlocking
public class ArticleController implements ArticlesApi {
    private final ArticleService service;

    public ArticleController(ArticleService articleService) {
        this.service = articleService;
    }

    @Override
    @RolesAllowed("admin")
    public Uni<Response> createArticle(@Valid @NotNull ArticleDTO articleDTO) {
        return service.createArticle(articleDTO)
            .onItem().transform(persistedArticle -> Response
                .ok(persistedArticle)
                .build()
            )
            .onFailure().recoverWithItem(throwable -> Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Errore durante la creazione dell'articolo: " + throwable.getMessage())
                .build()
            );
    }

    @Override
    public Uni<Response> deleteArticleById(Long id) {
        return service.deleteArticleById(id)
            .onItem().transform(deleted -> {
                if (deleted) {
                    return Response.noContent().build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                                   .entity("Articolo non trovato")
                                   .build();
                }
            })
            .onFailure().recoverWithItem(throwable -> Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Errore durante l'eliminazione dell'articolo: " + throwable.getMessage())
                .build()
            );
    }

    @Override
    public Uni<Response> getAllArticles() {
        return service.getAllArticles()
            .onItem().transform(dtoList -> Response
                .ok(dtoList)
                .build()
            )
            .onFailure().recoverWithItem(throwable -> Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Errore durante il recupero degli articoli: " + throwable.getMessage())
                .build()
            );
    }

    @Override
    public Uni<Response> getArticleById(Long id) {
        return service.getArticleById(id)
            .onItem().transform(article -> {
                if (article != null) {
                    return Response.ok(article).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                                   .entity("Articolo non trovato")
                                   .build();
                }
            })
            .onFailure().recoverWithItem(throwable -> Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Errore durante il recupero dell'articolo: " + throwable.getMessage())
                .build()
            );
    }

    @Override
    public Uni<Response> updateArticleById(Long id, @Valid @NotNull ArticleDTO articleDTO) {
        return service.updateArticleById(id, articleDTO)
            .onItem().transform(updatedArticle -> {
                if (updatedArticle != null) {
                    return Response.ok(updatedArticle).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                                   .entity("Articolo non trovato")
                                   .build();
                }
            })
            .onFailure().recoverWithItem(throwable -> Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Errore durante l'aggiornamento dell'articolo: " + throwable.getMessage())
                .build()
            );
    }
}
