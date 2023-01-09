package com.housservice.housstock.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.NomenclatureMapper;
import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.Picture;
import com.housservice.housstock.model.dto.NomenclatureDto;
import com.housservice.housstock.repository.NomenclatureRepository;
import com.housservice.housstock.repository.PictureRepository;

@Service
public class NomenclatureServiceImpl implements NomenclatureService {

	


}
