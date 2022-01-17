package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.MvtStk;
import com.housservice.housstock.model.dto.MvtStkDto;
import com.housservice.housstock.repository.ArticleRepository;
import com.housservice.housstock.repository.MvtStkRepository;

@Service
public class MvtStkServiceImpl implements MvtStkService{
	
	private MvtStkRepository MvtStkRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	private ArticleRepository articleRepository;
	
	
	@Autowired
	public MvtStkServiceImpl (MvtStkRepository MvtStkRepository,SequenceGeneratorService sequenceGeneratorService,
			MessageHttpErrorProperties messageHttpErrorProperties,ArticleRepository articleRepository)
	{
		this.MvtStkRepository = MvtStkRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.articleRepository = articleRepository;
	}

	@Override
	public MvtStkDto buildMvtStkDtoFromMvtStk(MvtStk MvtStk) {
		if (MvtStk == null)
		{
			return null;
		}
			
		MvtStkDto MvtStkDto = new MvtStkDto();
		MvtStkDto.setId(MvtStk.getId());
		MvtStkDto.setDateMvt(MvtStk.getDateMvt());
		MvtStkDto.setQuantite(MvtStk.getQuantite());
		MvtStkDto.setTypeMvt(MvtStk.getTypeMvt());
		
		MvtStkDto.setIdArticle(MvtStk.getArticle().getId());
		MvtStkDto.setDesignationArticle(MvtStk.getArticle().getDesignation());

		return MvtStkDto;
	}
	
	
	private MvtStk buildMvtStkFromMvtStkDto(MvtStkDto MvtStkDto) {
		
		MvtStk MvtStk = new MvtStk();
		MvtStk.setId(""+sequenceGeneratorService.generateSequence(MvtStk.SEQUENCE_NAME));	
		MvtStk.setDateMvt(MvtStkDto.getDateMvt());
		MvtStk.setQuantite(MvtStkDto.getQuantite());
		MvtStk.setTypeMvt(MvtStkDto.getTypeMvt());
		
		Article art = articleRepository.findById(MvtStkDto.getIdArticle()).get();
		MvtStk.setArticle(art);

		return MvtStk;
		
	}
	
	@Override
	public List<MvtStkDto> getAllMvtStk() {
		List<MvtStk> listMvtStk = MvtStkRepository.findAll();
		
		return listMvtStk.stream()
				.map(MvtStk -> buildMvtStkDtoFromMvtStk(MvtStk))
				.filter(MvtStk -> MvtStk != null)
				.collect(Collectors.toList());
	}

	@Override
	public MvtStkDto getMvtStkById(String id) {
		
		   Optional<MvtStk> MvtStkOpt = MvtStkRepository.findById(id);
			if(MvtStkOpt.isPresent()) {
				return buildMvtStkDtoFromMvtStk(MvtStkOpt.get());
			}
			return null;
	}


	@Override
	public void createNewMvtStk(@Valid MvtStkDto MvtStkDto) {
		
		MvtStkRepository.save(buildMvtStkFromMvtStkDto(MvtStkDto));
		
	}

	@Override
	public void updateMvtStk(@Valid MvtStkDto MvtStkDto) throws ResourceNotFoundException {
		
		MvtStk MvtStk = MvtStkRepository.findById(MvtStkDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), MvtStkDto.getId())));
		
		MvtStk.setDateMvt(MvtStkDto.getDateMvt());
		MvtStk.setQuantite(MvtStkDto.getQuantite());
		MvtStk.setTypeMvt(MvtStkDto.getTypeMvt());
	
		if(MvtStk.getArticle() == null || !StringUtils.equals(MvtStkDto.getIdArticle(), MvtStk.getArticle().getId())) 
		{
			Article article = articleRepository.findById(MvtStkDto.getIdArticle()).get();
			MvtStk.setArticle(article);
		}
			
		MvtStkRepository.save(MvtStk);		
	}

	@Override
	public void deleteMvtStk(String MvtStkId) {
		
		MvtStkRepository.deleteById(MvtStkId);
		
	}

}
