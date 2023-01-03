package com.housservice.housstock.configuration;

import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.CommandeClientRepository;
import com.housservice.housstock.repository.PersonnelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    final
    PersonnelRepository personnelRepository ;

    final MessageHttpErrorProperties messageHttpErrorProperties;

    final
    CommandeClientRepository commandeClientRepository ;
    final
    ClientRepository  clientRepository ;

    public DataLoader(PersonnelRepository personnelRepository, MessageHttpErrorProperties messageHttpErrorProperties, CommandeClientRepository commandeClientRepository, ClientRepository clientRepository) {
        this.personnelRepository = personnelRepository;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.commandeClientRepository = commandeClientRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        loadUserData();

    }
//
//
//    //condition to save data whith Faker  to test when  application .
//    //MODEL : [CommandClient , Client ,Personnel]
//    private void loadUserData() throws ResourceNotFoundException {
//        int i =0 ;
//        if (personnelRepository.count()<10 || clientRepository.count()<10 || commandeClientRepository.count()<10)
//        while (i < 15) {
//            Faker faker =Faker.instance();
//            int ramdomN = faker.number().numberBetween(0, 1);
//            String sex;
//            if (ramdomN == 0) {
//                sex = "Homme";
//            } else {
//                sex = "Femme";
//            }
//            int ramdomMiseEnVeille = faker.number().numberBetween(0, 1);
//            int ramdomRegime = faker.number().numberBetween(0, 1);
//            String regim;
//            if (ramdomN == 0) {
//                regim = "société tunisienne";
//            } else {
//                regim = "société étrangère";
//            }
//            Client client = new Client(
//                    faker.date().birthday(),
//                    faker.name().title(),
//                    regim,
//                    "Textile",
//                    "Maille",
//                    faker.address().country(),
//                    faker.address().city(),
//                    "EXW",
//                    "60j",
//                    "Chèque",
//                    "+21629883656",
//                    "73598242,23658451",
//                   faker.number().toString(),
//                    "BIAT",
//                    "545644534545",
//                    "1256458454",
//                    "15415646545",
//                    faker.date().birthday(),
//                    ramdomMiseEnVeille,
//                    new ArrayList<>(),
//                    new ArrayList<>()
//                    );
//            clientRepository.save(client);
//            Personnel personnel = new Personnel(
//                    faker.name().firstName(),
//                    faker.name().lastName(),
//                    LocalDate.now(),
//                    faker.address().city(),
//                    faker.file().fileName(),
//                    "084425423"+ i,
//                    sex,
//                    "15514845454564412",
//                    "personnel",
//                    LocalDate.now(),
//                    faker.number().numberBetween(1,9),
//                    faker.name().title(),
//                    "1255"+ i,
//                    "+21629883494"+i,
//                    new Comptes(),
//                    false,
//                    faker.address().city(),
//                    "5025"+i,
//                    "TestEmail@gmail.com"
//            );
//            personnelRepository.save(personnel);
//            CommandeClient commandeClient = new CommandeClient(
//                    "Stock",
//                    "CMD02155",
//                    "Non Fermer",
//                    faker.date().birthday(),
//                    faker.date().birthday(),
//                    false,
//"En attente",
//                    client
//                    );
//            commandeClientRepository.save(commandeClient);
//            i++;
//        }
//    }
}