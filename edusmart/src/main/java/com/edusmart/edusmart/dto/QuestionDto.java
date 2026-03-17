package com.edusmart.edusmart.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDto {
    private String questionText;
    private Integer points;
    private String questionType;
    private Integer correctOptionIndex;

    // Angular sends an array of objects: [{optionText: "A"}, {optionText: "B"}]
    private List<OptionDto> options;
}
