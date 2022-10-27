package com.housservice.housstock.repository;
import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.CommandeClient;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.housservice.housstock.model.LigneCommandeClient;
import java.util.List;
import java.util.Optional;

public interface LigneCommandeClientRepository extends MongoRepository<LigneCommandeClient, String>{

    Optional<LigneCommandeClient>   findLigneCommandeClientByCommandeClientAndArticle(CommandeClient commande, Article article) ;

    Optional<LigneCommandeClient> findLigneCommandeClientByArticleId(String idArticle) ;

    List<LigneCommandeClient> findLigneCommandeClientByCommandeClient(CommandeClient commandeClient) ;

}
