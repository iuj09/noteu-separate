package com.noteu.noteu.reference.service.impl;

import com.noteu.noteu.member.repository.MemberRepository;
import com.noteu.noteu.reference.converter.Impl.ReferenceRoomConverterImpl;
import com.noteu.noteu.reference.dto.ReferenceDTO;
import com.noteu.noteu.reference.dto.ReferenceRoomDTO;
import com.noteu.noteu.reference.dto.request.EditRequestReferenceRoomDTO;
import com.noteu.noteu.reference.dto.request.AddRequestReferenceRoomDTO;
import com.noteu.noteu.reference.dto.response.DetailResponseReferenceRoomDTO;
import com.noteu.noteu.reference.dto.response.GetAllResponseReferenceRoomDTO;
import com.noteu.noteu.reference.entity.Reference;
import com.noteu.noteu.reference.entity.ReferenceRoom;
import com.noteu.noteu.reference.repository.ReferenceRepository;
import com.noteu.noteu.reference.repository.ReferenceRoomRepository;
import com.noteu.noteu.reference.service.ReferenceRoomService;
import com.noteu.noteu.subject.entity.Subject;
import com.noteu.noteu.subject.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReferenceRoomServiceImpl implements ReferenceRoomService {

    private final ReferenceRoomRepository referenceRoomRepository;
    private final ReferenceRepository referenceRepository;
    private final ReferenceRoomConverterImpl referenceRoomConverter;
    private final MemberRepository memberRepository;
    private final SubjectRepository subjectRepository;

    public ReferenceRoomDTO save(AddRequestReferenceRoomDTO addRequestReferenceRoomDTO, Long subjectId, Long memberId){
        /**
         * 1. AddRequestReferenceRoomDTO, subjectId, memberId -> ReferenceRoomDTO 변환
         * 2. ReferenceRoomDTO -> ReferenceRoom(Entity) and save Entity
         * 3. ReferenceRoom(Entity) -> ReferenceRoomDTO(response) 변환 후 return
         */
        ReferenceRoomDTO referenceRoomDTO = referenceRoomConverter.requestDtoToReferenceRoomDto(addRequestReferenceRoomDTO);
        referenceRoomDTO.setMember(memberRepository.getReferenceById(memberId));
        referenceRoomDTO.setSubject(subjectRepository.getReferenceById(subjectId));

        ReferenceRoom referenceRoom = referenceRoomConverter.referenceRoomDtoToReferenceEntity(referenceRoomDTO);

        referenceRoomRepository.save(referenceRoom);

        ReferenceRoomDTO responseReferenceRoomDTO = referenceRoomConverter.referenceRoomEntityToReferenceRoomDto(referenceRoom);

        return responseReferenceRoomDTO;
    }

    @Override
    public void saveFile(AddRequestReferenceRoomDTO addRequestReferenceRoomDTO, Long referenceRoomId){
        /**
         * 1. AddRequestReferenceRoomDTO에서 fileNames, fileSizes, fileTypes List 추출
         * 2. List의 size만큼 for문을 실행하면서 List에서 꺼낸값과 referenceRoomId를 ReferenceDTO에 저장
         * 3. ReferenceDTO -> Reference(Entity) and save Entity
         */
        List<String> fileNames = addRequestReferenceRoomDTO.getReferenceName();
        List<Long> fileSizes = addRequestReferenceRoomDTO.getReferenceSize();
        List<String> fileTypes = addRequestReferenceRoomDTO.getReferenceType();

        int size = fileNames.size();

        for(int i = 0; i < size; i++) {
            String fileName = fileNames.get(i);
            Long fileSize = fileSizes.get(i);
            String fileType = fileTypes.get(i);
            ReferenceDTO referenceDTO = ReferenceDTO.builder()
                    .referenceRoom(referenceRoomRepository.getReferenceById(referenceRoomId))
                    .referenceName(fileName)
                    .referenceSize(fileSize)
                    .referenceType(fileType)
                    .build();
            Reference reference = referenceRoomConverter.referenceDtoToReferenceEntity(referenceDTO);
            referenceRepository.save(reference);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public DetailResponseReferenceRoomDTO getById(Long referenceRoomId) {
        /**
         * parameter로 받은 id로 ReferenceRoom과 위 id를 FK로 갖는 Reference들을 List에 담은 것을
         * DetatilResponseReferenceRoomDTO에 담아 반환
         */
        ReferenceRoom referenceRoom = referenceRoomRepository.findById(referenceRoomId)
                .orElseThrow(() -> new EntityNotFoundException("Entity가 존재하지 않습니다." + referenceRoomId));

        List<Reference> referenceList = referenceRepository.findByReferenceRoom(referenceRoom);

        DetailResponseReferenceRoomDTO detailResponseReferenceRoomDTO =
                referenceRoomConverter.toDetailResponseReferenceRoomDto(referenceRoom, referenceList);

        return detailResponseReferenceRoomDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public ReferenceDTO getFileById(Long referenceId) {
        Reference reference = referenceRepository.findById(referenceId)
                .orElseThrow(() -> new EntityNotFoundException("Entity가 존재하지 않습니다." + referenceId));

        ReferenceDTO referenceDTO = referenceRoomConverter.referenceEntityToReferenceDTO(reference);

        return referenceDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetAllResponseReferenceRoomDTO> getAll(int page, Long subjectId) {
        /**
         * 1. ReferenceRoom Entity의 전체 목록을 id 기준 내림차순 정렬(최신순 정렬)해서 Page에 담음
         * 2. 위 Page를 GetAllResponseRefeRenceRoomDTO 타입의 List로 변환
         * 3. 위 List를 PageImpl에 담아서 반환
         */
        Subject subject = subjectRepository.getReferenceById(subjectId);
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<ReferenceRoom> pageNationList = referenceRoomRepository.findBySubject(pageable, subject);
        List<GetAllResponseReferenceRoomDTO> dtoList  = new ArrayList<>();
        for(ReferenceRoom referenceRoom : pageNationList) {
            GetAllResponseReferenceRoomDTO getAllResponseReferenceRoomDTO = referenceRoomConverter.toGetAllResponseReferenceRoomDto(referenceRoom);
            dtoList.add(getAllResponseReferenceRoomDTO);
        }
        PageImpl<GetAllResponseReferenceRoomDTO> pageNationDtoList = new PageImpl<>(dtoList, pageable, pageNationList.getTotalElements());

        return pageNationDtoList;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetAllResponseReferenceRoomDTO> getByTitle(int page, Long subjectId, String searchWord) {

        Subject subject = subjectRepository.getReferenceById(subjectId);
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<ReferenceRoom> pageNationList = referenceRoomRepository.findBySubjectAndReferenceRoomTitleContaining(pageable, subject, searchWord);
        List<GetAllResponseReferenceRoomDTO> dtoList = new ArrayList<>();
        for(ReferenceRoom referenceRoom : pageNationList) {
            GetAllResponseReferenceRoomDTO getAllResponseReferenceRoomDTO = referenceRoomConverter.toGetAllResponseReferenceRoomDto(referenceRoom);
            dtoList.add(getAllResponseReferenceRoomDTO);
        }
        PageImpl<GetAllResponseReferenceRoomDTO> pageNationDtoList = new PageImpl<>(dtoList, pageable, pageNationList.getTotalElements());

        return pageNationDtoList;
    }

    @Override
    public void updateById(EditRequestReferenceRoomDTO editRequestReferenceRoomDTO, Long referenceRoomId) {
        /**
         * ReferenceRoom Update
         * 1. parameter로 받은 id로 referenceRoom(Entity)를 get
         * 2. referenceRoom(Entity)의 Title, Content 수정
         */
        ReferenceRoom referenceRoom = referenceRoomRepository.findById(referenceRoomId)
                .orElseThrow(() -> new EntityNotFoundException("Entity가 존재하지 않습니다." + referenceRoomId));

        String newTitle = editRequestReferenceRoomDTO.getReferenceRoomTitle();
        String newContent = editRequestReferenceRoomDTO.getReferenceRoomContent();

        referenceRoom.update(newTitle, newContent);

        /**
         * Reference Update(기존 DB에 저장된 값 삭제 후 새로 받아온 값 저장)
         * 1. parameter로 받은 id를 FK로 갖는 Reference(Entity) 목록 OriginReferences에 저장
         * 2. OrigineReferences 삭제
         * 3. EditRequestReferenceRoomDTO에서 fileNames, fileSizes, fileTypes List 추출
         * 4. List의 size만큼 for문을 실행하면서 List에서 꺼낸값과 referenceRoomId를 ReferenceDTO에 저장
         * 5. ReferenceDTO -> Reference(Entity) and save Entity
         */
        List<Reference> originReferences = referenceRepository.findByReferenceRoom(referenceRoom);
        for(Reference originReference : originReferences) {
            referenceRepository.deleteById(originReference.getId());
        }

        List<String> fileNames = editRequestReferenceRoomDTO.getReferenceName();
        List<Long> fileSizes = editRequestReferenceRoomDTO.getReferenceSize();
        List<String> fileTypes = editRequestReferenceRoomDTO.getReferenceType();

        int size = fileNames.size();

        for(int i = 0; i < size; i++) {
            String fileName = fileNames.get(i);
            Long fileSize = fileSizes.get(i);
            String fileType = fileTypes.get(i);
            ReferenceDTO referenceDTO =
                    ReferenceDTO.builder()
                            .referenceRoom(referenceRoomRepository.getReferenceById(referenceRoomId))
                            .referenceName(fileName)
                            .referenceSize(fileSize)
                            .referenceType(fileType)
                            .build();
            Reference reference = referenceRoomConverter.referenceDtoToReferenceEntity(referenceDTO);
            referenceRepository.save(reference);
        }
    }

    @Override
    public void deleteById(Long id) {
        referenceRoomRepository.deleteById(id);
    }
}
