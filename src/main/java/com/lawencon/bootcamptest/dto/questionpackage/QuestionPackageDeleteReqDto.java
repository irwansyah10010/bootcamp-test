package com.lawencon.bootcamptest.dto.questionpackage;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.lawencon.bootcamptest.dto.questionpackagedetail.QuestionPackageDetailResDataDto;

import lombok.Data;

@Data
public class QuestionPackageDeleteReqDto {
    
    @NotNull(message = "Id is must not null")
    private String id;
    private Boolean isActive;

    private List<QuestionPackageDetailResDataDto> listPackageDetail;
}
