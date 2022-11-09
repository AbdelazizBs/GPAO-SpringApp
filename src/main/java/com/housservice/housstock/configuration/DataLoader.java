package com.housservice.housstock.configuration;

import com.github.javafaker.Faker;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.CommandeClientRepository;
import com.housservice.housstock.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    PersonnelRepository personnelRepository ;

    @Autowired MessageHttpErrorProperties messageHttpErrorProperties;

    @Autowired
    CommandeClientRepository commandeClientRepository ;
    @Autowired
    ClientRepository  clientRepository ;
    @Override
    public void run(String... args) throws Exception {
        loadUserData();

    }


    //condition to save data whith Faker  to dataBase for test when running application .
    //MODEL : [CommandClient , Client ,Personnel]
    private void loadUserData() throws ResourceNotFoundException {
        int i =0 ;
        if (personnelRepository.count()<10 || clientRepository.count()<10 || commandeClientRepository.count()<10)
        while (i < 15) {
            Faker faker =Faker.instance();
            int ramdomN = faker.number().numberBetween(0, 1);
            String sex;
            if (ramdomN == 0) {
                sex = "homme";
            } else {
                sex = "femme";
            }
            int ramdomMiseEnVeille = faker.number().numberBetween(0, 1);
            int ramdomRegime = faker.number().numberBetween(0, 1);
            String regim;
            if (ramdomN == 0) {
                regim = "société tunisienne";
            } else {
                regim = "société étrangère";
            }
            Client  client = new Client(
                    faker.date().birthday(),
                    faker.name().title(),
                    regim,
                    "Textile",
                    "Maille",
                    faker.address().country(),
                    faker.address().city(),
                    "EXW",
                    "60j",
                    "Chèque",
                    "BIAT",
                    "545644534545",
                    "1256458454",
                    "15415646545",
                    faker.date().birthday(),
                    ramdomMiseEnVeille,
                    new ArrayList<>(),
                    new ArrayList<>()
                    );
            clientRepository.save(client);
            Personnel personnel = new Personnel(faker.name().firstName(),
                    faker.name().lastName(),
                    faker.date().birthday(),
                    faker.address().city(),
                    faker.file().fileName(),
                    "084425423",
                    sex,
                    "15514845454564412",
                    "personnel",
                    faker.date().birthday(),
                    faker.number().numberBetween(1,9),
                    faker.name().title(),
                    faker.number().numberBetween(1,9999),
                    new Comptes(),
                    false
            );
            personnelRepository.save(personnel);
            CommandeClient commandeClient = new CommandeClient(
                    "Stock",
                    "CMD02155",
                    "Non Fermer",
                    faker.date().birthday(),
                    faker.date().birthday(),
                    false,
"En attente",
                    client
                    );
            commandeClientRepository.save(commandeClient);
            i++;
        }
    }
}
