package com.housservice.housstock.repository;

import com.housservice.housstock.model.Etape;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.Personnel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MachineRepository extends MongoRepository<Machine, String> {
    boolean existsMachineByRefMachine(String refMachine);

    Page<Machine> findMachineByMiseEnVeille(boolean b, Pageable paging);
    @Query( "{$or:[{'refMachine': {$regex : ?0}} ,{'type': {$regex : ?0}},{'libelle': {$regex : ?0}},{'nomConducteur': {$regex : ?0}},{'etat': {$regex : ?0}}]}")
    Page<Machine> findMachineByTextToFind(String textToFind, Pageable pageable);


    Machine findMachineByLibelle(String refMachine);

    List<Machine> findMachineByNomConducteur(String nomConducteur);

    List<Machine> findMachineByType(String etape);

    List<Machine> findMachineByEtat(String b);

    List<Machine> findMachineByMiseEnVeille(boolean b);
}
