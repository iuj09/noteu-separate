package com.noteu.noteu.question.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchRequestQuestionPostDTO {

    private String searchType;

    private String searchWord;
}
