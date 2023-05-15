

package com.housservice.housstock.model.dto;


import com.housservice.housstock.model.Picture;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.io.File;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PlanEtapesDto {

    private String id;
    private String nomEtape;
    private String refMachine;

    private List<String> personnels;
    private Date datePrevu;
    private Date heureDebutPrevue;
    private Date heureFinPrevue;
    private Date heureDebutReel;
    private Date heureFinReel;
    private int quantiteNonConforme;
    private int quantiteConforme;
    private int quantiteInitiale;
    private String commentaire;

}








