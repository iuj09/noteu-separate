package com.noteu.noteu.question.dto.request;

import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.question.entity.QuestionPost;
import com.noteu.noteu.subject.entity.Subject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestQuestionCommentDTO {

    private QuestionPost questionPost;

    private Member member;

    private String questionCommentContent;
}
