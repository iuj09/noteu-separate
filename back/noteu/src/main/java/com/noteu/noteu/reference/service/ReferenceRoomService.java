package com.noteu.noteu.reference.service;

import com.noteu.noteu.reference.dto.ReferenceDTO;
import com.noteu.noteu.reference.dto.ReferenceRoomDTO;
import com.noteu.noteu.reference.dto.request.EditRequestReferenceRoomDTO;
import com.noteu.noteu.reference.dto.request.AddRequestReferenceRoomDTO;
import com.noteu.noteu.reference.dto.response.DetailResponseReferenceRoomDTO;
import com.noteu.noteu.reference.dto.response.GetAllResponseReferenceRoomDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReferenceRoomService {

    ReferenceRoomDTO save(AddRequestReferenceRoomDTO addRequestReferenceRoomDTO, Long subjectId, Long memberId);

    DetailResponseReferenceRoomDTO getById(Long referenceRoomId);

    void saveFile(AddRequestReferenceRoomDTO requestReferenceRoomDTO, Long referenceRoomId);

    ReferenceDTO getFileById(Long referenceId);

    Page<GetAllResponseReferenceRoomDTO> getAll(int page, Long subjectId);

    Page<GetAllResponseReferenceRoomDTO> getByTitle(int page, Long subjectId, String searchWord);

    void updateById(EditRequestReferenceRoomDTO requestReferenceRoomDTO, Long referenceRoomId);

    void deleteById(Long id);
}
