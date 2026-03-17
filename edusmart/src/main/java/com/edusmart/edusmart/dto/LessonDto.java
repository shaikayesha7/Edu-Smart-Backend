package com.edusmart.edusmart.dto;

import lombok.Data;

@Data
public class LessonDto {
    private Long id;
    private String lessonTitle;
    private String videoLink;
    private String lessonDescription;
}
