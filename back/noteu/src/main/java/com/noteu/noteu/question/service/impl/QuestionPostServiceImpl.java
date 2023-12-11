package com.noteu.noteu.question.service.impl;

import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.member.repository.MemberRepository;
import com.noteu.noteu.question.converter.impl.QuestionPostConverterImpl;
import com.noteu.noteu.question.dto.QuestionPostDTO;
import com.noteu.noteu.question.dto.RecentQuestionDto;
import com.noteu.noteu.question.dto.request.RequestQuestionPostDTO;
import com.noteu.noteu.question.dto.response.DetailResponseQuestionPostDTO;
import com.noteu.noteu.question.dto.response.GetAllResponseQuestionPostDTO;
import com.noteu.noteu.question.entity.QuestionComment;
import com.noteu.noteu.question.entity.QuestionPost;
import com.noteu.noteu.question.repository.QuestionCommentRepository;
import com.noteu.noteu.question.repository.QuestionPostRepository;
import com.noteu.noteu.question.service.QuestionPostService;
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
public class QuestionPostServiceImpl implements QuestionPostService {

    private final QuestionPostRepository questionPostRepository;
    private final QuestionCommentRepository questionCommentRepository;
    private final QuestionPostConverterImpl questionPostConverter;
    private final MemberRepository memberRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public void save(RequestQuestionPostDTO requestQuestionPostDTO, Long subjectId, Long memberId) {

        QuestionPostDTO questionPostDTO = questionPostConverter.addRequestQuestionPostDtoToQuestionPostDto(requestQuestionPostDTO);
        questionPostDTO.setSubject(subjectRepository.getReferenceById(subjectId));
        questionPostDTO.setMember(memberRepository.getReferenceById(memberId));

        QuestionPost questionPost = questionPostConverter.questionPostDtoToQuestionPostEntity(questionPostDTO);
        questionPostRepository.save(questionPost);
    }

    @Transactional(readOnly = true)
    @Override
    public DetailResponseQuestionPostDTO getById(Long questionPostId) {

        QuestionPost questionPost = questionPostRepository.findById(questionPostId)
                .orElseThrow(() -> new EntityNotFoundException("Entity가 존재하지 않습니다." + questionPostId));

        List<QuestionComment> commentList = questionCommentRepository.findByQuestionPost(questionPost);
        DetailResponseQuestionPostDTO detailResponseQuestionPostDTO =
                questionPostConverter.toDetailResponseQuestionPostDto(questionPost, commentList);

        return detailResponseQuestionPostDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetAllResponseQuestionPostDTO> getAll(int page, Long subjectId) {

        Subject subject = subjectRepository.getReferenceById(subjectId);
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<QuestionPost> entityList = questionPostRepository.findBySubject(pageable, subject);
        List<GetAllResponseQuestionPostDTO> dtoList = new ArrayList<>();
        for (QuestionPost questionPost : entityList) {
            GetAllResponseQuestionPostDTO getAllResponseQuestionPostDTO = questionPostConverter.toGetAllResponseReferenceRoomDTO(questionPost);
            dtoList.add(getAllResponseQuestionPostDTO);
        }
        PageImpl<GetAllResponseQuestionPostDTO> pageNationDtoList = new PageImpl<>(dtoList, pageable, entityList.getTotalElements());

        return pageNationDtoList;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetAllResponseQuestionPostDTO> getByTitle(int page, Long subjectId, String title) {

        Subject subject = subjectRepository.getReferenceById(subjectId);
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<QuestionPost> entityList = questionPostRepository.findBySubjectAndQuestionPostTitleContaining(pageable, subject, title);
        List<GetAllResponseQuestionPostDTO> dtoList = new ArrayList<>();
        for (QuestionPost questionPost : entityList) {
            GetAllResponseQuestionPostDTO getAllResponseQuestionPostDTO = questionPostConverter.toGetAllResponseReferenceRoomDTO(questionPost);
            dtoList.add(getAllResponseQuestionPostDTO);
        }
        PageImpl<GetAllResponseQuestionPostDTO> pageNationDtoList = new PageImpl<>(dtoList, pageable, entityList.getTotalElements());

        return pageNationDtoList;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetAllResponseQuestionPostDTO> getByMember(int page, Long subjectId, String memberName) {

        Subject subject = subjectRepository.getReferenceById(subjectId);
        List<Member> memberList = memberRepository.findByMemberNameContaining(memberName);
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        List<QuestionPost> entityList = new ArrayList<>();
        for(Member member : memberList) {
            Page<QuestionPost> memberQuestionPosts = questionPostRepository.findBySubjectAndMember(pageable, subject, member);
            if(memberQuestionPosts.hasContent()) {
                entityList.addAll(memberQuestionPosts.getContent());
            }
        }
        Page<QuestionPost> pageNationEntityList = new PageImpl<>(entityList, pageable, entityList.size());

        List<GetAllResponseQuestionPostDTO> dtoList = new ArrayList<>();
        for (QuestionPost questionPost : pageNationEntityList) {
            GetAllResponseQuestionPostDTO getAllResponseQuestionPostDTO = questionPostConverter.toGetAllResponseReferenceRoomDTO(questionPost);
            dtoList.add(getAllResponseQuestionPostDTO);
        }
        PageImpl<GetAllResponseQuestionPostDTO> pageNationDtoList = new PageImpl<>(dtoList, pageable, pageNationEntityList.getTotalElements());

        return pageNationDtoList;
    }

    @Override
    public void updateById(RequestQuestionPostDTO requestQuestionPostDTO, Long questionPostId) {
        QuestionPost questionPost = questionPostRepository.findById(questionPostId)
                .orElseThrow(() -> new EntityNotFoundException("Entity가 존재하지 않습니다" + questionPostId));

        String newTitle = requestQuestionPostDTO.getQuestionPostTitle();
        String newContent = requestQuestionPostDTO.getQuestionPostContent();

        questionPost.update(newTitle, newContent);
    }

    @Override
    public void deleteById(Long questionPostId) {
        questionPostRepository.deleteById(questionPostId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RecentQuestionDto> getRecentQuestionList(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        // 최근 작성된 글 5개
        Pageable pageable = PageRequest.of(0, 5);
        List<QuestionPost> questionPostList = questionPostRepository.findByMemberOrderByCreatedAtDesc(member, pageable);

        List<RecentQuestionDto> recentQuestionList = new ArrayList<>();
        for (QuestionPost questionPost : questionPostList) {
            Subject subject = questionPost.getSubject();
            List<QuestionComment> questionCommentList = questionCommentRepository.findByQuestionPost(questionPost);

            RecentQuestionDto recentQuestionDto = RecentQuestionDto.builder()
                    .subjectId(subject.getId())
                    .questionId(questionPost.getId())
                    .subjectName(subject.getSubjectName())
                    .questionPostTitle(questionPost.getQuestionPostTitle())
                    .questionPostContent(questionPost.getQuestionPostContent())
                    .commentCount(questionCommentList.size())
                    .createdAt(questionPost.getCreatedAt())
                    .modifiedAt(questionPost.getModifiedAt())
                    .build();
            recentQuestionList.add(recentQuestionDto);
        }

        return recentQuestionList;
    }
}
