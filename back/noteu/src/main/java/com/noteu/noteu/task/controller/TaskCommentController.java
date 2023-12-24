package com.noteu.noteu.task.controller;

import com.noteu.noteu.member.dto.MemberInfo;
import com.noteu.noteu.task.dto.TaskCommentRequestDto;
import com.noteu.noteu.task.service.TaskCommentService;
import com.noteu.noteu.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subjects/{subject-id}/tasks")
public class TaskCommentController {

    private final TaskCommentService taskCommentService;

    @Value("${spring.servlet.multipart.location}")
    private String path;

    // 과제 제출
    @PostMapping(value = "/{task-id}/task-comment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addTaskComment(@AuthenticationPrincipal MemberInfo memberInfo,
                                 @PathVariable("subject-id") Long subjectId, @PathVariable("task-id") Long taskId,
                                 @RequestPart("taskCommentTitle") String taskCommentTitle, @RequestPart("taskCommentFile") MultipartFile taskCommentFile
    ){

        String fileName = taskCommentFile.getOriginalFilename();

        File directory = new File(path + "/task/");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                log.info("Directory is created!");
            } else {
                log.error("Failed to create directory!");
                throw new RuntimeException("Failed to create directory for task files");
            }
        }

        if(memberInfo==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후 이용해 주세요.");
        else if(taskCommentFile == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일이 선택 되지 않았습니다.");
        else if(taskCommentTitle == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("내용을 입력해주세요.");
        else {

            File newFile = new File(directory, fileName);
            try {
                taskCommentFile.transferTo(newFile);

                TaskCommentRequestDto taskCommentRequestDto = TaskCommentRequestDto.builder()
                        .taskCommentTitle(taskCommentTitle)
                        .taskCommentFileName(fileName)
                        .build();
                taskCommentService.save(taskCommentRequestDto, taskId, memberInfo.getId());
                return ResponseEntity.status(HttpStatus.CREATED).body("과제 제출이 완료되었습니다.");

            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("과제 제출에 실패하였습니다.");
            }
        }
    }

    // 과제 삭제
    @PostMapping("/{task-id}/{task-comment-id}")
    public ResponseEntity<String> deleteTaskComment(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId, @PathVariable("task-id") Long taskId, @PathVariable("task-comment-id") Long taskCommentId){
        if(memberInfo==null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 후 이용해 주세요.");
        else {
            taskCommentService.deleteTaskComment(taskCommentId);
            return ResponseEntity.status(HttpStatus.OK).body("과제 삭제가 완료되었습니다.");
        }
    }

    @RequestMapping("/down")
    public ResponseEntity<byte[]> downReference(String fileName) {
        File f = new File(path + "/task/" + fileName);

        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<byte[]> result = null;
        try {
            headers.add("Content-Type", Files.probeContentType(f.toPath()));
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""+ URLEncoder.encode(fileName, "utf-8"));
            result = new ResponseEntity<byte[]>(
                    FileCopyUtils.copyToByteArray(f), headers, HttpStatus.OK
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
