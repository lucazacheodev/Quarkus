package it.lucadev.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.lucadev.article_api.model.ArticleDTO;
import it.lucadev.entity.ArticleEntity;


@Mapper(componentModel = "cdi")
public interface ArticleMapper{    
    public ArticleDTO entityToDto(ArticleEntity entity);

    @Mapping(target = "id", ignore = true)
    public ArticleEntity dtoToEntity(ArticleDTO dto);
}
