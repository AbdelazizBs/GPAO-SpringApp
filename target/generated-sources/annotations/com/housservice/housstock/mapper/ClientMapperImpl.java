package com.housservice.housstock.mapper;

import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.Picture;
import com.housservice.housstock.model.dto.ClientDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-18T10:10:38+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class ClientMapperImpl extends ClientMapper {

    @Override
    public ClientDto toClientDto(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientDto clientDto = new ClientDto();

        clientDto.setId( client.getId() );
        clientDto.setRefClientIris( client.getRefClientIris() );
        clientDto.setDate( client.getDate() );
        clientDto.setRaisonSocial( client.getRaisonSocial() );
        clientDto.setPays( client.getPays() );
        clientDto.setRegime( client.getRegime() );
        clientDto.setSecteurActivite( client.getSecteurActivite() );
        clientDto.setBrancheActivite( client.getBrancheActivite() );
        clientDto.setAdresse( client.getAdresse() );
        clientDto.setStatut( client.getStatut() );
        clientDto.setCodePostal( client.getCodePostal() );
        clientDto.setVille( client.getVille() );
        clientDto.setRegion( client.getRegion() );
        clientDto.setCodeDouane( client.getCodeDouane() );
        clientDto.setRne( client.getRne() );
        clientDto.setCif( client.getCif() );
        clientDto.setIncoterm( client.getIncoterm() );
        clientDto.setTelecopie( client.getTelecopie() );
        clientDto.setPhone( client.getPhone() );
        clientDto.setEcheance( client.getEcheance() );
        clientDto.setModePaiement( client.getModePaiement() );
        clientDto.setNomBanque( client.getNomBanque() );
        clientDto.setAdresseBanque( client.getAdresseBanque() );
        clientDto.setRib( client.getRib() );
        clientDto.setSwift( client.getSwift() );
        clientDto.setEmail( client.getEmail() );
        clientDto.setMiseEnVeille( client.isMiseEnVeille() );
        clientDto.setDateMiseEnVeille( client.getDateMiseEnVeille() );
        clientDto.setBlocage( client.getBlocage() );
        clientDto.setDateBlocage( client.getDateBlocage() );
        List<Contact> list = client.getContact();
        if ( list != null ) {
            clientDto.setContact( new ArrayList<Contact>( list ) );
        }
        List<Picture> list1 = client.getPictures();
        if ( list1 != null ) {
            clientDto.setPictures( new ArrayList<Picture>( list1 ) );
        }
        List<CommandeClient> list2 = client.getListCommandes();
        if ( list2 != null ) {
            clientDto.setListCommandes( new ArrayList<CommandeClient>( list2 ) );
        }

        updateClientDto( client, clientDto );

        return clientDto;
    }

    @Override
    public Client toClient(ClientDto clientDto) {
        if ( clientDto == null ) {
            return null;
        }

        Client client = new Client();

        client.setId( clientDto.getId() );
        client.setRefClientIris( clientDto.getRefClientIris() );
        client.setStatut( clientDto.getStatut() );
        client.setCodePostal( clientDto.getCodePostal() );
        client.setAdresse( clientDto.getAdresse() );
        client.setVille( clientDto.getVille() );
        client.setRegion( clientDto.getRegion() );
        client.setPays( clientDto.getPays() );
        client.setCodeDouane( clientDto.getCodeDouane() );
        client.setRne( clientDto.getRne() );
        client.setCif( clientDto.getCif() );
        client.setDate( clientDto.getDate() );
        client.setRaisonSocial( clientDto.getRaisonSocial() );
        client.setRegime( clientDto.getRegime() );
        client.setSecteurActivite( clientDto.getSecteurActivite() );
        client.setBrancheActivite( clientDto.getBrancheActivite() );
        client.setIncoterm( clientDto.getIncoterm() );
        client.setTelecopie( clientDto.getTelecopie() );
        client.setPhone( clientDto.getPhone() );
        client.setEcheance( clientDto.getEcheance() );
        client.setModePaiement( clientDto.getModePaiement() );
        client.setNomBanque( clientDto.getNomBanque() );
        client.setAdresseBanque( clientDto.getAdresseBanque() );
        client.setRib( clientDto.getRib() );
        client.setSwift( clientDto.getSwift() );
        client.setEmail( clientDto.getEmail() );
        client.setMiseEnVeille( clientDto.isMiseEnVeille() );
        client.setDateMiseEnVeille( clientDto.getDateMiseEnVeille() );
        client.setBlocage( clientDto.getBlocage() );
        client.setDateBlocage( clientDto.getDateBlocage() );
        List<Contact> list = clientDto.getContact();
        if ( list != null ) {
            client.setContact( new ArrayList<Contact>( list ) );
        }
        List<Picture> list1 = clientDto.getPictures();
        if ( list1 != null ) {
            client.setPictures( new ArrayList<Picture>( list1 ) );
        }
        List<CommandeClient> list2 = clientDto.getListCommandes();
        if ( list2 != null ) {
            client.setListCommandes( new ArrayList<CommandeClient>( list2 ) );
        }

        updateClient( clientDto, client );

        return client;
    }
}
