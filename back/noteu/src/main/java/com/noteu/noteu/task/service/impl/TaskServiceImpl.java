package com.noteu.noteu.task.service.impl;

import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.member.repository.MemberRepository;
import com.noteu.noteu.subject.entity.Subject;
import com.noteu.noteu.subject.repository.SubjectRepository;
import com.noteu.noteu.task.dto.TaskRequestDto;
import com.noteu.noteu.task.dto.TaskResponseDto;
import com.noteu.noteu.task.entity.Task;
import com.noteu.noteu.task.repository.TaskRepository;
import com.noteu.noteu.task.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final SubjectRepository subjectRepository;
    private final MemberRepository memberRepository;

    @Override
    public void save(TaskRequestDto taskRequestDto, Long memberId, Long subjectId, LocalDateTime deadLine) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Subject subject = subjectRepository.findById(subjectId).orElse(null);
        log.info("member : {}", member);
        log.info("subject : {}", subject);

        taskRepository.save(
                Task.builder()
                    .taskTitle(taskRequestDto.getTaskTitle())
                    .taskContent(taskRequestDto.getTaskContent())
                    .member(member)
                    .subject(subject)
                    .deadLine(deadLine)
                    .build()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public TaskResponseDto getTask(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        return TaskResponseDto.builder()
                .taskTitle(task.getTaskTitle())
                .taskContent(task.getTaskContent())
                .deadLine(task.getDeadLine())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public ArrayList<Task> getAll(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElse(null);
        ArrayList<Task> task_list = (ArrayList<Task>) taskRepository.findTasksBySubject(subjectId);
        return task_list;
    }


    @Override
    public void updateById(TaskRequestDto taskRequestDto, Long taskId, LocalDateTime deadLine) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다."));

        task.updateTask(taskRequestDto.getTaskTitle(), taskRequestDto.getTaskContent(), deadLine);

    }

    @Override
    public void delTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
