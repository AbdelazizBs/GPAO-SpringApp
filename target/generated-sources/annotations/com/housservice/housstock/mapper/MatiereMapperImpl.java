package com.housservice.housstock.mapper;

import com.housservice.housstock.model.Matiere;
import com.housservice.housstock.model.dto.MatiereDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-20T11:15:25+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class MatiereMapperImpl extends MatiereMapper {

    @Override
    public MatiereDto toMatiereDto(Matiere matiere) {
        if ( matiere == null ) {
            return null;
        }

        MatiereDto matiereDto = new MatiereDto();

        matiereDto.setId( matiere.getId() );
        matiereDto.setRefMatiereIris( matiere.getRefMatiereIris() );
        matiereDto.setDesignation( matiere.getDesignation() );
        matiereDto.setFamille( matiere.getFamille() );
        matiereDto.setUniteAchat( matiere.getUniteAchat() );
        matiereDto.setMiseEnVeille( matiere.isMiseEnVeille() );

        updateMatiereDto( matiere, matiereDto );

        return matiereDto;
    }

    @Override
    public Matiere toMatiere(MatiereDto matiereDto) {
        if ( matiereDto == null ) {
            return null;
        }

        Matiere matiere = new Matiere();

        matiere.setId( matiereDto.getId() );
        matiere.setRefMatiereIris( matiereDto.getRefMatiereIris() );
        matiere.setDesignation( matiereDto.getDesignation() );
        matiere.setFamille( matiereDto.getFamille() );
        matiere.setUniteAchat( matiereDto.getUniteAchat() );
        matiere.setMiseEnVeille( matiereDto.isMiseEnVeille() );

        updateMatiere( matiereDto, matiere );

        return matiere;
    }
}
