package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Sort;
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
import com.housservice.housstock.util.ConstantHelper;
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
	public List<NomenclatureDto> getNomenclatureyIdCompteIdParent(String idCompte, String idParent) {
		if (StringUtils.equals(idParent, NomenclatureDto.ROOT_LEVEL)) {
			idParent = "";
		}
		List<Nomenclature> listNomenclatures = nomenclatureRepository.findByIdCompteAndIdParent(idCompte, idParent).orElse(new ArrayList<>());
		
		return listNomenclatures.stream()
				.map(nomenclature -> buildNomenclatureDtoFromNomenlcature(nomenclature))
				.filter(nomenclatur -> nomenclatur != null)
				.collect(Collectors.toList());
			
	}
	
	@Override
	public List<NomenclatureDto> getNomenclatureyByIdCompteIdParentSorted(String idCompte, String idParent, String sortId, String sortWay) {
		if (StringUtils.equals(idParent, NomenclatureDto.ROOT_LEVEL)) {
			idParent = "";
		}
		if (StringUtils.isNotBlank(sortId)) {
			Sort sort = Sort.by(Sort.Direction.ASC, sortId);
			if (StringUtils.equals(sortWay, ConstantHelper.SORT_SENS_DESC)) {
				sort = Sort.by(Sort.Direction.DESC, sortId);
			}
			
			List<Nomenclature> listNomenclatures = nomenclatureRepository.findByIdCompteAndIdParent(idCompte, idParent, sort).orElse(new ArrayList<>());
			
			return listNomenclatures.stream()
					.map(nomenclature -> buildNomenclatureDtoFromNomenlcature(nomenclature))
					.filter(nomenclatur -> nomenclatur != null)
					.collect(Collectors.toList());
		}
		return getNomenclatureyIdCompteIdParent(idCompte, idParent);
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
			nomenclatureDto.setHasChildren(true);
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
		
		Nomenclature nomenclature = nomenclatureRepository.save(buildNomenclatureFromNomenlcatureDto(nomenclatureDto, true));
		if (StringUtils.isNotBlank(nomenclature.getIdParent())) {
			addIdNomenclatureToPere(nomenclature.getId(), nomenclature.getIdParent());
			
		}
		
	}

	private Nomenclature buildNomenclatureFromNomenlcatureDto(NomenclatureDto nomenclatureDto, boolean withID) {
		Nomenclature nomenclature = new Nomenclature();
		if (withID) {
			if (StringUtils.isBlank(nomenclatureDto.getId())) {
				nomenclature.setId("" + sequenceGeneratorService.generateSequence(Nomenclature.SEQUENCE_NAME));
			}else {
				nomenclature.setId(nomenclatureDto.getId());
			}
		}
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
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getErro0002(),  nomenclatureDto.getId())));
		
		nomenclature.setNom(nomenclatureDto.getNom());
		nomenclature.setDescription(nomenclatureDto.getDescription());
		nomenclature.setType(nomenclatureDto.getType());
		if (!StringUtils.equals(nomenclature.getIdParent(), nomenclatureDto.getIdParent())) {
			deleteidNomenclatureFromPere(nomenclature.getId(), nomenclature.getIdParent());
			addIdNomenclatureToPere(nomenclature.getId(), nomenclatureDto.getIdParent());
		}
		nomenclatureRepository.save(nomenclature);
	}


	private void addIdNomenclatureToPere(String nomenclatureId, String parentId) {
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
	public List<NomenclatureDto> findFamilyNomenclatureByIdCompte(String idCompte, String recherche) {
		List<Nomenclature> listNomenclatures =  nomenclatureRepository.findByIdCompteAndNomLikeOrDescriptionLikeAndTypeAllIgnoreCase(idCompte, recherche, recherche, Nomenclature.TYPE_FAMILLE).orElse(new ArrayList<Nomenclature>());
		return listNomenclatures.stream()
				.map(nomenclature -> buildNomenclatureDtoFromNomenlcature(nomenclature))
				.filter(nomenclature -> nomenclature != null)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<NomenclatureDto> findNomenclatures(String idCompte, NomenclatureDto nomenclatureDto) {
		Nomenclature nomenclatur = buildNomenclatureFromNomenlcatureDto(nomenclatureDto, false);
		nomenclatur.setIdCompte(idCompte);
		if (nomenclatur.getListIdChildren().isEmpty()) {
			nomenclatur.setListIdChildren(null);
		}
		ExampleMatcher matcher = ExampleMatcher.matchingAll()
				   .withIgnoreCase()
				   .withStringMatcher(StringMatcher.ENDING)
				   .withStringMatcher(StringMatcher.STARTING)
				   .withStringMatcher(StringMatcher.CONTAINING);
		Example<Nomenclature> exampleNomenclature = Example.of(nomenclatur, matcher);
		
				
		List<Nomenclature> listNomenclatures =  nomenclatureRepository.findAll(exampleNomenclature);
		return listNomenclatures.stream()
				.map(nomenclature -> buildNomenclatureDtoFromNomenlcature(nomenclature))
				.filter(nomenclature -> nomenclature != null)
				.collect(Collectors.toList());
	}

}
