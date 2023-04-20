package com.housservice.housstock.mapper;

import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.dto.CommandeClientDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-20T04:06:17+0200",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class CommandMapperImpl extends CommandMapper {

    @Override
    public CommandeClientDto toCommandDto(CommandeClient commandeClient) {
        if ( commandeClient == null ) {
            return null;
        }

        CommandeClientDto commandeClientDto = new CommandeClientDto();

        commandeClientDto.setId( commandeClient.getId() );
        commandeClientDto.setTypeCmd( commandeClient.getTypeCmd() );
        commandeClientDto.setNumCmd( commandeClient.getNumCmd() );
        commandeClientDto.setClosed( commandeClient.isClosed() );
        commandeClientDto.setEtatProduction( commandeClient.getEtatProduction() );
        commandeClientDto.setDateCmd( commandeClient.getDateCmd() );
        commandeClientDto.setDateCreationCmd( commandeClient.getDateCreationCmd() );
        List<LigneCommandeClient> list = commandeClient.getLigneCommandeClient();
        if ( list != null ) {
            commandeClientDto.setLigneCommandeClient( new ArrayList<LigneCommandeClient>( list ) );
        }
        commandeClientDto.setHaveLc( commandeClient.getHaveLc() );

        updateCommandDto( commandeClient, commandeClientDto );

        return commandeClientDto;
    }

    @Override
    public CommandeClient toCommand(CommandeClientDto commandeClientDto) {
        if ( commandeClientDto == null ) {
            return null;
        }

        CommandeClient commandeClient = new CommandeClient();

        commandeClient.setId( commandeClientDto.getId() );
        commandeClient.setTypeCmd( commandeClientDto.getTypeCmd() );
        commandeClient.setNumCmd( commandeClientDto.getNumCmd() );
        commandeClient.setClosed( commandeClientDto.isClosed() );
        commandeClient.setDateCmd( commandeClientDto.getDateCmd() );
        commandeClient.setDateCreationCmd( commandeClientDto.getDateCreationCmd() );
        commandeClient.setHaveLc( commandeClientDto.getHaveLc() );
        commandeClient.setEtatProduction( commandeClientDto.getEtatProduction() );
        List<LigneCommandeClient> list = commandeClientDto.getLigneCommandeClient();
        if ( list != null ) {
            commandeClient.setLigneCommandeClient( new ArrayList<LigneCommandeClient>( list ) );
        }

        updateCommand( commandeClientDto, commandeClient );

        return commandeClient;
    }
}
