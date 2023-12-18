package com.noteu.noteu.reference.service;

import com.noteu.noteu.reference.dto.ReferenceDTO;
import com.noteu.noteu.reference.dto.ReferenceRoomDTO;
import com.noteu.noteu.reference.dto.request.EditRequestReferenceRoomDTO;
import com.noteu.noteu.reference.dto.request.AddRequestReferenceRoomDTO;
import com.noteu.noteu.reference.dto.response.DetailResponseReferenceRoomDTO;
import com.noteu.noteu.reference.dto.response.GetAllResponseReferenceRoomDTO;
import com.noteu.noteu.reference.dto.response.ResponseReferenceDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReferenceRoomService {

    ReferenceRoomDTO save(AddRequestReferenceRoomDTO addRequestReferenceRoomDTO, Long subjectId, Long memberId);

    DetailResponseReferenceRoomDTO getById(Long referenceRoomId);

    void updateViews(Long referenceRoomId);

    void saveFile(AddRequestReferenceRoomDTO requestReferenceRoomDTO, Long referenceRoomId);

    ResponseReferenceDTO getFileById(Long referenceRoomId);

    Page<GetAllResponseReferenceRoomDTO> getAll(int page, Long subjectId);

    Page<GetAllResponseReferenceRoomDTO> getByTitle(int page, Long subjectId, String searchWord);

    void updateById(EditRequestReferenceRoomDTO requestReferenceRoomDTO, Long referenceRoomId);

    void deleteById(Long id);
}
