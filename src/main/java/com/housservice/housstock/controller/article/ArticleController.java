package com.housservice.housstock.controller.article;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/articleCommande")
@Api(tags = {"Article Commandes Management"})

public class ArticleController {
    final
    ArticleService articleService ;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }
    @DeleteMapping("/deleteArticleCommande/{idArticle}")
    @ApiOperation(value = "service to delete one Commande by Id.")
    public Map< String, Boolean > deleteArticleCommande(
            @ApiParam(name = "idArticle", value="id of commande", required = true)
            @PathVariable(value = "idArticle", required = true) @NotEmpty(message = "{http.error.0001}") String idArticle)
            throws ResourceNotFoundException {
        articleService.deleteArticleCommande(idArticle);
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
