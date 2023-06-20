package com.housservice.housstock.mapper;

import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.dto.NomenclatureDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-20T05:15:49+0200",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class NomenclatureMapperImpl extends NomenclatureMapper {

    @Override
    public NomenclatureDto toNomenclatureDto(Nomenclature nomenclature) {
        if ( nomenclature == null ) {
            return null;
        }

        NomenclatureDto nomenclatureDto = new NomenclatureDto();

        nomenclatureDto.setId( nomenclature.getId() );
        nomenclatureDto.setNomNomenclature( nomenclature.getNomNomenclature() );
        List<String> list = nomenclature.getClientId();
        if ( list != null ) {
            nomenclatureDto.setClientId( new ArrayList<String>( list ) );
        }
        List<String> list1 = nomenclature.getFournisseurId();
        if ( list1 != null ) {
            nomenclatureDto.setFournisseurId( new ArrayList<String>( list1 ) );
        }
        nomenclatureDto.setDescription( nomenclature.getDescription() );
        nomenclatureDto.setType( nomenclature.getType() );
        nomenclatureDto.setNature( nomenclature.getNature() );
        List<String> list2 = nomenclature.getChildrensId();
        if ( list2 != null ) {
            nomenclatureDto.setChildrensId( new ArrayList<String>( list2 ) );
        }
        List<String> list3 = nomenclature.getParentsName();
        if ( list3 != null ) {
            nomenclatureDto.setParentsName( new ArrayList<String>( list3 ) );
        }
        List<Nomenclature> list4 = nomenclature.getChildrens();
        if ( list4 != null ) {
            nomenclatureDto.setChildrens( new ArrayList<Nomenclature>( list4 ) );
        }
        List<String> list5 = nomenclature.getChildrensName();
        if ( list5 != null ) {
            nomenclatureDto.setChildrensName( new ArrayList<String>( list5 ) );
        }
        List<String> list6 = nomenclature.getParentsId();
        if ( list6 != null ) {
            nomenclatureDto.setParentsId( new ArrayList<String>( list6 ) );
        }
        nomenclatureDto.setPrice( nomenclature.getPrice() );
        nomenclatureDto.setQuantityMin( nomenclature.getQuantityMin() );
        nomenclatureDto.setQuantityMax( nomenclature.getQuantityMax() );
        nomenclatureDto.setQuantity( nomenclature.getQuantity() );
        nomenclatureDto.setQuantityStock( nomenclature.getQuantityStock() );
        List<EtapeProduction> list7 = nomenclature.getEtapeProductions();
        if ( list7 != null ) {
            nomenclatureDto.setEtapeProductions( new ArrayList<EtapeProduction>( list7 ) );
        }
        nomenclatureDto.setCategorie( nomenclature.getCategorie() );
        nomenclatureDto.setRefIris( nomenclature.getRefIris() );
        nomenclatureDto.setPicture( nomenclature.getPicture() );
        nomenclatureDto.setDate( nomenclature.getDate() );
        nomenclatureDto.setMiseEnVeille( nomenclature.isMiseEnVeille() );
        nomenclatureDto.setDateMiseEnVeille( nomenclature.getDateMiseEnVeille() );

        updateNomenclatureDto( nomenclature );

        return nomenclatureDto;
    }

    @Override
    public Nomenclature toNomenclature(NomenclatureDto nomenclatureDto) {
        if ( nomenclatureDto == null ) {
            return null;
        }

        Nomenclature nomenclature = new Nomenclature();

        nomenclature.setId( nomenclatureDto.getId() );
        nomenclature.setNomNomenclature( nomenclatureDto.getNomNomenclature() );
        nomenclature.setDescription( nomenclatureDto.getDescription() );
        nomenclature.setRefIris( nomenclatureDto.getRefIris() );
        nomenclature.setType( nomenclatureDto.getType() );
        nomenclature.setNature( nomenclatureDto.getNature() );
        nomenclature.setCategorie( nomenclatureDto.getCategorie() );
        List<String> list = nomenclatureDto.getClientId();
        if ( list != null ) {
            nomenclature.setClientId( new ArrayList<String>( list ) );
        }
        List<String> list1 = nomenclatureDto.getFournisseurId();
        if ( list1 != null ) {
            nomenclature.setFournisseurId( new ArrayList<String>( list1 ) );
        }
        List<String> list2 = nomenclatureDto.getChildrensId();
        if ( list2 != null ) {
            nomenclature.setChildrensId( new ArrayList<String>( list2 ) );
        }
        List<String> list3 = nomenclatureDto.getParentsName();
        if ( list3 != null ) {
            nomenclature.setParentsName( new ArrayList<String>( list3 ) );
        }
        List<String> list4 = nomenclatureDto.getChildrensName();
        if ( list4 != null ) {
            nomenclature.setChildrensName( new ArrayList<String>( list4 ) );
        }
        List<String> list5 = nomenclatureDto.getParentsId();
        if ( list5 != null ) {
            nomenclature.setParentsId( new ArrayList<String>( list5 ) );
        }
        List<Nomenclature> list6 = nomenclatureDto.getChildrens();
        if ( list6 != null ) {
            nomenclature.setChildrens( new ArrayList<Nomenclature>( list6 ) );
        }
        nomenclature.setPicture( nomenclatureDto.getPicture() );
        nomenclature.setPrice( nomenclatureDto.getPrice() );
        nomenclature.setQuantityMin( nomenclatureDto.getQuantityMin() );
        nomenclature.setQuantityMax( nomenclatureDto.getQuantityMax() );
        nomenclature.setQuantity( nomenclatureDto.getQuantity() );
        nomenclature.setQuantityStock( nomenclatureDto.getQuantityStock() );
        nomenclature.setDate( nomenclatureDto.getDate() );
        nomenclature.setMiseEnVeille( nomenclatureDto.isMiseEnVeille() );
        nomenclature.setDateMiseEnVeille( nomenclatureDto.getDateMiseEnVeille() );
        List<EtapeProduction> list7 = nomenclatureDto.getEtapeProductions();
        if ( list7 != null ) {
            nomenclature.setEtapeProductions( new ArrayList<EtapeProduction>( list7 ) );
        }

        updateNomenclature( nomenclatureDto, nomenclature );

        return nomenclature;
    }
}
