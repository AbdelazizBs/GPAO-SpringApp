package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.dto.NomenclatureDto;
import com.housservice.housstock.repository.NomenclatureRepository;
import com.mongodb.client.result.UpdateResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NomenclatureServiceImpl implements NomenclatureService {

	private NomenclatureRepository nomenclatureRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private MongoOperations mongoOperations;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;

	
	@Autowired
	public NomenclatureServiceImpl(NomenclatureRepository nomenclatureRepository, SequenceGeneratorService sequenceGeneratorService,  MongoOperations mongoOperations,
									MessageHttpErrorProperties messageHttpErrorProperties) {
		this.nomenclatureRepository = nomenclatureRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.mongoOperations = mongoOperations;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	}
	
	@Override
	public List<NomenclatureDto> getAllFamily(String idCompte, String typeFamille, String idParent) {
		List<Nomenclature> listNomenclatures = nomenclatureRepository.findByIdCompteAndTypeAndIdParent(idCompte, Nomenclature.TYPE_FAMILLE, "").orElse(new ArrayList<>());
		
		return listNomenclatures.stream()
				.map(nomenclature -> buildNomenclatureDtoFromNomenlcature(nomenclature))
				.filter(nomenclatur -> nomenclatur != null)
				.collect(Collectors.toList());
			
	}
	
	@Override
	public NomenclatureDto buildNomenclatureDtoFromNomenlcature(Nomenclature nomenclature) {
		if (nomenclature == null) {
			return null;
		}
		NomenclatureDto nomenclatureDto = new NomenclatureDto();
		nomenclatureDto.setId(nomenclature.getId());
		nomenclatureDto.setNom(nomenclature.getNom());
		nomenclatureDto.setDescription(nomenclature.getDescription());
		nomenclatureDto.setIdParent(nomenclature.getIdParent());
		if (nomenclature.getListIdChildren() != null) {
			List<NomenclatureDto> listNomenclatureDtoChild = nomenclature.getListIdChildren().stream()
																		.map(nomenclatureId -> buildNomenclatureDtoFromNomenlcature(getNomenclatureById(nomenclatureId)))
																		.filter(nomenclatur -> nomenclatur != null)
																		.collect(Collectors.toList());
			
			nomenclatureDto.setListNomenclatureChildren(listNomenclatureDtoChild);
		}
		
		return nomenclatureDto;
	}
	
	private NomenclatureDto buildNomenclatureDtoFromNomenlcature(Optional<Nomenclature> nomenclatureOp) {
		if (nomenclatureOp.isPresent()) {
			return buildNomenclatureDtoFromNomenlcature(nomenclatureOp.get());
		}
		return null;
	}
	
	@Override
	public Optional<Nomenclature> getNomenclatureById(String nomenclatureId) {
		return nomenclatureRepository.findById(nomenclatureId);
	}
	
	@Override
	public void deleteNomenclature(Nomenclature nomenclature) {
		nomenclatureRepository.delete(nomenclature);
	}
	
	@Override
	public void createNewNomenclature(@Valid NomenclatureDto nomenclatureDto) {
		
		Nomenclature nomenclature = nomenclatureRepository.save(buildNomenclatureFromNomenlcatureDto(nomenclatureDto));
		if (StringUtils.isNotBlank(nomenclature.getIdParent())) {
			addIdNomenclatureToPere(nomenclature.getId(), nomenclature.getIdParent());
			
		}
		
	}

	private Nomenclature buildNomenclatureFromNomenlcatureDto(NomenclatureDto nomenclatureDto) {
		Nomenclature nomenclature = new Nomenclature();
		nomenclature.setId("" + sequenceGeneratorService.generateSequence(Nomenclature.SEQUENCE_NAME));
		nomenclature.setNom(nomenclatureDto.getNom());
		nomenclature.setDescription(nomenclatureDto.getDescription());
		nomenclature.setIdCompte(nomenclatureDto.getIdCompte());
		nomenclature.setType(nomenclatureDto.getType());
		nomenclature.setIdParent(nomenclatureDto.getIdParent());
		
		return nomenclature;
	}

	@Override
	public void updateNomenclature(@Valid NomenclatureDto nomenclatureDto) throws ResourceNotFoundException {
		Nomenclature nomenclature = getNomenclatureById(nomenclatureDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  nomenclatureDto.getId())));
		
		nomenclature.setNom(nomenclatureDto.getNom());
		nomenclature.setDescription(nomenclatureDto.getDescription());
		nomenclature.setType(nomenclatureDto.getType());
		if (!StringUtils.equals(nomenclature.getIdParent(), nomenclatureDto.getIdParent())) {
			deleteidNomenclatureFromPere(nomenclature.getId(), nomenclature.getIdParent());
			addIdNomenclatureToPere(nomenclature.getId(), nomenclatureDto.getIdParent());
		}
		nomenclatureRepository.save(nomenclature);
	}


	private void addIdNomenclatureToPere(String nomenclatureId, String parentId)
	{
		Update update = new Update().addToSet("listIdChildren", nomenclatureId);
		final Query query = new Query(new Criteria().andOperator(Criteria.where("_id").is(parentId)));
		UpdateResult updateResult = mongoOperations.updateFirst(query, update, Nomenclature.class);
		 
		log.info("la mise à jour de pere a été effectué : " + (updateResult.getModifiedCount() > 0 ? "OK":"KO"));
		log.debug("nombre des document modifier : " + updateResult.getModifiedCount());
		
	}
	
	private void deleteidNomenclatureFromPere(String nomenclatureId, String parentId) {
		Update update = new Update().pull("listIdChildren", nomenclatureId);
		final Query query = new Query(new Criteria().andOperator(Criteria.where("_id").is(parentId)));
		UpdateResult updateResult = mongoOperations.updateFirst(query, update, Nomenclature.class);
		 
		log.info("la mise à jour de pere a été effectué : " + (updateResult.getModifiedCount() > 0 ? "OK":"KO"));
		log.debug("nombre des document modifier : " + updateResult.getModifiedCount());
	}

	@Override
	public List<NomenclatureDto> findFamilyNomenclature(String recherche) {
		List<Nomenclature> listNomenclatures =  nomenclatureRepository.findByNomLikeOrDescriptionLikeAndTypeAllIgnoreCase(recherche, recherche, Nomenclature.TYPE_FAMILLE).orElse(new ArrayList<Nomenclature>());
		return listNomenclatures.stream()
				.map(nomenclature -> buildNomenclatureDtoFromNomenlcature(nomenclature))
				.filter(nomenclatur -> nomenclatur != null)
				.collect(Collectors.toList());
	}

}
