package com.noteu.noteu.reference.controller;


import com.noteu.noteu.member.dto.MemberInfo;
import com.noteu.noteu.reference.dto.ReferenceDTO;
import com.noteu.noteu.reference.dto.ReferenceRoomDTO;
import com.noteu.noteu.reference.dto.request.EditRequestReferenceRoomDTO;
import com.noteu.noteu.reference.dto.request.AddRequestReferenceRoomDTO;
import com.noteu.noteu.reference.dto.response.DetailResponseReferenceRoomDTO;
import com.noteu.noteu.reference.dto.response.GetAllResponseReferenceRoomDTO;
import com.noteu.noteu.reference.dto.response.ResponseReferenceDTO;
import com.noteu.noteu.reference.service.impl.ReferenceRoomServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/subjects/{subject-id}/references")
public class ReferenceRoomController {

    private final ReferenceRoomServiceImpl referenceRoomService;

    @Value("${spring.servlet.multipart.location}")
    private String path;


    @PostMapping
    public ResponseEntity<Void> addReferenceRoom(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                                 @RequestPart("addRequestReferenceRoomDTO") AddRequestReferenceRoomDTO addRequestReferenceRoomDTO, @RequestPart("referenceFile") List<MultipartFile> referenceFile) {

        if (memberInfo == null) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        } else if (!memberInfo.getAuthorities().toString().contains("ROLE_TEACHER")) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        } else {
            log.info("[log] 회원id : {}", memberInfo.getId());
            log.info("[log] 과목id : {}", subjectId);
            log.info("[log] 제목 : {}", addRequestReferenceRoomDTO.getReferenceRoomTitle());
            log.info("[log] 내용 : {}", addRequestReferenceRoomDTO.getReferenceRoomContent());
            log.info("[log] 파일 : {}", referenceFile);
            if (addRequestReferenceRoomDTO.getReferenceRoomTitle().isEmpty()
                    || addRequestReferenceRoomDTO.getReferenceRoomContent().isEmpty()
                    || referenceFile.size() == 0) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .build();
            } else {
                ReferenceRoomDTO referenceRoomDTO = referenceRoomService.save(addRequestReferenceRoomDTO, subjectId, memberInfo.getId());

                List<MultipartFile> fileList = referenceFile;
                Long referenceRoomDTOId = referenceRoomDTO.getId();

                File referenceDir = new File(path + "/reference");
                if(!referenceDir.exists()) {
                    if(referenceDir.mkdir()) {
                        log.info("[log] Success create reference directory!");
                    } else {
                        log.info("[log] Failed create reference directory");
                    }
                }

                File fileDir = new File(path + "/reference/" + referenceRoomDTOId);
                if(!fileDir.exists()) {
                    if(fileDir.mkdir()) {
                        log.info("[log] Success create file directory!");
                    } else {
                        log.info("[log] Failed create file directory");
                    }
                }

                List<String> fileNames = new ArrayList<>();
                List<Long> fileSizes = new ArrayList<>();
                List<String> fileTypes = new ArrayList<>();

                for (MultipartFile file : fileList) {
                    String fileName = Normalizer.normalize(file.getOriginalFilename(), Normalizer.Form.NFC);

                    Long bytes = file.getSize();
                    Long fileSize = bytes / 1024 / 1024;
                    String fileType = file.getContentType();
                    log.info("[log] 파일 이름 : {}", fileName);
                    log.info("[log] 파일 크기 : {}", fileSize);
                    log.info("[log] 파일 타입 : {}", fileType);
                    try {
                        file.transferTo(new File(fileDir + "/" + fileName));
                        fileNames.add(fileName);
                        fileSizes.add(fileSize);
                        fileTypes.add(fileType);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                addRequestReferenceRoomDTO.setReferenceName(fileNames);
                addRequestReferenceRoomDTO.setReferenceSize(fileSizes);
                addRequestReferenceRoomDTO.setReferenceType(fileTypes);
                referenceRoomService.saveFile(addRequestReferenceRoomDTO, referenceRoomDTOId);

                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .build();
            }
        }
    }

    @GetMapping
    public ResponseEntity<Page<GetAllResponseReferenceRoomDTO>> referenceRoomList(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                    @RequestParam(value = "page", defaultValue = "0") int page) {
        if (memberInfo == null) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        } else {
            Page<GetAllResponseReferenceRoomDTO> dtoList = referenceRoomService.getAll(page, subjectId);

            return ResponseEntity.ok(dtoList);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<Page<GetAllResponseReferenceRoomDTO>> referenceRoomListByTitle(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                           @RequestParam(value = "page", defaultValue = "0") int page, @RequestBody String searchWord) {
        if (memberInfo == null) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        } else if (!memberInfo.getAuthorities().toString().equals("[ROLE_{authority=ROLE_TEACHER}]")) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        } else {
            Page<GetAllResponseReferenceRoomDTO> dtoList = referenceRoomService.getByTitle(page, subjectId, searchWord);

            return ResponseEntity.ok(dtoList);
        }
    }

    @GetMapping("/{referenceId}")
    public ResponseEntity<DetailResponseReferenceRoomDTO> getReferenceRoomById(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                                                               @PathVariable Long referenceId, HttpServletRequest request, HttpServletResponse response) {
        if (memberInfo == null) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        } else {
            DetailResponseReferenceRoomDTO detailResponseReferenceRoomDTO = referenceRoomService.getById(referenceId);
            if(detailResponseReferenceRoomDTO != null) {
                Cookie oldCookie = null;
                Cookie[] cookies = request.getCookies();
                if(cookies != null) {
                    for(Cookie cookie : cookies) {
                        if(cookie.getName().equals("referenceRoomView")) {
                            oldCookie = cookie;
                        }
                    }
                }
                if(oldCookie != null) {
                    if (!oldCookie.getValue().contains("["+ referenceId.toString() +"]")) {
                        referenceRoomService.updateViews(referenceId);
                        oldCookie.setValue(oldCookie.getValue() + "_[" + referenceId + "]");
                        oldCookie.setPath("/");
                        oldCookie.setMaxAge(60 * 60 * 12);
                        response.addCookie(oldCookie);
                    }
                } else {
                    Cookie newCookie = new Cookie("referenceRoomView", "[" + referenceId + "]");
                    referenceRoomService.updateViews(referenceId);
                    newCookie.setPath("/");
                    newCookie.setMaxAge(60 * 60 * 12);
                    response.addCookie(newCookie);
                }
            }

            return ResponseEntity.ok(detailResponseReferenceRoomDTO);
        }
    }


    @PutMapping("/{referenceRoomId}")
    public ResponseEntity<Void> referenceRoomUpdate(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable Long referenceRoomId,
                                                    @RequestPart("editRequestReferenceRoomDTO") EditRequestReferenceRoomDTO editRequestReferenceRoomDTO, @RequestPart("referenceFile") List<MultipartFile> referenceFile) {

        if(!memberInfo.getId().equals(referenceRoomService.getById(referenceRoomId).getMemberId())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        } else {
            List<Long> originReferenceIds = editRequestReferenceRoomDTO.getReferenceId();

            List<String> fileNames = new ArrayList<>();
            List<Long> fileSizes = new ArrayList<>();
            List<String> fileTypes = new ArrayList<>();

            File fileDir = new File(path + "/reference/" + referenceRoomId);
            File[] files = fileDir.listFiles();

            /**
             * 1. originReferenceIds에 값이 있는 지 확인
             *  1.1. 값이 있다면 해당 id로 ReferenceDTO에서 fileName, fileSize, fileType을 꺼내옴
             *  1.2. 꺼내온 파일 정보들과 fileDir에 저장된 파일들의 정보가 서로 일치하는 지 확인
             *  1.3. DTO의 파일 정보와 fileDir에 저장된 파일 정보가 일치하지 않는 파일들 삭제
             *  1.4. originReferenceIds에 값이 없으면 fileDir에 존재하는 모든 파일 삭제
             *  1.5. 삭제되지 않은 파일들은 파일 정보를 담는 각각의 List에 파일 정보 저장
             */
            if (!originReferenceIds.isEmpty() && originReferenceIds != null) {
                for (Long originReferenceId : originReferenceIds) {
                    ResponseReferenceDTO referenceDTO = referenceRoomService.getFileById(originReferenceId);
                    fileNames.add(referenceDTO.getReferenceName());
                    fileSizes.add(referenceDTO.getReferenceSize());
                    fileTypes.add(referenceDTO.getReferenceType());
                }
                for (File file : files) {
                    String originFileName = file.getName();
                    log.info("[log] 기존 파일 명 : {}", originFileName);
                    long bytes = file.length();
                    Long originFileSize = bytes / 1024 / 1024;
                    log.info("[log] 기존 파일 크기 : {}", originFileSize);
                    Path filePath = file.toPath();
                    try {
                        String originFileType = Files.probeContentType(filePath);
                        log.info("[log] 기존 파일 타입 : {}", originFileType);

                        boolean shouldDeleteFile = true;

                        for (int i = 0; i < fileNames.size(); i++) {
                            String fileName = fileNames.get(i);
                            Long fileSize = fileSizes.get(i);
                            String fileType = fileTypes.get(i);

                            log.info("[log] 저장 유지 파일 명 : {}", fileName);
                            log.info("[log] 저장 유지 파일 크기 : {}", fileSize);
                            log.info("[log] 저장 유지 파일 타입 : {}", fileType);
                            log.info("[log] originFileName.equals(fileName)) : {}", originFileName.equals(fileName));
                            log.info("[log] originFileName.length : {}", originFileName.length());
                            log.info("[log] fileName.length : {}", fileName.length());
                            log.info("[log] originFileSize.equals(fileSize) : {}", originFileSize.equals(fileSize));
                            log.info("[log] originFileType.equals(fileType)) : {}", originFileType.equals(fileType));
                            if (originFileName.equals(fileName) &&
                                    originFileSize.equals(fileSize) &&
                                    originFileType.equals(fileType)) {

                                shouldDeleteFile = false;
                                break;
                            }
                            log.info("[log] 파일 삭제 여부 : {}", shouldDeleteFile);
                        }
                        if (shouldDeleteFile) {
                            file.delete();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                for (File file : files) {
                    file.delete();
                }
            }

            /**
             * 2. List<MultipartFile> referenceFile 값이 있는 지 확인
             *  2.1. referenceFile에 값이 있는 경우 해당 파일을 fileDir에 저장
             *  2.2. 디렉토리에 저장한 파일의 이름, 크기, 타입 정보를 추출 해 각각의 값을 담을 List에 저장
             *  2.3.
             */
            List<MultipartFile> referenceFiles = referenceFile;

            if (referenceFiles != null && !referenceFiles.isEmpty()) {
                for (MultipartFile file : referenceFiles) {
                    String fileName = file.getOriginalFilename().strip();
                    Long bytes = file.getSize();
                    Long fileSize = bytes / 1024 / 1024;
                    String fileType = file.getContentType();
                    log.info("[log] 파일 이름 : {}", fileName);
                    log.info("[log] 파일 크기 : {}", fileSize);
                    log.info("[log] 파일 타입 : {}", fileType);
                    try {
                        file.transferTo(new File(fileDir + "/" + fileName));
                        fileNames.add(fileName);
                        fileSizes.add(fileSize);
                        fileTypes.add(fileType);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                log.info("[log] 새로 추가된 파일 없음");
            }
            editRequestReferenceRoomDTO.setReferenceName(fileNames);
            editRequestReferenceRoomDTO.setReferenceSize(fileSizes);
            editRequestReferenceRoomDTO.setReferenceType(fileTypes);
            referenceRoomService.updateById(editRequestReferenceRoomDTO, referenceRoomId);

            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }

    }


    @DeleteMapping("/{referenceRoomId}")
    public ResponseEntity<Void> deleteReferenceRoom(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                                    @PathVariable Long referenceRoomId) {
        if(!memberInfo.getId().equals(referenceRoomService.getById(referenceRoomId).getMemberId())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        } else {
            File delDir = new File(path + "/reference/" + referenceRoomId);

            if (delDir.exists()) {
                File[] files = delDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        file.delete();
                    }
                }
                if (delDir.isDirectory() && delDir.list().length == 0) {
                    delDir.delete();
                }
            }
            referenceRoomService.deleteById(referenceRoomId);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }
    }


    @RequestMapping("/down")
    public ResponseEntity<byte[]> downReference(Long id, String referenceName) {
        File f = new File(path + "/reference/" + id + "/" + referenceName);

        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<byte[]> result = null;
        try {
            headers.add("Content-Type", Files.probeContentType(f.toPath()));
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + URLEncoder.encode(referenceName, "utf-8"));
            result = new ResponseEntity<byte[]>(
                    FileCopyUtils.copyToByteArray(f), headers, HttpStatus.OK
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
