package com.housservice.housstock.mapper;

import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.dto.LigneCommandeClientDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-26T10:19:24+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
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

        updateLigneCommandClient( ligneCommandeClientDto, ligneCommandeClient );

        return ligneCommandeClient;
    }
}
