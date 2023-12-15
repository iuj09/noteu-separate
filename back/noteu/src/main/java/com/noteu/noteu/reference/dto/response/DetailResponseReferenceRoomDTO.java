package com.noteu.noteu.reference.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.noteu.noteu.reference.dto.ReferenceDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class DetailResponseReferenceRoomDTO {

    private Long referenceRoomId;

    private Long subjectId;

    private Long memberId;

    private String memberName;

    private String referenceRoomTitle;

    private String referenceRoomContent;

    private List<ResponseReferenceDTO> reference;

    private Long views;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;
}
