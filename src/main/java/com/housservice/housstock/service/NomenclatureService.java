package com.housservice.housstock.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.dto.NomenclatureDto;

public interface NomenclatureService {

	Optional<Nomenclature> getNomenclatureById(String id);
	
	NomenclatureDto buildNomenclatureDtoFromNomenlcature(Nomenclature nomenclature);

	void deleteNomenclature(Nomenclature nomenclature);

	void createNewNomenclature(@Valid NomenclatureDto nomenclatureDto);

	void updateNomenclature(@Valid NomenclatureDto nomenclatureDto) throws ResourceNotFoundException;

	List<NomenclatureDto> findFamilyNomenclatureByIdCompte(String idCompte, String recherche);
	
	List<NomenclatureDto> findNomenclatures(String idCompte, NomenclatureDto nomenclatureDto);

	List<NomenclatureDto> getNomenclatureyIdCompteIdParent(String idCompte, String idParent);

	List<NomenclatureDto> getNomenclatureyByIdCompteIdParentSorted(String idCompte, String idParent, String sortId, String sortWay);

}
