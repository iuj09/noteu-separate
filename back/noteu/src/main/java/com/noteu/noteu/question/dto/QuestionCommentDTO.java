package com.noteu.noteu.question.dto;

import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.question.entity.QuestionPost;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class QuestionCommentDTO {

    private Long id;

    private QuestionPost questionPost;

    private Member member;

    private String questionCommentContent;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
