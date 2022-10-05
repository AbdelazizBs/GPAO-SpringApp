package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.PlanificationOf;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;


@Getter
@Setter
public class OrdreFabricationDTO {


    @Id
    private String id;

    List<PlanificationOf> planificationOfs;


}
