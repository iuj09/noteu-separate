package com.noteu.noteu.question.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestQuestionPostDTO {

    private String questionPostTitle;

    private String questionPostContent;
}
