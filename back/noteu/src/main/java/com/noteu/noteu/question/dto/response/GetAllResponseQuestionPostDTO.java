package com.noteu.noteu.question.dto.response;

import com.noteu.noteu.subject.entity.Subject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class GetAllResponseQuestionPostDTO {

    private Long questionPostId;

    private Subject subject;

    private Long memberId;

    private String memberName;

    private String profile;

    private String questionPostTitle;

    private LocalDateTime createdAt;
}
