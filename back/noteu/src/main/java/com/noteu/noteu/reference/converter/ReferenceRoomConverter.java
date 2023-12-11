package com.noteu.noteu.reference.converter;

import com.noteu.noteu.reference.dto.ReferenceDTO;
import com.noteu.noteu.reference.dto.ReferenceRoomDTO;
import com.noteu.noteu.reference.dto.request.AddRequestReferenceRoomDTO;
import com.noteu.noteu.reference.dto.response.DetailResponseReferenceRoomDTO;
import com.noteu.noteu.reference.dto.response.GetAllResponseReferenceRoomDTO;
import com.noteu.noteu.reference.entity.Reference;
import com.noteu.noteu.reference.entity.ReferenceRoom;

import java.util.List;
import java.util.stream.Collectors;


public interface ReferenceRoomConverter {

    /**
     * < RequestDTO(Add) => ReferenceRoomDTO >
     * @param addRequestReferenceRoomDTO
     * @return ReferenceRoomDTO
     */
    default ReferenceRoomDTO requestDtoToReferenceRoomDto(AddRequestReferenceRoomDTO addRequestReferenceRoomDTO) {
        return ReferenceRoomDTO.builder()
                .referenceRoomTitle(addRequestReferenceRoomDTO.getReferenceRoomTitle())
                .referenceRoomContent(addRequestReferenceRoomDTO.getReferenceRoomContent())
                .build();
    }

    /**
     * < ReferenceRoom Dto to Entity >
     * @param referenceRoomDTO
     * @return ReferenceRoom Entity
     */
    default ReferenceRoom referenceRoomDtoToReferenceEntity(ReferenceRoomDTO referenceRoomDTO) {
        return ReferenceRoom.builder()
                .subject(referenceRoomDTO.getSubject())
                .member(referenceRoomDTO.getMember())
                .referenceRoomTitle(referenceRoomDTO.getReferenceRoomTitle())
                .referenceRoomContent(referenceRoomDTO.getReferenceRoomContent())
                .build();
    }

    /**
     * < ReferenceRoom Entity to Dto >
     * @param referenceRoom
     * @return ReferenceRoomDTO
     */
    default ReferenceRoomDTO referenceRoomEntityToReferenceRoomDto(ReferenceRoom referenceRoom) {
        return ReferenceRoomDTO.builder()
                .id(referenceRoom.getId())
                .subject(referenceRoom.getSubject())
                .member(referenceRoom.getMember())
                .referenceRoomTitle(referenceRoom.getReferenceRoomTitle())
                .referenceRoomContent(referenceRoom.getReferenceRoomContent())
                .createdAt(referenceRoom.getCreatedAt())
                .modifiedAt(referenceRoom.getModifiedAt())
                .build();
    }

    /**
     * < Reference Dto to Entity >
     * @param referenceDTO
     * @return Reference Entity
     */
    default Reference referenceDtoToReferenceEntity(ReferenceDTO referenceDTO) {
        return Reference.builder()
                .referenceRoom(referenceDTO.getReferenceRoom())
                .referenceName(referenceDTO.getReferenceName())
                .referenceSize(referenceDTO.getReferenceSize())
                .referenceType(referenceDTO.getReferenceType())
                .build();
    }

    /**
     * < Reference Entity to Dto >
     * @param reference
     * @return ReferenceDTO
     */
    default ReferenceDTO referenceEntityToReferenceDTO(Reference reference) {
        return ReferenceDTO.builder()
                .id(reference.getId())
                .referenceRoom(reference.getReferenceRoom())
                .referenceName(reference.getReferenceName())
                .referenceSize(reference.getReferenceSize())
                .referenceType(reference.getReferenceType())
                .build();
    }

    /**
     * < ReferenceRoom + Reference => DetailResponseDTO >
     * @param referenceRoom
     * @param referenceName
     * @return ResponseDTO
     */
    default DetailResponseReferenceRoomDTO toDetailResponseReferenceRoomDto(ReferenceRoom referenceRoom, List<Reference> referenceName) {

        List<Reference> matchingReferences = referenceName.stream()
                .filter(reference -> reference.getReferenceRoom().getId() == referenceRoom.getId())
                .toList();

        List<ReferenceDTO> referenceDTOList = matchingReferences.stream()
                .map(this::referenceEntityToReferenceDTO)
                .collect(Collectors.toList());

        return DetailResponseReferenceRoomDTO.builder()
                .referenceRoomId(referenceRoom.getId())
                .subject(referenceRoom.getSubject())
                .memberId(referenceRoom.getMember().getId())
                .memberName(referenceRoom.getMember().getMemberName())
                .referenceRoomTitle(referenceRoom.getReferenceRoomTitle())
                .referenceRoomContent(referenceRoom.getReferenceRoomContent())
                .reference(referenceDTOList)
                .createdAt(referenceRoom.getCreatedAt())
                .modifiedAt(referenceRoom.getModifiedAt())
                .build();
    }

    /**
     * < ReferenceRoom Entity to GetAllResponseReferenceRoomDTO >
     * @param referenceRoom
     * @return
     */
    default GetAllResponseReferenceRoomDTO toGetAllResponseReferenceRoomDto(ReferenceRoom referenceRoom) {
        return GetAllResponseReferenceRoomDTO.builder()
                .referenceRoomId(referenceRoom.getId())
                .subject(referenceRoom.getSubject())
                .memberId(referenceRoom.getMember().getId())
                .memberName(referenceRoom.getMember().getMemberName())
                .referenceRoomTitle(referenceRoom.getReferenceRoomTitle())
                .createdAt(referenceRoom.getCreatedAt())
                .build();
    }
}
