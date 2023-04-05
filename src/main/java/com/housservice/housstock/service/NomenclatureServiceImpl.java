package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.NomenclatureMapper;
import com.housservice.housstock.model.*;
import com.housservice.housstock.model.dto.NomenclatureDto;
import com.housservice.housstock.model.enums.TypeNomEnClature;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.FournisseurRepository;
import com.housservice.housstock.repository.NomenclatureRepository;
import com.housservice.housstock.repository.PictureRepository;
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


    @Autowired
    public NomenclatureServiceImpl(NomenclatureRepository nomenclatureRepository, SequenceGeneratorService sequenceGeneratorService,
                                   MessageHttpErrorProperties messageHttpErrorProperties, PictureRepository pictureRepository,
                                   ClientRepository clientRepository,
                                   FournisseurRepository fournisseurRepository) {
        this.nomenclatureRepository = nomenclatureRepository;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.pictureRepository = pictureRepository;
        this.clientRepository = clientRepository;
        this.fournisseurRepository = fournisseurRepository;
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
    public ResponseEntity<Map<String, Object>> getIdNomenclatures(String nomNomenclature)
            throws ResourceNotFoundException {

        Nomenclature nomenclature = nomenclatureRepository.findNomenclatureByNomNomenclature(nomNomenclature).orElseThrow(()
                -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), nomNomenclature)));
        Map<String, Object> response = new HashMap<>();
        response.put("idNomenclature", nomenclature.getId());
        //response.put("refIris", nomenclature.getRefIris());
        return ResponseEntity.ok(response);
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
                                      List<String> raisonSoClient,
                                      List<String> intituleFrs,
                                      MultipartFile[] image) throws ResourceNotFoundException, IOException {
        if (nomenclatureRepository.existsNomenclatureByNomNomenclature(nomNomenclature)) {
            throw new IllegalArgumentException("Nom nomenclature existe déja !!");
        }
        Nomenclature nomenclature = new Nomenclature();
        nomenclature.setType(TypeNomEnClature.valueOf(type));
        nomenclature.setDate(new Date());
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
        nomenclature.setId(ObjectId.get().toString());
        Picture picture = new Picture();
        if (image.length == 0) {
            getEmptyPicture(nomenclature, picture);
        }
        setPictureToNomenclature(image, nomenclature, picture);
        if (!raisonSoClient.isEmpty()) {
            affectationClientsIds(raisonSoClient, nomenclature);
        }
        if (!intituleFrs.isEmpty()) {
            setFrsIds(intituleFrs, nomenclature);
        }
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
    public void updateNomenclature(String idNomenclature, String nomNomenclature, String description, String refIris, String type,
                                   String nature, String categorie, List<String> parentsName, List<String> childrensName, List<String> raisonSoClient,
                                   List<String> intituleFrs, MultipartFile[] image) throws ResourceNotFoundException {

        if (isEmpty(nomNomenclature, description, type, categorie)) {
            throw new IllegalArgumentException("Veuillez remplir tous les champs obligatoires !!");
        }
        Nomenclature nomenclature = getNomenclatureById(idNomenclature)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idNomenclature)));
        nomenclature.setType(TypeNomEnClature.valueOf(type));
        nomenclature.setDate(nomenclature.getDate());
        nomenclature.setDateMiseEnVeille(nomenclature.getDateMiseEnVeille());
        nomenclature.setDescription(description);
        nomenclature.setNature(nature);
        nomenclature.setCategorie(categorie);
        nomenclature.setRefIris(refIris);
        nomenclature.setNomNomenclature(nomNomenclature);
        nomenclature.setParentsName(parentsName);
        nomenclature.setChildrensName(childrensName);
        setPictureToNomenclature(image, nomenclature, nomenclature.getPicture());
//        if (!nomenclature.getParentsId().isEmpty()) {
//            nomenclature.getParentsId().stream().map(
//                    id -> {
//                        try {
//                            return nomenclatureRepository.findById(id).orElseThrow(() ->
//                                    new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
//                        } catch (ResourceNotFoundException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//            ).forEach(parent -> {
//                parent.getChildrensId().removeIf(id -> id.equals(nomenclature.getId()));
//                nomenclatureRepository.save(parent);
//            });
//        }
//        if (!parentsName.isEmpty()) {
//            List<String> parentsId = new ArrayList<>();
//            for (String parentName : parentsName) {
//                try {
//                    Nomenclature parent = nomenclatureRepository.findNomenclatureByNomNomenclature(parentName).orElseThrow(() ->
//                            new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), parentName)));
//                    parent.getChildrensId().add(nomenclature.getId());
//                    nomenclatureRepository.save(parent);
//                    parentsId.add(parent.getId());
//                } catch (ResourceNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            nomenclature.setParentsId(parentsId);
//            nomenclatureRepository.save(nomenclature);
//        } else {
//            nomenclature.setParentsId(new ArrayList<>());
//        }

        if (!raisonSoClient.isEmpty()) {
            affectationClientsIds(raisonSoClient, nomenclature);
        }
        if (!intituleFrs.isEmpty()) {
            setFrsIds(intituleFrs, nomenclature);
        }
        if (parentsName.isEmpty()) {
            nomenclature.setParentsId(new ArrayList<>());
            nomenclature.setParentsName(new ArrayList<>());
        } else {
            updateAffectationParents(parentsName, nomenclature);
        }
        if (childrensName.isEmpty()) {
            nomenclature.setChildrensId(new ArrayList<>());
            nomenclature.setChildrensName(new ArrayList<>());
        } else {
            updateAffectationChildrens(parentsName, nomenclature);
        }
        nomenclatureRepository.save(nomenclature);

    }

    private static boolean isEmpty(String nomNomenclature, String description, String type, String categorie) {
        return nomNomenclature.isEmpty() || description.isEmpty() || type.isEmpty() || categorie.isEmpty();
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
                parentsIdIdList.stream().filter(id -> id.equals(nomenclature.getId())).collect(Collectors.toList()).add(nomenclature.getId());
                parentsNameList.addAll(child.getParentsName());
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
        nomenclatureRepository.save(nomenclature);
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
                childrensIdList.removeIf(id -> id.equals(nomenclature.getId()));
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
        nomenclatureRepository.save(nomenclature);
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
        nomenclature.setMiseEnVeille(true);
        nomenclatureRepository.save(nomenclature);

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
    public ResponseEntity<Map<String, Object>> getChildrensNameArticles() {
        try {
            Map<String, Object> response = new HashMap<>();
            List<Nomenclature> nomenclatures = nomenclatureRepository.findNomenclatureByMiseEnVeille(false);
            response.put("childrensName", nomenclatures.stream().filter(
                    nomenclature -> !nomenclature.getType().equals(TypeNomEnClature.Element)).map(Nomenclature::getNomNomenclature).collect(Collectors.toList()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> getChildrensNameElements() {
        try {
            Map<String, Object> response = new HashMap<>();
            List<Nomenclature> nomenclatures = nomenclatureRepository.findNomenclatureByMiseEnVeille(false);
            response.put("childrensName", nomenclatures.stream().filter(
                    nomenclature -> nomenclature.getType().equals(TypeNomEnClature.Element)).map(Nomenclature::getNomNomenclature).collect(Collectors.toList()));
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
        return nomenclature.getEtapeProductions();
    }

}
