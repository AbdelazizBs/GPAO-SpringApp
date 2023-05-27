package com.housservice.housstock.mapper;

import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.PersonnelDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-26T09:00:50+0200",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class PersonnelMapperImpl extends PersonnelMapper {

    @Override
    public PersonnelDto toPersonnelDto(Personnel personnel) {
        if ( personnel == null ) {
            return null;
        }

        PersonnelDto personnelDto = new PersonnelDto();

        personnelDto.setId( personnel.getId() );
        personnelDto.setNom( personnel.getNom() );
        personnelDto.setAdresse( personnel.getAdresse() );
        personnelDto.setPays( personnel.getPays() );
        personnelDto.setPhoto( personnel.getPhoto() );
        personnelDto.setVille( personnel.getVille() );
        personnelDto.setRegion( personnel.getRegion() );
        personnelDto.setCodePostal( personnel.getCodePostal() );
        personnelDto.setCin( personnel.getCin() );
        personnelDto.setEmail( personnel.getEmail() );
        personnelDto.setSexe( personnel.getSexe() );
        personnelDto.setRib( personnel.getRib() );
        personnelDto.setPoste( personnel.getPoste() );
        personnelDto.setDateEmbauche( personnel.getDateEmbauche() );
        personnelDto.setDateNaissance( personnel.getDateNaissance() );
        personnelDto.setEchelon( personnel.getEchelon() );
        personnelDto.setNumCnss( personnel.getNumCnss() );
        personnelDto.setSituationFamiliale( personnel.getSituationFamiliale() );
        personnelDto.setNbrEnfant( personnel.getNbrEnfant() );
        personnelDto.setTypeContrat( personnel.getTypeContrat() );
        personnelDto.setMatricule( personnel.getMatricule() );
        personnelDto.setPhone( personnel.getPhone() );
        personnelDto.setCategorie( personnel.getCategorie() );
        personnelDto.setCompte( personnel.getCompte() );
        personnelDto.setMiseEnVeille( personnel.isMiseEnVeille() );

        updatePersonnelDto( personnel, personnelDto );

        return personnelDto;
    }

    @Override
    public Personnel toPersonnel(PersonnelDto personnelDto) {
        if ( personnelDto == null ) {
            return null;
        }

        Personnel personnel = new Personnel();

        personnel.setId( personnelDto.getId() );
        personnel.setNom( personnelDto.getNom() );
        personnel.setCodePostal( personnelDto.getCodePostal() );
        personnel.setAdresse( personnelDto.getAdresse() );
        personnel.setPhoto( personnelDto.getPhoto() );
        personnel.setMatricule( personnelDto.getMatricule() );
        personnel.setCin( personnelDto.getCin() );
        personnel.setSexe( personnelDto.getSexe() );
        personnel.setVille( personnelDto.getVille() );
        personnel.setRegion( personnelDto.getRegion() );
        personnel.setPays( personnelDto.getPays() );
        personnel.setRib( personnelDto.getRib() );
        personnel.setPoste( personnelDto.getPoste() );
        personnel.setPhone( personnelDto.getPhone() );
        personnel.setDateEmbauche( personnelDto.getDateEmbauche() );
        personnel.setDateNaissance( personnelDto.getDateNaissance() );
        personnel.setEchelon( personnelDto.getEchelon() );
        personnel.setNumCnss( personnelDto.getNumCnss() );
        personnel.setSituationFamiliale( personnelDto.getSituationFamiliale() );
        personnel.setNbrEnfant( personnelDto.getNbrEnfant() );
        personnel.setTypeContrat( personnelDto.getTypeContrat() );
        personnel.setCategorie( personnelDto.getCategorie() );
        personnel.setEmail( personnelDto.getEmail() );
        personnel.setCompte( personnelDto.getCompte() );
        personnel.setMiseEnVeille( personnelDto.isMiseEnVeille() );

        updatePersonnel( personnelDto, personnel );

        return personnel;
    }
}
