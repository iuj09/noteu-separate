package com.noteu.noteu.question.dto;

import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.subject.entity.Subject;
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
public class QuestionPostDTO {

    private Long id;

    private Subject subject;

    private Member member;

    private String questionPostTitle;

    private String questionPostContent;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
