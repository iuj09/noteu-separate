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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subjects/{subject-id}/tasks")
public class TaskCommentController {

    private final TaskService taskService;
    private final TaskCommentService taskCommentService;

    @Value("${spring.servlet.multipart.location}")
    private String path;

    @PostMapping("/{task-id}/task-comment")
    public String addTaskComment(@AuthenticationPrincipal MemberInfo memberInfo,
                                 @PathVariable("subject-id") Long subjectId, @PathVariable("task-id") Long taskId,
                                 String taskCommentTitle, MultipartFile taskCommentFile
    ){

        log.info("taskCommentFile: {}", taskCommentFile);
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

        File newFile = new File(directory, fileName);
        try {
            taskCommentFile.transferTo(newFile);

            TaskCommentRequestDto taskCommentRequestDto = TaskCommentRequestDto.builder()
                    .taskCommentTitle(taskCommentTitle)
                    .taskCommentFileName(fileName)
                    .build();
            taskCommentService.save(taskCommentRequestDto, taskId, memberInfo.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/subjects/{subject-id}/tasks/{task-id}";
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

    @PostMapping("/{task-id}/{task-comment-id}")
    public String deleteTaskComment(@PathVariable("subject-id") Long subjectId, @PathVariable("task-id") Long taskId, @PathVariable("task-comment-id") Long taskCommentId){
        taskCommentService.deleteTaskComment(taskCommentId);

        return "redirect:/subjects/{subject-id}/tasks/{task-id}";
    }

}
