package com.noteu.noteu.reference.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class EditRequestReferenceRoomDTO {
    private String referenceRoomTitle;

    private String referenceRoomContent;

    private List<String> referenceName;

    private List<String> referenceType;

    private List<Long> referenceSize;

    private List<Long> referenceId;

    private List<MultipartFile> referenceFile;
}
