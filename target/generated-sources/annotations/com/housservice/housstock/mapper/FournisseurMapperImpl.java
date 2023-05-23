package com.housservice.housstock.mapper;

import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.Picture;
import com.housservice.housstock.model.dto.FournisseurDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-22T18:21:51+0200",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class FournisseurMapperImpl extends FournisseurMapper {

    @Override
    public FournisseurDto toFournisseurDto(Fournisseur Fournisseur) {
        if ( Fournisseur == null ) {
            return null;
        }

        FournisseurDto fournisseurDto = new FournisseurDto();

        fournisseurDto.setId( Fournisseur.getId() );
        fournisseurDto.setRefFrsIris( Fournisseur.getRefFrsIris() );
        fournisseurDto.setIntitule( Fournisseur.getIntitule() );
        fournisseurDto.setAbrege( Fournisseur.getAbrege() );
        fournisseurDto.setStatut( Fournisseur.getStatut() );
        fournisseurDto.setAdresse( Fournisseur.getAdresse() );
        fournisseurDto.setCodePostal( Fournisseur.getCodePostal() );
        fournisseurDto.setVille( Fournisseur.getVille() );
        fournisseurDto.setRegion( Fournisseur.getRegion() );
        fournisseurDto.setPays( Fournisseur.getPays() );
        fournisseurDto.setTelephone( Fournisseur.getTelephone() );
        fournisseurDto.setTelecopie( Fournisseur.getTelecopie() );
        fournisseurDto.setLinkedin( Fournisseur.getLinkedin() );
        fournisseurDto.setEmail( Fournisseur.getEmail() );
        fournisseurDto.setSiteWeb( Fournisseur.getSiteWeb() );
        fournisseurDto.setNomBanque( Fournisseur.getNomBanque() );
        fournisseurDto.setAdresseBanque( Fournisseur.getAdresseBanque() );
        fournisseurDto.setRib( Fournisseur.getRib() );
        fournisseurDto.setSwift( Fournisseur.getSwift() );
        fournisseurDto.setCodeDouane( Fournisseur.getCodeDouane() );
        fournisseurDto.setRne( Fournisseur.getRne() );
        fournisseurDto.setIdentifiantTva( Fournisseur.getIdentifiantTva() );
        fournisseurDto.setMiseEnVeille( Fournisseur.getMiseEnVeille() );
        fournisseurDto.setDateMiseEnVeille( Fournisseur.getDateMiseEnVeille() );
        fournisseurDto.setDate( Fournisseur.getDate() );
        List<Contact> list = Fournisseur.getContact();
        if ( list != null ) {
            fournisseurDto.setContact( new ArrayList<Contact>( list ) );
        }
        List<Picture> list1 = Fournisseur.getPictures();
        if ( list1 != null ) {
            fournisseurDto.setPictures( new ArrayList<Picture>( list1 ) );
        }

        updateFournisseurDto( Fournisseur, fournisseurDto );

        return fournisseurDto;
    }

    @Override
    public Fournisseur toFournisseur(FournisseurDto FournisseurDto) {
        if ( FournisseurDto == null ) {
            return null;
        }

        Fournisseur fournisseur = new Fournisseur();

        fournisseur.setId( FournisseurDto.getId() );
        fournisseur.setRefFrsIris( FournisseurDto.getRefFrsIris() );
        fournisseur.setIntitule( FournisseurDto.getIntitule() );
        fournisseur.setAbrege( FournisseurDto.getAbrege() );
        fournisseur.setStatut( FournisseurDto.getStatut() );
        fournisseur.setAdresse( FournisseurDto.getAdresse() );
        fournisseur.setCodePostal( FournisseurDto.getCodePostal() );
        fournisseur.setVille( FournisseurDto.getVille() );
        fournisseur.setRegion( FournisseurDto.getRegion() );
        fournisseur.setPays( FournisseurDto.getPays() );
        fournisseur.setTelephone( FournisseurDto.getTelephone() );
        fournisseur.setTelecopie( FournisseurDto.getTelecopie() );
        fournisseur.setLinkedin( FournisseurDto.getLinkedin() );
        fournisseur.setEmail( FournisseurDto.getEmail() );
        fournisseur.setSiteWeb( FournisseurDto.getSiteWeb() );
        fournisseur.setNomBanque( FournisseurDto.getNomBanque() );
        fournisseur.setAdresseBanque( FournisseurDto.getAdresseBanque() );
        fournisseur.setRib( FournisseurDto.getRib() );
        fournisseur.setSwift( FournisseurDto.getSwift() );
        fournisseur.setCodeDouane( FournisseurDto.getCodeDouane() );
        fournisseur.setRne( FournisseurDto.getRne() );
        fournisseur.setIdentifiantTva( FournisseurDto.getIdentifiantTva() );
        fournisseur.setMiseEnVeille( FournisseurDto.getMiseEnVeille() );
        fournisseur.setDateMiseEnVeille( FournisseurDto.getDateMiseEnVeille() );
        List<Contact> list = FournisseurDto.getContact();
        if ( list != null ) {
            fournisseur.setContact( new ArrayList<Contact>( list ) );
        }
        List<Picture> list1 = FournisseurDto.getPictures();
        if ( list1 != null ) {
            fournisseur.setPictures( new ArrayList<Picture>( list1 ) );
        }
        fournisseur.setDate( FournisseurDto.getDate() );

        updateFournisseur( FournisseurDto, fournisseur );

        return fournisseur;
    }
}
