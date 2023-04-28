package com.housservice.housstock.mapper;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.dto.ArticleDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-28T16:46:31+0200",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class ArticleMapperImpl extends ArticleMapper {

    @Override
    public ArticleDto toArticleDto(Article article) throws ResourceNotFoundException {
        if ( article == null ) {
            return null;
        }

        ArticleDto articleDto = new ArticleDto();

        articleDto.setId( article.getId() );
        articleDto.setReferenceIris( article.getReferenceIris() );
        articleDto.setNumFicheTechnique( article.getNumFicheTechnique() );
        articleDto.setDesignation( article.getDesignation() );
        List<EtapeProduction> list = article.getEtapeProductions();
        if ( list != null ) {
            articleDto.setEtapeProductions( new ArrayList<EtapeProduction>( list ) );
        }
        articleDto.setTypeProduit( article.getTypeProduit() );
        articleDto.setRefClient( article.getRefClient() );
        articleDto.setRaisonSocial( article.getRaisonSocial() );
        articleDto.setPrix( article.getPrix() );
        articleDto.setMiseEnVeille( article.getMiseEnVeille() );
        articleDto.setPicture( article.getPicture() );
        articleDto.setDateCreationArticle( article.getDateCreationArticle() );

        updateArticleDto( article, articleDto );

        return articleDto;
    }

    @Override
    public Article toArticle(ArticleDto articleDto) throws ResourceNotFoundException {
        if ( articleDto == null ) {
            return null;
        }

        Article article = new Article();

        article.setId( articleDto.getId() );
        article.setReferenceIris( articleDto.getReferenceIris() );
        article.setNumFicheTechnique( articleDto.getNumFicheTechnique() );
        article.setPrix( articleDto.getPrix() );
        article.setDesignation( articleDto.getDesignation() );
        article.setTypeProduit( articleDto.getTypeProduit() );
        article.setRefClient( articleDto.getRefClient() );
        article.setRaisonSocial( articleDto.getRaisonSocial() );
        article.setPicture( articleDto.getPicture() );
        List<EtapeProduction> list = articleDto.getEtapeProductions();
        if ( list != null ) {
            article.setEtapeProductions( new ArrayList<EtapeProduction>( list ) );
        }
        article.setDateCreationArticle( articleDto.getDateCreationArticle() );
        article.setMiseEnVeille( articleDto.getMiseEnVeille() );

        updateArticle( articleDto, article );

        return article;
    }
}
