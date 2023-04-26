package com.housservice.housstock.mapper;


import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.dto.ArticleDto;
import com.housservice.housstock.repository.ArticleRepository;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.PictureRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ArticleMapper {

@Autowired
private PictureRepository pictureRepository;
    @Autowired
    private  ClientRepository clientRepository ;
    @Autowired
    private  ArticleRepository articleRepository ;
    @Autowired
    private  MessageHttpErrorProperties messageHttpErrorProperties;
    public static ArticleMapper  MAPPER = Mappers.getMapper(ArticleMapper.class);



    public abstract ArticleDto toArticleDto(Article article) throws ResourceNotFoundException;


    public abstract Article toArticle(ArticleDto  articleDto) throws ResourceNotFoundException;

    @AfterMapping
    void updateArticleDto(Article article, @MappingTarget ArticleDto articleDto) throws ResourceNotFoundException {
        articleDto.setIdClient(article.getClient().getId());
    }

    @AfterMapping
    void updateArticle(ArticleDto  articleDto, @MappingTarget Article article) throws ResourceNotFoundException {
//        Client client = clientRepository.findById(articleDto.getIdClient())
//                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),articleDto.getId())));
//        article.setClient(client);
    }


}
