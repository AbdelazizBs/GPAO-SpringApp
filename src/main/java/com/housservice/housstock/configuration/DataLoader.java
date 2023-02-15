package com.housservice.housstock.configuration;

import com.github.javafaker.Faker;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.CommandeClientRepository;
import com.housservice.housstock.repository.FournisseurRepository;
import com.housservice.housstock.repository.PersonnelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DataLoader implements CommandLineRunner {

    final
    PersonnelRepository personnelRepository ;

    final MessageHttpErrorProperties messageHttpErrorProperties;

    final
    CommandeClientRepository commandeClientRepository ;
    final
    ClientRepository  clientRepository ;
    private final FournisseurRepository fournisseurRepository;

    public DataLoader(PersonnelRepository personnelRepository, MessageHttpErrorProperties messageHttpErrorProperties, CommandeClientRepository commandeClientRepository, ClientRepository clientRepository,
                      FournisseurRepository fournisseurRepository) {
        this.personnelRepository = personnelRepository;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.commandeClientRepository = commandeClientRepository;
        this.clientRepository = clientRepository;
        this.fournisseurRepository = fournisseurRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadUserData();

    }


    //condition to save data whith Faker  to test when  application .
    //MODEL : [ Client ,Personnel , Fournisseur , Nomenclature]
    private void loadUserData() throws ResourceNotFoundException {
        int i =0 ;
        while (i < 15) {
            Faker faker =Faker.instance();
            int ramdomN = faker.number().numberBetween(0, 1);
            String genre;
            if (ramdomN == 0) {
                genre = "Homme";
            } else {
                genre = "Femme";
            }
            int ramdomMiseEnVeille = faker.number().numberBetween(0, 1);
            String regim;
            if (ramdomN == 0) {
                regim = "société tunisienne";
            } else {
                regim = "société étrangère";
            }
            Client client = new Client(
                    faker.date().birthday(),
                    faker.name().title(),
                    faker.name().firstName()+" "+faker.name().lastName() + " " + faker.name().lastName(),
                    regim,
                    "Textile",
                    "Maille",
                    faker.address().country(),
                    faker.address().city(),
                    "EXW",
                    "60j",
                    "Chèque",
                    "+21629883656",
                    "73598242,23658451",
                   faker.number().toString(),
                    "055455545454644648"+i,
                    "tester@gmail.com",
                    false,
                    ramdomMiseEnVeille,
                    new ArrayList<>(),
                    new ArrayList<>()
                    );
            clientRepository.save(client);
            Personnel personnel = new Personnel(
                    faker.name().firstName(),
                    faker.name().lastName(),
                   faker.date().birthday(),
                    faker.address().city(),
                    faker.file().fileName(),
                    "084425423"+ i,
                    genre,
                    "15514845454564412",
                    "personnel",
                    faker.date().birthday(),
                    faker.number().toString(),
                    faker.name().title(),
                    "1255"+ i,
                    "+21629883494"+i,
                    false,
                    faker.address().city(),
                    "5025"+i,
                    "TestEmail@gmail.com"
            );
            personnelRepository.save(personnel);

            Fournisseur fournisseur = new Fournisseur(
                    "055455545454644648"+ i,
                    faker.name().firstName()+" "+faker.name().lastName() + " " + faker.name().firstName(),
                    faker.address().streetAddress() + faker.address().streetName(),
                    faker.address().countryCode(),
                    faker.address().cityName(),
                    faker.address().city(),
                    faker.address().country(),
                    "995213645256"+ i,
                    "454564615645415"+ i,
                    "tester@gamil.com",
                    "Bank of Tunisie",
                    faker.address().streetAddress() + faker.address().streetName(),
                    "454554564684451655222"+i,
                    "21121212"+i,
                    "++55"+i,
                    "0252433"+i,
                    "ITVA25154"+ i,
                    0,
                    new ArrayList<>(),
                    faker.date().birthday()
                    );
            fournisseurRepository.save(fournisseur);
            i++;
        }
    }
}
