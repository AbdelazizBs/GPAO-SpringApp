package com.housservice.housstock.mapper;


import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.dto.ArticleDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ArticleMapper {

    public static ArticleMapper  MAPPER = Mappers.getMapper(ArticleMapper.class);



    public abstract ArticleDto toArticleDto(Article article);

    public abstract Article toArticle(ArticleDto  articleDto);

    @AfterMapping
    void updateArticleDto(final Article article, @MappingTarget final ArticleDto articleDto)   {

    }

    @AfterMapping
    void updateArticle(final ArticleDto  articleDto, @MappingTarget final Article article) {

    }


}
