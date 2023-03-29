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
        NomenclatureDto nomenclatureDto = new NomenclatureDto();
        Picture picture = new Picture();

        if (image.length == 0) {
            ClassLoader classLoader = getClass().getClassLoader();
            File emptyImg = new File(classLoader.getResource("empty.png").getFile());
            picture.setBytes(Files.readAllBytes(emptyImg.toPath()));
            picture.setFileName("empty");
            picture.setType("image/*");
            nomenclatureDto.setPicture(picture);
        } else {
            for (MultipartFile file1 : image) {
                picture.setFileName(file1.getOriginalFilename());
                picture.setType(file1.getContentType());
                try {
                    picture.setBytes(file1.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pictureRepository.save(picture);
                nomenclatureDto.setPicture(picture);
            }
        }
        if (raisonSoClient.isEmpty()) {
            nomenclatureDto.setClientId(new ArrayList<>());
        } else {
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
            nomenclatureDto.setClientId(clientsId);
        }
        if (intituleFrs.isEmpty()) {
            nomenclatureDto.setFournisseurId(new ArrayList<>());
        } else {
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
            nomenclatureDto.setFournisseurId(fournisseursId);
        }
        nomenclatureDto.setType(TypeNomEnClature.valueOf(type));
        nomenclatureDto.setDate(new Date());
        nomenclatureDto.setMiseEnVeille(false);
        nomenclatureDto.setNomNomenclature(nomNomenclature);
        nomenclatureDto.setDescription(description);
        nomenclatureDto.setNature(nature);
        nomenclatureDto.setCategorie(categorie);
        nomenclatureDto.setParentsId(new ArrayList<>());
        nomenclatureDto.setChildrensId(new ArrayList<>());
        nomenclatureDto.setChildrens(new ArrayList<>());
        nomenclatureDto.setRefIris(refIris);
        nomenclatureDto.setParentsName(parentsName);
        Nomenclature nomenclature = NomenclatureMapper.MAPPER.toNomenclature(nomenclatureDto);
        nomenclatureRepository.save(nomenclature);
        if (parentsName != null) {
            List<String> childrens = new ArrayList<>();
            List<String> parents = new ArrayList<>();
            for (String parentName : parentsName) {
                try {
                    Nomenclature parent = nomenclatureRepository.findNomenclatureByNomNomenclature(parentName).orElseThrow(() ->
                            new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), parentName)));
                    childrens.add(nomenclature.getId());
                    childrens.addAll(parent.getChildrensId());
                    parent.setChildrensId(childrens);
                    nomenclatureRepository.save(parent);
                    parents.add(parent.getId());
                } catch (ResourceNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            nomenclature.setParentsId(parents);
            nomenclatureRepository.save(nomenclature);


        }
    }


    @Override
    public void updateNomenclature(String idNomenclature, String nomNomenclature, String description, String refIris, String type,
                                   String nature, String categorie, List<String> parentsName, List<String> raisonSoClient,
                                   List<String> intituleFrs, MultipartFile[] image) throws ResourceNotFoundException {

        if (nomNomenclature.isEmpty() || description.isEmpty() || type.isEmpty() || categorie.isEmpty()) {
            throw new IllegalArgumentException("Veuillez remplir tous les champs obligatoires !!");
        }
        Nomenclature nomenclature = getNomenclatureById(idNomenclature)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idNomenclature)));
        for (MultipartFile file1 : image) {
            Picture picture = new Picture();

            picture.setFileName(file1.getOriginalFilename());
            picture.setType(file1.getContentType());
            try {
                picture.setBytes(file1.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            pictureRepository.save(picture);
            nomenclature.setPicture(picture);
        }

        if (raisonSoClient.isEmpty()) {
            nomenclature.setClientId(new ArrayList<>());
        } else {
            List<String> clientsId = new ArrayList<>();
            raisonSoClient.stream().map(raisonSoClient1 -> {
                Client client = null;
                try {
                    client = clientRepository.findClientByRaisonSocial(raisonSoClient1).orElseThrow(()
                            -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), raisonSoClient1)));
                } catch (ResourceNotFoundException e) {
                    throw new RuntimeException(e);
                }
                return clientsId.add(client.getId());
            }).collect(Collectors.toList());
            nomenclature.setClientId(clientsId);
        }
        if (intituleFrs.isEmpty()) {
            nomenclature.setFournisseurId(new ArrayList<>());
        } else {
            List<String> fournisseursId = new ArrayList<>();
            intituleFrs.stream().map(intituleFrs1 -> {
                Fournisseur fournisseur = null;
                try {
                    fournisseur = fournisseurRepository.findFournisseurByIntitule(intituleFrs1).orElseThrow(()
                            -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), intituleFrs1)));
                } catch (ResourceNotFoundException e) {
                    throw new RuntimeException(e);
                }
                return fournisseursId.add(fournisseur.getId());
            }).collect(Collectors.toList());
            nomenclature.setFournisseurId(fournisseursId);
        }
        nomenclature.setType(TypeNomEnClature.valueOf(type));
        nomenclature.setDate(nomenclature.getDate());
        nomenclature.setDateMiseEnVeille(nomenclature.getDateMiseEnVeille());
        nomenclature.setDescription(description);
        nomenclature.setNature(nature);
        nomenclature.setCategorie(categorie);
        nomenclature.setRefIris(refIris);
        nomenclature.setNomNomenclature(nomNomenclature);
        if (!nomenclature.getParentsId().isEmpty()) {
            nomenclature.getParentsId().stream().map(
                    id -> {
                        try {
                            return nomenclatureRepository.findById(id).orElseThrow(() ->
                                    new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
                        } catch (ResourceNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
            ).forEach(parent -> {
                parent.getChildrensId().removeIf(id -> id.equals(nomenclature.getId()));
                nomenclatureRepository.save(parent);
            });
        }
        if (!parentsName.isEmpty()) {
            List<String> parentsId = new ArrayList<>();
            for (String parentName : parentsName) {
                try {
                    Nomenclature parent = nomenclatureRepository.findNomenclatureByNomNomenclature(parentName).orElseThrow(() ->
                            new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), parentName)));
                    parent.getChildrensId().add(nomenclature.getId());
                    nomenclatureRepository.save(parent);
                    parentsId.add(parent.getId());
                } catch (ResourceNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            nomenclature.setParentsId(parentsId);
            nomenclatureRepository.save(nomenclature);
        } else {
            nomenclature.setParentsId(new ArrayList<>());
        }
        nomenclature.setParentsName(parentsName);
        nomenclatureRepository.save(nomenclature);

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
    public List<String> getParentsNameFiltered(String idNomEnClature) throws ResourceNotFoundException {
        Nomenclature nomenclatures = nomenclatureRepository.findById(idNomEnClature)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idNomEnClature)));
        List<Nomenclature> nomenclatureList = nomenclatureRepository.findAll();
        // return list nomenclatures after filtered by type
        return nomenclatureList.stream().filter(nomenclature -> !nomenclature.getNomNomenclature().equals(nomenclatures.getNomNomenclature()))
                .map(Nomenclature::getNomNomenclature).collect(Collectors.toList());

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
