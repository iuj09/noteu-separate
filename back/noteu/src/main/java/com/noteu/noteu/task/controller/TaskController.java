package com.noteu.noteu.task.controller;

import com.noteu.noteu.member.dto.MemberInfo;
import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.notice.dto.NoticeResponseDto;
import com.noteu.noteu.subject.dto.SubjectResponseDto;
import com.noteu.noteu.subject.entity.Subject;
import com.noteu.noteu.subject.service.SubjectService;
import com.noteu.noteu.task.dto.TaskCommentResponseDto;
import com.noteu.noteu.task.dto.TaskRequestDto;
import com.noteu.noteu.task.dto.TaskResponseDto;
import com.noteu.noteu.task.entity.Task;
import com.noteu.noteu.task.entity.TaskComment;
import com.noteu.noteu.task.service.TaskCommentService;
import com.noteu.noteu.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subjects/{subject-id}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskCommentService taskCommentService;

    @GetMapping("/add-form")
    public String addForm(@PathVariable("subject-id") Long subjectId, Model m){

        m.addAttribute("subjectId", subjectId);

        return "layout/task/add";
    }

    @GetMapping
    public String list(@PathVariable("subject-id") Long subjectId, Model m){
        ArrayList<Task> task_list = (ArrayList<Task>) taskService.getAll(subjectId);

        m.addAttribute("task_list", task_list);
        m.addAttribute("subjectId", subjectId);

        return "layout/task/list";
    }

    @PostMapping
    public String addTask(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                          String taskTitle, String taskContent,
                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadLine
                          ){
        TaskRequestDto taskRequestDto = TaskRequestDto.builder()
                .taskTitle(taskTitle)
                .taskContent(taskContent)
                .build();
        taskService.save(taskRequestDto, memberInfo.getId(), subjectId, deadLine);

        return "redirect:/subjects/{subject-id}/tasks";
    }

    @GetMapping("/{task-id}")
    public String detailForm(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId, @PathVariable("task-id") Long taskId, Model m){
        TaskResponseDto taskResponseDto = taskService.getTask(taskId);
        List<TaskComment> taskCommentList;
        if (memberInfo.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_TEACHER"))) {
            taskCommentList = taskCommentService.getAll(taskId);
        } else {
            taskCommentList = taskCommentService.getAllTaskComment(taskId, memberInfo.getId());
        }

        m.addAttribute("task", taskResponseDto);
        m.addAttribute("taskId", taskId);
        m.addAttribute("subjectId", subjectId);
        m.addAttribute("taskCommentList", taskCommentList);

        return "layout/task/detail";
    }

    @GetMapping("/{task-id}/edit-form")
    public String editForm(@PathVariable("subject-id") Long subjectId, @PathVariable("task-id") Long taskId, Model m){
        TaskResponseDto taskResponseDto = taskService.getTask(taskId);

        m.addAttribute("task", taskResponseDto);
        m.addAttribute("subjectId", subjectId);
        m.addAttribute("taskId", taskId);

        return "layout/task/edit";
    }

    @PostMapping("/edit/{task-id}")
    public String editTask(TaskRequestDto taskRequestDto, @PathVariable("subject-id") Long subjectId, @PathVariable("task-id") Long taskId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadLine){
        taskService.updateById(taskRequestDto, taskId, deadLine);

        return "redirect:/subjects/{subject-id}/tasks";
    }

    @PostMapping("/{task-id}")
    public String deleteTask(@PathVariable("subject-id") Long subjectId, @PathVariable("task-id") Long taskId){
        taskService.delTask(taskId);

        return "redirect:/subjects/{subject-id}/tasks";
    }

}
