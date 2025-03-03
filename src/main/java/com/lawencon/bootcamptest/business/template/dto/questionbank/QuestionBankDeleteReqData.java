package com.lawencon.bootcamptest.business.template.dto.questionbank;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.lawencon.bootcamptest.business.template.dto.questionmultiplechoice.QuestionMultipleChoiceDeleteReqDto;

import lombok.Data;

@Data
public class QuestionBankDeleteReqData {
    
    @NotNull(message = "Id is must not null")
    private String id;
    private Boolean isActive;
    private List<QuestionMultipleChoiceDeleteReqDto> multipleChoices;
}
