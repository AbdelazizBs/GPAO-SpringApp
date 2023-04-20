package com.housservice.housstock.mapper;

import com.housservice.housstock.model.Produit;
import com.housservice.housstock.model.dto.ProduitDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public abstract class ProduitMapper {



        public static com.housservice.housstock.mapper.ProduitMapper MAPPER = Mappers.getMapper(com.housservice.housstock.mapper.ProduitMapper.class);


        public abstract ProduitDto toProduitDto(Produit produit);

        public abstract Produit toProduit(ProduitDto  produitDto);



        @AfterMapping
        void updateProduitDto(final Produit produit, @MappingTarget final ProduitDto produitDto)   {

        }

        @AfterMapping
        void updateProduit(final ProduitDto  produitDto, @MappingTarget final Produit produit) {

        }
}
