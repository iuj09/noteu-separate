package com.noteu.noteu.subject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Builder
public class SubjectRequestDto{
    private String subjectName;
}
