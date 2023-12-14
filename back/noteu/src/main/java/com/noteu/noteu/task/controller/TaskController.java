package com.noteu.noteu.task.controller;

import com.noteu.noteu.member.dto.MemberInfo;
import com.noteu.noteu.task.dto.TaskRequestDto;
import com.noteu.noteu.task.dto.TaskResponseDto;
import com.noteu.noteu.task.entity.Task;
import com.noteu.noteu.task.entity.TaskComment;
import com.noteu.noteu.task.service.TaskCommentService;
import com.noteu.noteu.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subjects/{subject-id}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskCommentService taskCommentService;

    // 과제 리스트
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> list(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId){
        ArrayList<TaskResponseDto> task_list = (ArrayList<TaskResponseDto>) taskService.getAll(subjectId);

        if(memberInfo==null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        else if(task_list.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
            return ResponseEntity.ok(task_list);
    }

    // 과제 추가
    @PostMapping
    public ResponseEntity<String> addTask(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                          String taskTitle, String taskContent,
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadLine
                          ){
        TaskRequestDto taskRequestDto = TaskRequestDto.builder()
                .taskTitle(taskTitle)
                .taskContent(taskContent)
                .build();
        if(memberInfo==null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 후 이용해 주세요.");
        else if(!memberInfo.getAuthorities().toString().contains("ROLE_TEACHER"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("선생님만 사용할 수 있는 서비스 입니다.");
        else {
            taskService.save(taskRequestDto, memberInfo.getId(), subjectId, deadLine);
            return ResponseEntity.status(HttpStatus.CREATED).body("과제 생성이 완료되었습니다.");
        }
    }

    // 과제 수정
    @PutMapping("/{task-id}")
    public ResponseEntity<String> editTask(@AuthenticationPrincipal MemberInfo memberInfo, @RequestBody TaskRequestDto taskRequestDto, @PathVariable("subject-id") Long subjectId, @PathVariable("task-id") Long taskId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadLine){
        if(memberInfo==null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 후 이용해 주세요.");
        else if(!memberInfo.getAuthorities().toString().contains("ROLE_TEACHER"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("선생님만 사용할 수 있는 서비스 입니다.");
        else {
            taskService.updateById(taskRequestDto, taskId, deadLine);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("과제 수정이 완료되었습니다.");
        }
    }

    // 과제 삭제
    @PostMapping("/{task-id}")
    public ResponseEntity<String> deleteTask(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId, @PathVariable("task-id") Long taskId){
        if(memberInfo==null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 후 이용해 주세요.");
        else if(!memberInfo.getAuthorities().toString().contains("ROLE_TEACHER"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("선생님만 사용할 수 있는 서비스 입니다.");
        else {
            taskService.delTask(taskId);
            return ResponseEntity.status(HttpStatus.OK).body("과제 삭제가 완료되었습니다.");
        }
    }

}
