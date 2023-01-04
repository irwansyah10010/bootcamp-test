package com.lawencon.bootcamptest.dto.questionbank;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.lawencon.bootcamptest.dto.questionmultiplechoice.QuestionMultipleChoiceResDataDto;

import lombok.Data;

@Data
public class QuestionBankInsertReqDto {
    
    @NotBlank(message = "question is must required")
    private String question;

    private String questionTypeId;

    private List<QuestionMultipleChoiceResDataDto> multipleChoices;
}
