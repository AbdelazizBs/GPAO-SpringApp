package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.NomenclatureMapper;
import com.housservice.housstock.model.*;
import com.housservice.housstock.model.dto.NomenclatureDto;
import com.housservice.housstock.model.enums.TypeNomEnClature;
import com.housservice.housstock.repository.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@Service
public class NomenclatureServiceImpl implements NomenclatureService {

    private final NomenclatureRepository nomenclatureRepository;

    final PictureRepository pictureRepository;

    private final MessageHttpErrorProperties messageHttpErrorProperties;
    private final ClientRepository clientRepository;
    private final FournisseurRepository fournisseurRepository;
    private final EtapeProductionRepository etapeProductionRepository;


    @Autowired
    public NomenclatureServiceImpl(NomenclatureRepository nomenclatureRepository, SequenceGeneratorService sequenceGeneratorService,
                                   MessageHttpErrorProperties messageHttpErrorProperties, PictureRepository pictureRepository,
                                   ClientRepository clientRepository,
                                   FournisseurRepository fournisseurRepository, EtapeProductionRepository etapeProductionRepository) {
        this.nomenclatureRepository = nomenclatureRepository;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.pictureRepository = pictureRepository;
        this.clientRepository = clientRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.etapeProductionRepository = etapeProductionRepository;
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException ignored) {
        }
        return outputStream.toByteArray();
    }


    @Override
    public ResponseEntity<Map<String, Object>> getFamilleNomenclature(int page, int size) {
        try {
            List<Nomenclature> nomenclatures;
            Pageable paging = PageRequest.of(page, size);
            Page<Nomenclature> pageTuts;
            pageTuts = nomenclatureRepository.findNomenclatureActif(paging);
            Map<String, Object> response = new HashMap<>();
            nomenclatures = pageTuts.getContent().stream().filter(nomenclature -> nomenclature.getParentsId().isEmpty())
                    .collect(Collectors.toList());
            nomenclatures = nomenclatures.stream().map(nomenclature -> {
                nomenclature.getChildrensId().stream().map(childrenId -> {
                    Nomenclature nomenclatureOptional;
                    try {
                        nomenclatureOptional = nomenclatureRepository.findById(childrenId)
                                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), childrenId)));
                        nomenclature.getChildrens().add(nomenclatureOptional);
                        nomenclatureOptional.getChildrensId().stream().map(childrenId2 -> {
                            Nomenclature nomenclatureOptional2;
                            try {
                                nomenclatureOptional2 = nomenclatureRepository.findById(childrenId2)
                                        .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), childrenId2)));
                                nomenclatureOptional.getChildrens().add(nomenclatureOptional2);
                                nomenclatureOptional2.getChildrensId().stream().map(childrenId3 -> {
                                    Nomenclature nomenclatureOptional3;
                                    try {
                                        nomenclatureOptional3 = nomenclatureRepository.findById(childrenId3)
                                                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), childrenId3)));
                                        nomenclatureOptional2.getChildrens().add(nomenclatureOptional3);

                                    } catch (ResourceNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                    return nomenclatureOptional3;
                                }).collect(Collectors.toList());

                            } catch (ResourceNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            return nomenclatureOptional2;
                        }).collect(Collectors.toList());
                    } catch (ResourceNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    return nomenclature;
                }).collect(Collectors.toList());
                return nomenclature;
            }).collect(Collectors.toList());
            response.put("nomenclatures", nomenclatures);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllNomenClatures(int page, int size) {

        try {
            List<NomenclatureDto> nomenclatures;
            Pageable paging = PageRequest.of(page, size);
            Page<Nomenclature> pageTuts;
            pageTuts = nomenclatureRepository.findAll(paging);
            nomenclatures = pageTuts.getContent().stream().map(nomenclature ->
                    NomenclatureMapper.MAPPER.toNomenclatureDto(nomenclature)).collect(Collectors.toList());
            nomenclatures = nomenclatures.stream().filter(nomenclatureDto -> !nomenclatureDto.isMiseEnVeille()).collect(Collectors.toList());
//            nomenclatures.stream().forEach(nomenclatureDto -> convertToDateViaInstant(nomenclatureDto.getDurationOfFabrication()));
            Map<String, Object> response = new HashMap<>();
            response.put("nomenclatures", nomenclatures);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>> getRow(List<String> childrenIds) {

        try {
            List<Nomenclature> nomenclatures = null;
            Page<Nomenclature> pageTuts = null;
            Map<String, Object> response = new HashMap<>();

            // get  nomenclature childrens
            nomenclatures = childrenIds.stream().map(childrenId -> {
                try {
                    return nomenclatureRepository.findById(childrenId).orElseThrow(()
                            -> new
                            ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), childrenId)));
                } catch (ResourceNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());

            // set children liste to parent from childensId
            nomenclatures = nomenclatures.stream().map(nomenclature -> {
                nomenclature.getChildrensId().stream().map(childrenId -> {
                    Optional<Nomenclature> nomenclatureOptional = nomenclatureRepository.findById(childrenId);
                    nomenclatureOptional.ifPresent(value -> nomenclature.getChildrens().add(value));
                    return nomenclature;
                }).collect(Collectors.toList());
                return nomenclature;
            }).collect(Collectors.toList());
            response.put("nomenclatures", nomenclatures);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>> findNomenclatureNonActive(int page, int size) {
        try {
            List<NomenclatureDto> nomenclatures;
            Pageable paging = PageRequest.of(page, size);
            Page<Nomenclature> pageTuts;
            pageTuts = nomenclatureRepository.findAll(paging);
            nomenclatures = pageTuts.getContent().stream().map(nomenclature ->
                    NomenclatureMapper.MAPPER.toNomenclatureDto(nomenclature)).collect(Collectors.toList());
            nomenclatures = nomenclatures.stream().filter(nomenclatureDto -> nomenclatureDto.isMiseEnVeille()).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("nomenclatures", nomenclatures);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public List<String> getNomNomenclatures() {
        List<Nomenclature> nomenclatures = nomenclatureRepository.findAll();
        return nomenclatures.stream()
                .map(Nomenclature::getNomNomenclature)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getIdAndRefIrisOfNomenclature(String nameNomenclature) throws ResourceNotFoundException {
        Nomenclature nomenclature = nomenclatureRepository.findNomenclatureByNomNomenclature(nameNomenclature).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), nameNomenclature)));
        ArrayList<String> refIrisAndClient = new ArrayList<>();
        refIrisAndClient.add(nomenclature.getId());
        refIrisAndClient.add(nomenclature.getRefIris());
        return refIrisAndClient;
    }

    @Override
    public List<String> getNameOfNomenclatureOfClient(String idClient) throws ResourceNotFoundException {
        Client client = clientRepository.findById(idClient).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idClient)));
        List<Nomenclature> nomenclatureList = nomenclatureRepository.findNomenclaturesByClientId(client.getId());
        return nomenclatureList.stream()
                .map(Nomenclature::getNomNomenclature)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Nomenclature> getNomenclatureById(String id) {
        return nomenclatureRepository.findById(id);
    }

    @Override
    public void createNewNomenclature(String nomNomenclature,
                                      List<String> parentsName,
                                      List<String> childrensName,
                                      String description,
                                      String refIris,
                                      String type,
                                      String nature,
                                      String categorie,
                                      Date durationOfFabrication,
                                      int quantity,
                                      int quantityMax,
                                      int quantityMin,
                                      List<String> etapeProductions,
                                      MultipartFile[] image) throws ResourceNotFoundException, IOException {
        if (nomenclatureRepository.existsNomenclatureByNomNomenclature(nomNomenclature)) {
            throw new IllegalArgumentException("Nom nomenclature existe déja !!");
        }
        Nomenclature nomenclature = new Nomenclature();
        nomenclature.setEtapeProductions(new ArrayList<>());
        for (String etape : etapeProductions) {
            EtapeProduction etapeProduction = etapeProductionRepository.findByNomEtape(etape).orElseThrow(() ->
                    new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), etape)));
            nomenclature.getEtapeProductions().add(etapeProduction);
        }
        nomenclature.setType(TypeNomEnClature.valueOf(type));
        nomenclature.setMiseEnVeille(false);
        nomenclature.setNomNomenclature(nomNomenclature);
        nomenclature.setDescription(description);
        nomenclature.setNature(nature);
        nomenclature.setCategorie(categorie);
        nomenclature.setParentsId(new ArrayList<>());
        nomenclature.setChildrensId(new ArrayList<>());
        nomenclature.setChildrens(new ArrayList<>());
        nomenclature.setRefIris(refIris);
        nomenclature.setChildrensName(childrensName);
        nomenclature.setParentsName(parentsName);
        nomenclature.setClientId(new ArrayList<>());
        nomenclature.setFournisseurId(new ArrayList<>());
        nomenclature.setQuantity(0);
        nomenclature.setPrice(0);
        nomenclature.setQuantityMin(0);
        nomenclature.setQuantityMax(0);
        nomenclature.setDurationOfFabrication(durationOfFabrication);
        nomenclature.setQuantity(quantity);
        nomenclature.setQuantityMax(quantityMax);
        nomenclature.setQuantityMin(quantityMin);
        nomenclature.setId(ObjectId.get().toString());

        Picture picture = new Picture();
        if (image.length == 0) {
            getEmptyPicture(nomenclature, picture);
        }
        setPictureToNomenclature(image, nomenclature, picture);
        if (parentsName != null) {
            affectParents(parentsName, nomenclature);
        }
        if (childrensName != null) {
            affectChildrens(childrensName, nomenclature);
        }
        nomenclature.setParentsName(parentsName);
        nomenclature.setChildrensName(childrensName);
        nomenclatureRepository.save(nomenclature);
    }

    private void affectParents(List<String> parentsName, Nomenclature nomenclature) {
        List<String> parentsIdForParent = new ArrayList<>();
        List<String> parentsNameForParent = new ArrayList<>();
        List<String> childrensNameListForParent = new ArrayList<>();
        List<String> childrensIdForParent = new ArrayList<>();
        for (String parentName : parentsName) {
            try {
                Nomenclature parent = nomenclatureRepository.findNomenclatureByNomNomenclature(parentName).orElseThrow(() ->
                        new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), parentName)));
                childrensIdForParent.addAll(parent.getChildrensId());
                childrensIdForParent.add(nomenclature.getId());
                childrensNameListForParent.addAll(parent.getChildrensName());
                childrensNameListForParent.add(nomenclature.getNomNomenclature());
                parent.setChildrensName(childrensNameListForParent);
                parent.setChildrensId(childrensIdForParent);
                nomenclatureRepository.save(parent);
                parentsNameForParent.add(parent.getNomNomenclature());
                parentsIdForParent.add(parent.getId());
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        nomenclature.setParentsId(parentsIdForParent);
        nomenclature.setParentsName(parentsNameForParent);
        nomenclatureRepository.save(nomenclature);
    }

    @Override
    public void updateNomenclature(String idNomenclature,
                                   String nomNomenclature,
                                   String description,
                                   String refIris,
                                   String type,
                                   String nature,
                                   String categorie,
                                   List<String> parentsName,
                                   List<String> childrensName,
                                   Date durationOfFabrication,
                                   int quantity,
                                   int quantityMax,
                                   int quantityMin,
                                   List<String> etapeProductions,
                                   MultipartFile[] image) throws ResourceNotFoundException {

        if (isEmpty(nomNomenclature, type, categorie)) {
            throw new IllegalArgumentException("Veuillez remplir tous les champs obligatoires !!");
        }
        Nomenclature nomenclature = getNomenclatureById(idNomenclature)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idNomenclature)));
        removechildrensAndParentParamsFromAllNomenclaturesAfterUpdate(nomenclature);
        nomenclature.setType(TypeNomEnClature.valueOf(type));
        nomenclature.setDate(nomenclature.getDate());
        nomenclature.setDateMiseEnVeille(nomenclature.getDateMiseEnVeille());
        nomenclature.setDescription(description);
        nomenclature.setNature(nature);
        nomenclature.setCategorie(categorie);
        nomenclature.setRefIris(refIris);
        setPictureToNomenclature(image, nomenclature, nomenclature.getPicture());
        nomenclature.setParentsName(parentsName);
        nomenclature.setChildrensName(childrensName);
        nomenclature.setNomNomenclature(nomNomenclature);
        nomenclature.setDurationOfFabrication(durationOfFabrication);
        nomenclature.setQuantity(quantity);
        nomenclature.setQuantityMax(quantityMax);
        nomenclature.setQuantityMin(quantityMin);
        nomenclature.setEtapeProductions(new ArrayList<>());
        allAffectationMethod(parentsName, childrensName, nomenclature);
        for (String etape : etapeProductions) {
            EtapeProduction etapeProduction = etapeProductionRepository.findByNomEtape(etape).orElseThrow(() ->
                    new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), etape)));
            nomenclature.getEtapeProductions().add(etapeProduction);
        }
        nomenclatureRepository.save(nomenclature);
    }

    private void allAffectationMethod(List<String> parentsName, List<String> childrensName, Nomenclature nomenclature) {
        updateAffectationParents(parentsName, nomenclature);
        updateAffectationChildrens(childrensName, nomenclature);
    }

    @Override
    public void affectClientAndFrsToNomenclature(String idNomenclature, List<String> raisonSoClient, List<String> intituleFrs) throws ResourceNotFoundException {
        Nomenclature nomenclature = getNomenclatureById(idNomenclature)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idNomenclature)));
        nomenclature.setClientId(new ArrayList<>());
        nomenclature.setFournisseurId(new ArrayList<>());
        if (!raisonSoClient.isEmpty()) {
            affectationClientsIds(raisonSoClient, nomenclature);
        }
        if (!intituleFrs.isEmpty()) {
            setFrsIds(intituleFrs, nomenclature);
        }
        nomenclatureRepository.save(nomenclature);
    }

    private void removechildrensAndParentParamsFromAllNomenclaturesAfterUpdate(Nomenclature nomenclature) {
        nomenclature.getParentsId().forEach(id -> {
            Nomenclature parent = null;
            try {
                parent = nomenclatureRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
            parent.getChildrensId().removeIf(childId -> childId.equals(nomenclature.getId()));
            parent.getChildrensName().removeIf(childName -> childName.equals(nomenclature.getNomNomenclature()));
            nomenclatureRepository.save(parent);
        });
        nomenclature.getChildrensId().forEach(id -> {
            Nomenclature child = null;
            try {
                child = nomenclatureRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
            child.getParentsId().removeIf(parentId -> parentId.equals(nomenclature.getId()));
            child.getParentsName().removeIf(parentName -> parentName.equals(nomenclature.getNomNomenclature()));
            nomenclatureRepository.save(child);
        });
    }

    private static boolean isEmpty(String nomNomenclature, String type, String categorie) {
        return nomNomenclature.isEmpty() || type.isEmpty() || categorie.isEmpty();
    }

    private void updateAffectationChildrens(List<String> parentsName, Nomenclature nomenclature) {
        List<String> childrensIdForNewNomenclature = new ArrayList<>();
        List<String> childrensNameForNewNomenclature = new ArrayList<>();
        List<String> parentsIdIdList = new ArrayList<>();
        List<String> parentsNameList = new ArrayList<>();
        for (String parentName : parentsName) {
            try {
                Nomenclature child = nomenclatureRepository.findNomenclatureByNomNomenclature(parentName).orElseThrow(() ->
                        new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), parentName)));
                parentsIdIdList.addAll(child.getParentsId());

                parentsIdIdList.add(nomenclature.getId());
                parentsNameList.addAll(child.getParentsName());
                parentsNameList.add(nomenclature.getNomNomenclature());
                child.setParentsId(parentsIdIdList);
                child.setParentsName(parentsNameList);
                nomenclatureRepository.save(child);
                childrensIdForNewNomenclature.add(child.getId());
                childrensNameForNewNomenclature.add(child.getNomNomenclature());
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        nomenclature.setChildrensId(childrensIdForNewNomenclature);
        nomenclature.setChildrensName(childrensNameForNewNomenclature);
    }

    private void updateAffectationParents(List<String> parentsName, Nomenclature nomenclature) {
        List<String> parentsIdForNewNomenclature = new ArrayList<>();
        List<String> parentsNameForNewNomenclature = new ArrayList<>();
        List<String> childrensIdList = new ArrayList<>();
        List<String> childrensNameList = new ArrayList<>();
        for (String parentName : parentsName) {
            try {
                Nomenclature parent = nomenclatureRepository.findNomenclatureByNomNomenclature(parentName).orElseThrow(() ->
                        new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), parentName)));
                childrensIdList.addAll(parent.getChildrensId());
                childrensIdList.add(nomenclature.getId());
                childrensNameList.addAll(parent.getChildrensName());
                childrensNameList.add(nomenclature.getNomNomenclature());
                parent.setChildrensId(childrensIdList);
                parent.setChildrensName(childrensNameList);
                nomenclatureRepository.save(parent);
                parentsIdForNewNomenclature.add(parent.getId());
                parentsNameForNewNomenclature.add(parent.getNomNomenclature());
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        nomenclature.setParentsId(parentsIdForNewNomenclature);
        nomenclature.setParentsName(parentsNameForNewNomenclature);
    }

    private void affectChildrens(List<String> childrensName, Nomenclature nomenclature) {
        List<String> parentsNameListForChildren = new ArrayList<>();
        List<String> parentsIdForChildren = new ArrayList<>();
        List<String> childrensIdForChildren = new ArrayList<>();
        List<String> childrensNameForChildren = new ArrayList<>();

        for (String childName : childrensName) {
            try {
                Nomenclature child = nomenclatureRepository.findNomenclatureByNomNomenclature(childName).orElseThrow(() ->
                        new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), childName)));
                parentsIdForChildren.addAll(child.getParentsId());
                parentsIdForChildren.add(nomenclature.getId());
                parentsNameListForChildren.addAll(child.getParentsName());
                parentsNameListForChildren.add(nomenclature.getNomNomenclature());
                child.setParentsId(parentsIdForChildren);
                child.setParentsName(parentsNameListForChildren);
                nomenclatureRepository.save(child);
                childrensIdForChildren.add(child.getId());
                childrensNameForChildren.add(child.getNomNomenclature());
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        nomenclature.setChildrensId(childrensIdForChildren);
        nomenclature.setChildrensName(childrensNameForChildren);
    }

    private void setFrsIds(List<String> intituleFrs, Nomenclature nomenclature) {
        List<String> fournisseursId = new ArrayList<>();
        intituleFrs.stream().map(intitule -> {
            Fournisseur fournisseur = null;
            try {
                fournisseur = fournisseurRepository.findFournisseurByIntitule(intitule).orElseThrow(()
                        -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), intitule)));
                fournisseursId.add(fournisseur.getId());
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
            return fournisseursId;
        }).collect(Collectors.toList());
        nomenclature.setFournisseurId(fournisseursId);
    }

    private void affectationClientsIds(List<String> raisonSoClient, Nomenclature nomenclature) {
        List<String> clientsId = new ArrayList<>();
        raisonSoClient.stream().map(raisonSo -> {
            try {
                Client client = clientRepository.findClientByRaisonSocial(raisonSo).orElseThrow(()
                        -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), raisonSo)));
                clientsId.add(client.getId());
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
            return clientsId;
        }).collect(Collectors.toList());
        nomenclature.setClientId(clientsId);
    }

    private static void setPictureToNomenclature(MultipartFile[] image, Nomenclature nomenclature, Picture picture) {
        for (MultipartFile file1 : image) {
            picture.setFileName(file1.getOriginalFilename());
            picture.setType(file1.getContentType());
            try {
                picture.setBytes(file1.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            nomenclature.setPicture(picture);
        }
    }

    private void getEmptyPicture(Nomenclature nomenclature, Picture picture) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File emptyImg = new File(classLoader.getResource("empty.png").getFile());
        picture.setBytes(Files.readAllBytes(emptyImg.toPath()));
        picture.setFileName("empty");
        picture.setType("image/*");
        nomenclature.setPicture(picture);
    }


    @Override
    public ResponseEntity<Map<String, Object>> onSortNomenclatureNotActive(int page, int size, String field, String order) {
        try {
            List<NomenclatureDto> nomencalturesDto;
            Page<Nomenclature> pageTuts;
            if (order.equals("1")) {
                pageTuts = nomenclatureRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
            } else {
                pageTuts = nomenclatureRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
            }
            nomencalturesDto = pageTuts.getContent().stream().map(nomenclature -> {
                return NomenclatureMapper.MAPPER.toNomenclatureDto(nomenclature);
            }).collect(Collectors.toList());
            nomencalturesDto = nomencalturesDto.stream().filter(nomenclatureDto -> nomenclatureDto.isMiseEnVeille()).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("nomenclatures", nomencalturesDto);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> onSortActiveNomenClature(int page, int size, String field, String order) {
        try {
            List<NomenclatureDto> nomencalturesDto;
            Page<Nomenclature> pageTuts;
            if (order.equals("1")) {
                pageTuts = nomenclatureRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
            } else {
                pageTuts = nomenclatureRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
            }
            nomencalturesDto = pageTuts.getContent().stream().map(nomenclature -> {
                return NomenclatureMapper.MAPPER.toNomenclatureDto(nomenclature);
            }).collect(Collectors.toList());
            nomencalturesDto = nomencalturesDto.stream().filter(nomenclatureDto -> !nomenclatureDto.isMiseEnVeille()).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("nomenclatures", nomencalturesDto);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Override
    public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size, boolean enVeille) {
        try {

            List<NomenclatureDto> nomenclatures;
            Pageable paging = PageRequest.of(page, size);
            Page<Nomenclature> pageTuts;
            pageTuts = nomenclatureRepository.findNomenclatureByTextToFindAndMiseEnVeille(textToFind, enVeille, paging);
            nomenclatures = pageTuts.getContent().stream().map(
                    nomenclature -> {
                        return NomenclatureMapper.MAPPER.toNomenclatureDto(nomenclature);

                    }).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("nomenclatures", nomenclatures);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void miseEnVeille(String idNomenclature) throws ResourceNotFoundException {
        Nomenclature nomenclature = nomenclatureRepository.findById(idNomenclature)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idNomenclature)));
        removeAffectationFromchildrensAndParents(nomenclature);
        nomenclature.setMiseEnVeille(true);
        nomenclatureRepository.save(nomenclature);

    }

    private void removeAffectationFromchildrensAndParents(Nomenclature nomenclature) {
        nomenclature.getChildrensId().forEach(id -> {
            Nomenclature   nomenclature1 = null;
            try {
                nomenclature1 = nomenclatureRepository.findById(id)
                           .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
            nomenclature1.getParentsName().removeIf(name -> name.equals(nomenclature.getNomNomenclature()));
            nomenclature1.getParentsId().removeIf(idParent -> idParent.equals(nomenclature.getId()));
            nomenclatureRepository.save(nomenclature1);
        });
        nomenclature.getParentsId().forEach(id -> {
            Nomenclature   nomenclature1 = null;
            try {
                nomenclature1 = nomenclatureRepository.findById(id)
                           .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
            nomenclature1.getChildrensName().removeIf(name -> name.equals(nomenclature.getNomNomenclature()));
            nomenclature1.getChildrensId().removeIf(idChild -> idChild.equals(nomenclature.getId()));
            nomenclatureRepository.save(nomenclature1);
        });
    }


    @Override
    public void deleteNomenclature(Nomenclature nomenclature) {
        nomenclatureRepository.delete(nomenclature);

    }


    @Override
    public ResponseEntity<Map<String, Object>> getNomenClaturesByRaisonsClient(String raison) throws ResourceNotFoundException {
        try {
            Client client = clientRepository.findClientByRaisonSocial(raison)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), raison)));
            List<Nomenclature> nomenclatures = new ArrayList<>();
            Map<String, Object> response = new HashMap<>();
            nomenclatureRepository.findAll().stream().map(
                    nomenclature -> {
                        if (nomenclature.getClientId().contains(client.getId())) {
                            nomenclatures.add(nomenclature);
                        }
                        return nomenclature;
                    }
            ).collect(Collectors.toList());
            response.put("nomenclatures", nomenclatures);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> getNomenClaturesByIntituleFournisseur(String intitule) throws ResourceNotFoundException {
        try {
            Fournisseur fournisseur = fournisseurRepository.findFournisseurByIntitule(intitule)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), intitule)));
            List<Nomenclature> nomenclatures = new ArrayList<>();
            Map<String, Object> response = new HashMap<>();
            nomenclatureRepository.findAll().stream().map(
                    nomenclature -> {
                        if (nomenclature.getFournisseurId().contains(fournisseur.getId())) {
                            nomenclatures.add(nomenclature);
                        }
                        return nomenclature;
                    }
            ).collect(Collectors.toList());
            response.put("nomenclatures", nomenclatures);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public void deleteNomenclatureSelected(List<String> idNomenclaturesSelected) {
        for (String id : idNomenclaturesSelected) {
            nomenclatureRepository.deleteById(id);
        }
    }
    @Override
    public ResponseEntity<Map<String, Object>> getChildrensName() {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("childrensName", nomenclatureRepository.findNomenclatureByMiseEnVeille(false).stream().filter(
                    nomenclature -> !nomenclature.getType().equals(TypeNomEnClature.Famille)).map(Nomenclature::getNomNomenclature).collect(Collectors.toList()
            ));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> getElementOfNomenclature(String idNomenclature) throws ResourceNotFoundException {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("elementsOfNomenclature", nomenclatureRepository.findById(idNomenclature)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idNomenclature)))
                    .getChildrensId().stream()
                    .map(id -> {
                        try {
                            return nomenclatureRepository.findById(id)
                                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
                        } catch (ResourceNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }).collect(Collectors.toList()));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<Map<String, Object>> getSelectedChildrensName(String nomenclatureId) throws ResourceNotFoundException {
        List<String> elementsName = new ArrayList<>();
        Nomenclature nomenclature = nomenclatureRepository.findById(nomenclatureId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), nomenclatureId)));
        for (String id : nomenclature.getChildrensId()) {
            Nomenclature nomenclature1 = nomenclatureRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
            elementsName.add(nomenclature1.getNomNomenclature());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("childrensName", elementsName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Map<String, Object>> getSelectedChildrens(List<String> nomNomenclature) throws ResourceNotFoundException {
        Map<String, Object> response = new HashMap<>();
        List<Nomenclature> nomenclatures = new ArrayList<>();
        for (String nom : nomNomenclature) {
            nomenclatures.add(nomenclatureRepository.findNomenclatureByNomNomenclature(nom)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), nom))));
        }
        response.put("childrens", nomenclatures);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getChildrensNomenclatureToUpdate(String nomenclatureId) throws ResourceNotFoundException {
        List<Nomenclature> childrensNomenclature = new ArrayList<>();
        Nomenclature nomenclature = nomenclatureRepository.findById(nomenclatureId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), nomenclatureId)));
        for (String id : nomenclature.getChildrensId()) {
            Nomenclature nomenclature1 = nomenclatureRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
            childrensNomenclature.add(nomenclature1);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("childrensNomenclature", childrensNomenclature);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getParent() {
        try {
            Map<String, Object> response = new HashMap<>();
            List<Nomenclature> nomenclatures = nomenclatureRepository.findNomenclatureByMiseEnVeille(false);
            response.put("nomenclaturesName", nomenclatures.stream().filter(
                    nomenclature -> !nomenclature.getType().equals(TypeNomEnClature.Element)).map(Nomenclature::getNomNomenclature).collect(Collectors.toList()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public List<String> getNomenclatureNameAffectedForClient(String idClient) throws ResourceNotFoundException {
        return nomenclatureRepository.findNomenclatureByClientId(idClient).stream().map(Nomenclature::getNomNomenclature).collect(Collectors.toList());

    }

    @Override
    public List<String> getNomenclatureNameAffectedForFrs(String idFrs) throws ResourceNotFoundException {
        return nomenclatureRepository.findNomenclatureByFournisseurId(idFrs).stream().map(Nomenclature::getNomNomenclature).collect(Collectors.toList());


    }


    @Override
    public List<String> getNomenclaturesName() {
        List<Nomenclature> nomenclatures = nomenclatureRepository.findAll();
        return nomenclatures.stream()
                .map(Nomenclature::getNomNomenclature)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getNomenclaturesNameFrs() {
        List<Nomenclature> nomenclatures = nomenclatureRepository.findAll();
        return nomenclatures.stream().filter(nomenclature -> nomenclature.getClientId().isEmpty())
                .map(Nomenclature::getNomNomenclature)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getNomenClaturesNameClient() {
        List<Nomenclature> nomenclatures = nomenclatureRepository.findAll();
        return nomenclatures.stream().filter(nomenclature -> nomenclature.getFournisseurId().isEmpty())
                .map(Nomenclature::getNomNomenclature)
                .collect(Collectors.toList());
    }


    @Override
    public List<Nomenclature> getLigneSousFamilleByIdFamille(String idNomEnClature) throws ResourceNotFoundException {
        List<Nomenclature> nomenclatures = new ArrayList<>();
        Nomenclature nomenclature = nomenclatureRepository.findById
                (idNomEnClature).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idNomEnClature)));
//		nomenclature.getChildrensId().stream().map(
//				id -> {
//					try {
//						return nomenclatures.add(nomenclatureRepository.findById(id).orElseThrow(() ->
//								new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id))));
//
//					} catch (ResourceNotFoundException e) {
//						throw new RuntimeException(e);
//					}
//				}
//		).collect(Collectors.toList());
        return nomenclatures;
    }


    @Override
    public void addEtapeToNomenclature(List<EtapeProduction> etapeProductions, String idNomenclature) throws ResourceNotFoundException {
        Nomenclature nomenclature = nomenclatureRepository.findById(idNomenclature)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idNomenclature)));
        nomenclature.setEtapeProductions(etapeProductions);
        nomenclatureRepository.save(nomenclature);
    }


    @Override
    public void deleteNomenclatureEnVeilleSelected(List<String> idNomenClatureSelected) {
        for (String id : idNomenClatureSelected) {
            nomenclatureRepository.deleteById(id);
        }
    }

    @Override
    public List<EtapeProduction> getTargetEtapesNomenclature(String idNomenclature) throws ResourceNotFoundException {
        Nomenclature nomenclature = nomenclatureRepository.findById(idNomenclature).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idNomenclature)));
        List<EtapeProduction> etapeProductions = new ArrayList<>(nomenclature.getEtapeProductions());
        return etapeProductions;
    }

}
