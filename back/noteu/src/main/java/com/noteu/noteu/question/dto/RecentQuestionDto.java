package com.noteu.noteu.question.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RecentQuestionDto {
    private Long subjectId;
    private Long questionId;
    private String subjectName;
    private String questionPostTitle;
    private String questionPostContent;
    private int commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
