package com.lawencon.bootcamptest.business.template.dto.questionpackagedetail;

import java.util.List;

import com.lawencon.bootcamptest.business.template.dto.questionmultiplechoice.QuestionMultipleChoiceResDataDto;

import lombok.Data;

@Data
public class QuestionPackageDetailResDataDto {
    
    private String id;

    private String questionId;
    private String question;

    private String typeOfQuestion;

    private List<QuestionMultipleChoiceResDataDto> multipleChoice;
}
