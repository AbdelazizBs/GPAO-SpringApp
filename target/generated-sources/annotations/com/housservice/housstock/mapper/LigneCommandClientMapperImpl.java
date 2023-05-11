package com.housservice.housstock.mapper;

import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.dto.LigneCommandeClientDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-11T01:06:15+0200",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class LigneCommandClientMapperImpl extends LigneCommandClientMapper {

    @Override
    public LigneCommandeClientDto toLigneCommandClientDto(LigneCommandeClient ligneCommandClient) {
        if ( ligneCommandClient == null ) {
            return null;
        }

        LigneCommandeClientDto ligneCommandeClientDto = new LigneCommandeClientDto();

        ligneCommandeClientDto.setId( ligneCommandClient.getId() );
        ligneCommandeClientDto.setQuantite( ligneCommandClient.getQuantite() );
        ligneCommandeClientDto.setIdCommandeClient( ligneCommandClient.getIdCommandeClient() );
        ligneCommandeClientDto.setNumCmdClient( ligneCommandClient.getNumCmdClient() );
        ligneCommandeClientDto.setDelai( ligneCommandClient.getDelai() );
        ligneCommandeClientDto.setLanced( ligneCommandClient.isLanced() );

        updateLigneCommandClientDto( ligneCommandClient, ligneCommandeClientDto );

        return ligneCommandeClientDto;
    }

    @Override
    public LigneCommandeClient toLigneCommandClient(LigneCommandeClientDto ligneCommandeClientDto) {
        if ( ligneCommandeClientDto == null ) {
            return null;
        }

        LigneCommandeClient ligneCommandeClient = new LigneCommandeClient();

        ligneCommandeClient.setId( ligneCommandeClientDto.getId() );
        ligneCommandeClient.setQuantite( ligneCommandeClientDto.getQuantite() );
        ligneCommandeClient.setIdCommandeClient( ligneCommandeClientDto.getIdCommandeClient() );
        ligneCommandeClient.setNumCmdClient( ligneCommandeClientDto.getNumCmdClient() );
        ligneCommandeClient.setDelai( ligneCommandeClientDto.getDelai() );
        ligneCommandeClient.setLanced( ligneCommandeClientDto.isLanced() );

        updateLigneCommandClient( ligneCommandeClientDto, ligneCommandeClient );

        return ligneCommandeClient;
    }
}
