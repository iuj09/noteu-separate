package com.noteu.noteu.task.service.impl;

import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.member.repository.MemberRepository;
import com.noteu.noteu.task.dto.TaskCommentRequestDto;
import com.noteu.noteu.task.dto.TaskCommentResponseDto;
import com.noteu.noteu.task.entity.Task;
import com.noteu.noteu.task.entity.TaskComment;
import com.noteu.noteu.task.repository.TaskCommentRepository;
import com.noteu.noteu.task.repository.TaskRepository;
import com.noteu.noteu.task.service.TaskCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TaskCommentServiceImpl implements TaskCommentService {

    private final TaskCommentRepository taskCommentRepository;
    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    @Override
    public void save(TaskCommentRequestDto taskCommentRequestDto, Long taskId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Task task = taskRepository.findById(taskId).orElse(null);

        taskCommentRepository.save(
                TaskComment.builder()
                        .task(task)
                        .member(member)
                        .taskCommentTitle(taskCommentRequestDto.getTaskCommentTitle())
                        .taskCommentFileName(taskCommentRequestDto.getTaskCommentFileName())
                        .build()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskComment> getAllTaskComment(Long taskId, Long memberId) {
        return taskCommentRepository.findByTaskAndMember(taskId, memberId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskComment> getAll(Long taskId) {
        return taskCommentRepository.findByTask(taskId);
    }

    @Override
    public void updateById(TaskCommentRequestDto taskCommentRequestDto, Long taskCommentId) {
        TaskComment taskComment = taskCommentRepository.findById(taskCommentId).orElse(null);

        taskComment.updateTaskComment(taskCommentRequestDto.getTaskCommentTitle(), taskComment.getTaskCommentFileName());
    }

    @Override
    public void deleteTaskComment(Long taskCommentId) {
        taskCommentRepository.deleteById(taskCommentId);
    }
}
