package com.housservice.housstock.mapper;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.dto.NomenclatureDto;
import com.housservice.housstock.repository.NomenclatureRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class NomenclatureMapper {
	@Autowired
	NomenclatureRepository nomenclatureRepository;
	@Autowired
	private MessageHttpErrorProperties messageHttpErrorProperties;
	  public static NomenclatureMapper  MAPPER = Mappers.getMapper(NomenclatureMapper.class);



	@Mapping(target = "childrens", ignore = true)
	public abstract NomenclatureDto toNomenclatureDto(Nomenclature nomenclature)  ;


	@Mapping(target = "childrensId", ignore = true)
	  public abstract Nomenclature toNomenclature(NomenclatureDto  nomenclatureDto);


		@AfterMapping
	    void updateNomenclatureDto(Nomenclature nomenclature, @MappingTarget NomenclatureDto nomenclatureDto)  {
//			if (nomenclature.getChildrensId() != null && !nomenclature.getChildrensId().isEmpty()) {
//				List<Nomenclature> nomenclatureList = new ArrayList<>();
//				Nomenclature nomenclature1 =	nomenclatureRepository.findNomenclatureById(nomenclature.getId());
//					nomenclatureList.add(nomenclature1);
//					nomenclatureDto.setChildrens(nomenclatureList);
//			}
	    }




	    @AfterMapping
	    void updateNomenclature(NomenclatureDto  nomenclatureDto, @MappingTarget Nomenclature nomenclature) {

		}
	}

