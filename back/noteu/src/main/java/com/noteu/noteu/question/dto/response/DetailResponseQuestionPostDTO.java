package com.noteu.noteu.question.dto.response;

import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.question.dto.QuestionCommentDTO;
import com.noteu.noteu.subject.entity.Subject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class DetailResponseQuestionPostDTO {

    private Long questionPostId;

    private Subject subject;

    private Long memberId;

    private String memberName;

    private String profile;

    private String questionPostTitle;

    private String questionPostContent;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private List<ResponseQuestionCommentDTO> comment;

    private int commentCount;
}
