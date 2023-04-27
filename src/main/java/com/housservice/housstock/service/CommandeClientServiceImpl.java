package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.ArticleMapper;
import com.housservice.housstock.mapper.CommandeClientMapper;
import com.housservice.housstock.mapper.CommandeClientSuiviMapper;
import com.housservice.housstock.mapper.CommandeSuiviMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.*;
import com.housservice.housstock.model.dto.ArticleDto;
import com.housservice.housstock.model.dto.CommandeClientDto;
import com.housservice.housstock.model.dto.CommandeClientSuiviDto;
import com.housservice.housstock.model.dto.CommandeSuiviDto;
import com.housservice.housstock.repository.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class CommandeClientServiceImpl implements CommandeClientService{
    private final CommandeClientRepository commandeClientRepository;
    private final ClientRepository clientRepository;
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    private final ArticleRepository articleRepository;
    private final CommandeClientSuiviRepository commandeClientSuiviRepository;

    private final AffectationProduitRepository affectationProduitRepository;

    public CommandeClientServiceImpl(CommandeClientRepository commandeClientRepository,CommandeClientSuiviRepository commandeClientSuiviRepository,ArticleRepository articleRepository, ClientRepository clientRepository, MessageHttpErrorProperties messageHttpErrorProperties, AffectationProduitRepository affectationProduitRepository) {

        this.commandeClientRepository = commandeClientRepository;
        this.clientRepository = clientRepository;
        this.commandeClientSuiviRepository = commandeClientSuiviRepository;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.articleRepository = articleRepository;
        this.affectationProduitRepository = affectationProduitRepository;

    }

    @Override
    public Optional<CommandeClient> getCommandeClientById(String id) {
        return commandeClientRepository.findById(id);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCommandeClient(int page, int size) {
        try {
            List<CommandeClientDto> commandeClients = new ArrayList<CommandeClientDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<CommandeClient> pageTuts;
            pageTuts =  commandeClientRepository.findAll(paging);
            commandeClients = pageTuts.getContent().stream().map(commandeClient -> CommandeClientMapper.MAPPER.toCommandeClientDto(commandeClient)).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("commandeClients", commandeClients);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public void createNewCommandeClient(CommandeClientDto commandeClientDto) throws ResourceNotFoundException {
        commandeClientDto.setMiseEnVeille(false);
        List<Article> articles = new ArrayList<>();
        if (commandeClientDto.getArticle()==null){
            commandeClientDto.setArticle(articles);
        }
        CommandeClient commandeClient = CommandeClientMapper.MAPPER.toCommandeClient(commandeClientDto);
        commandeClientRepository.save(commandeClient);
    }

    @Override
    public void UpdateCommandeClient(String idCommandeClient,CommandeClient commandeClient) throws ResourceNotFoundException {
        if (commandeClient.getNumBcd().isEmpty()) {
            throw new IllegalArgumentException("Veuillez remplir tous les champs obligatoires !!");
        }
        System.out.println(commandeClient.getDateCommande());
        CommandeClient commandeClient2 = commandeClientRepository.findById(idCommandeClient)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idCommandeClient)));
        commandeClient2.setCommentaire(commandeClient.getCommentaire());
        commandeClient2.setClient(commandeClient.getClient());
        commandeClient2.setNumBcd(commandeClient.getNumBcd());
        commandeClient2.setDateCommande(commandeClient.getDateCommande());
        commandeClientRepository.save(commandeClient);
    }



    @Override
    public void deleteCommandeClient(CommandeClient commandeClient) {
        commandeClientRepository.delete(commandeClient);
    }

    @Override
    public void deleteCommandeClientSelected(List<String> idCommandeClientsSelected) {
        for (String id : idCommandeClientsSelected){
            commandeClientRepository.deleteById(id);
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>>  getIdCommandeClients(String numBcd) throws ResourceNotFoundException {
        CommandeClient commandeClient = commandeClientRepository.findCommandeClientByNumBcd(numBcd).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),numBcd)));
        Map<String, Object> response = new HashMap<>();
        response.put("idCommandeClient", commandeClient.getId());
        return ResponseEntity.ok(response);
    }

    @Override
    public int getallCommandeClient() {
        try {
            List<CommandeClient> commandeClient = commandeClientRepository.findAll();
            System.out.println(commandeClient);
            return (int) commandeClient.stream().count();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;


        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> onSortActiveCommandeClient(int page, int size, String field, String order) {
        try {
            List<CommandeClientDto> commandeClientDtos ;
            Page<CommandeClient> pageTuts;
            if (order.equals("1")){
                pageTuts = commandeClientRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
            }
            else {
                pageTuts = commandeClientRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
            }
            commandeClientDtos = pageTuts.getContent().stream().map(commandeClient -> {
                return CommandeClientMapper.MAPPER.toCommandeClientDto(commandeClient);
            }).collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("commandeClients", commandeClientDtos);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public List<String> getAllClients() {
        List<Client> fournisseurs = clientRepository.findClientByMiseEnVeille(false);
        return fournisseurs.stream()
                .map(Client::getRaisonSocial)
                .collect(Collectors.toList());
    }

    public ResponseEntity<byte[]> RecordReport(String id) {
        try{
            List<CommandeClient> commandeClient= commandeClientRepository.findCommandeClientByid(id);
            File file = ResourceUtils.getFile("classpath:CommandeClient.jrxml");
            JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(commandeClient);
            Map<String ,Object> parameter = new HashMap<>();
            parameter.put("CreatedBy","Hellotest");
            JasperPrint print = JasperFillManager.fillReport(report, parameter,dataSource);
            HttpHeaders headers = new HttpHeaders();
            //set the PDF format
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "employees-details.pdf");
            //create the report in PDF format
            return new ResponseEntity<byte[]>
                    (JasperExportManager.exportReportToPdf(print), headers, HttpStatus.OK);

        } catch(Exception e) {
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void addArticleCommandeClient(ArticleDto articleDto, String idCommandeClient) throws ResourceNotFoundException {
        CommandeClient commandeClient = getCommandeClientById(idCommandeClient)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idCommandeClient)));
        List<Article> articles = new ArrayList<>();
        Article article1 = ArticleMapper.MAPPER.toArticle(articleDto);
        if(commandeClient.getArticle()==null){
            articles.add(article1);
            articleRepository.save(article1);
            commandeClient.setArticle(articles);
            commandeClientRepository.save(commandeClient);
        }
        articleRepository.save(article1);
        articles.add(article1);
        articles.addAll(commandeClient.getArticle());
        commandeClient.setArticle(articles);
        commandeClientRepository.save(commandeClient);

    }

    @Override
    public void updateArticleCommandeClient(ArticleDto articleDto, String idArticle) throws ResourceNotFoundException {
        CommandeClient commandeClient =commandeClientRepository.findCommandeClientByArticleId(idArticle)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  articleDto)));
        Article articleToUpdate = articleRepository.findById(idArticle)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  articleDto.getId())));
        commandeClient.getArticle().removeIf(article1 -> article1.equals(articleToUpdate));
        articleToUpdate.setCommentaire(articleDto.getCommentaire());
        articleToUpdate.setPrix(articleDto.getPrix());
        articleToUpdate.setQuantite(articleDto.getQuantite());
        articleToUpdate.setPrixUnitaire(articleDto.getPrixUnitaire());
        articleToUpdate.setDesignationMatiere(articleDto.getDesignationMatiere());
        articleToUpdate.setDateLivraison(articleDto.getDateLivraison());
        articleRepository.save(articleToUpdate);
        commandeClient.getArticle().add(articleToUpdate);
        commandeClientRepository.save(commandeClient);

    }

    @Override
    public void deleteArticleCommandeClient(String idArticle) throws ResourceNotFoundException {
        CommandeClient commandeClient= commandeClientRepository.findCommandeClientByArticleId(idArticle)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idArticle)));
        Article article = articleRepository.findById(idArticle)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idArticle)));
        List<Article> articleList = commandeClient.getArticle();
        articleList.removeIf(c -> c.equals(article));
        commandeClient.setArticle(articleList);
        commandeClientRepository.save(commandeClient);
        articleRepository.deleteById(idArticle);
    }

    @Override
    public void miseEnVeille(String id, CommandeClientSuiviDto commandeClientSuiviDto) throws ResourceNotFoundException {

        CommandeClient commande = commandeClientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
        commande.setMiseEnVeille(true);
        String name= commande.getClient();
        Client client = clientRepository.findClientByRaisonSocial(name).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),name)));
        client.setRate(commandeClientSuiviDto.getRate());
        clientRepository.save(client);
        commandeClientRepository.save(commande);
        CommandeClientSuivi commandeSuivi = CommandeClientSuiviMapper.MAPPER.toCommandeClientSuivi(commandeClientSuiviDto);
        commandeSuivi.setRaisonSocial(client.getRaisonSocial());
        commandeClientSuiviRepository.save(commandeSuivi);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getCommandeClientNotActive(int page, int size) {
        try {

            List<CommandeClientSuiviDto> commandesClient = new ArrayList<CommandeClientSuiviDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<CommandeClientSuivi> pageTuts;
            pageTuts =  commandeClientSuiviRepository.findAll(paging);
            commandesClient = pageTuts.getContent().stream().map(commandeClientSuivi -> CommandeClientSuiviMapper.MAPPER.toCommandeClientSuiviDto(commandeClientSuivi)).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("commandesClient", commandesClient);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public List<String> getAllArticle(String nomClient){
        List<AffectationProduit> matieres = affectationProduitRepository.findAffectationProduitBylistClient(nomClient);
        return matieres.stream()
                .map(AffectationProduit::getDestination)
                .collect(Collectors.toList());
    }

    public List<AffectationProduit> getArticleAttribut(String designation){
        List<AffectationProduit> Article = affectationProduitRepository.findAffectationProduitByDestination(designation);
        return Article;


    }

}
