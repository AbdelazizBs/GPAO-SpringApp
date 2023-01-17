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

import java.util.List;

import static java.util.stream.Collectors.toList;

@Mapper(componentModel = "spring")
public abstract class NomenclatureMapper {
	@Autowired
	NomenclatureRepository nomenclatureRepository;
	@Autowired
	private  MessageHttpErrorProperties messageHttpErrorProperties;
	  public static NomenclatureMapper  MAPPER = Mappers.getMapper(NomenclatureMapper.class);



	@Mapping(target = "nomNomEnclatureFamille", ignore = true)
	public abstract NomenclatureDto toNomenclatureDto(Nomenclature nomenclature);


	@Mapping(target = "nomenclatures", ignore = true)
	  public abstract Nomenclature toNomenclature(NomenclatureDto  nomenclatureDto);


	    @AfterMapping
	    void updateNomenclatureDto(Nomenclature nomenclature, @MappingTarget NomenclatureDto nomenclatureDto)   {
			if (nomenclature.getNomenclatures() != null) {
			List<String> list = nomenclature.getNomenclatures().stream().map(Nomenclature::getNomNomenclature).collect(toList());
			nomenclatureDto.setNomNomEnclatureFamille(list);
			}
		}




	    @AfterMapping
	    void updateNomenclature(NomenclatureDto  nomenclatureDto, @MappingTarget Nomenclature nomenclature) {
//			if (nomenclatureDto.getNomNomEnclatureFamille() != null) {
//				List<Nomenclature> famille = new ArrayList<>();
//				for (String nom : nomenclatureDto.getNomNomEnclatureFamille()) {
//					Nomenclature nomenclature1 = null;
//					try {
//						nomenclature1 = nomenclatureRepository.findNomenclatureByNomNomenclature(nom).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),nom)));
//					} catch (ResourceNotFoundException e) {
//						throw new RuntimeException(e);
//					}
//					famille.add(nomenclature1);
//				}
//				nomenclature.setNomenclatures(famille);
//			}
		}
	}

