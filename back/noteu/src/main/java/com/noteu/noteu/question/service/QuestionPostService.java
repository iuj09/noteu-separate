package com.noteu.noteu.question.service;


import com.noteu.noteu.question.dto.RecentQuestionDto;
import com.noteu.noteu.question.dto.request.RequestQuestionPostDTO;
import com.noteu.noteu.question.dto.response.DetailResponseQuestionPostDTO;
import com.noteu.noteu.question.dto.response.GetAllResponseQuestionPostDTO;
import com.noteu.noteu.question.entity.QuestionPost;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuestionPostService {

    void save(RequestQuestionPostDTO RequestQuestionPostDTO, Long subjectId, Long memberId);

    DetailResponseQuestionPostDTO getById(Long questionPostId);

    Page<GetAllResponseQuestionPostDTO> getAll(int page, Long subjectId);

    Page<GetAllResponseQuestionPostDTO> getByTitle(int page, Long subjectId, String title);

    Page<GetAllResponseQuestionPostDTO> getByMember(int page, Long subjectId, String memberName);

    void updateById(RequestQuestionPostDTO requestQuestionPostDTO, Long questionPostId);

    void deleteById(Long questionPostId);

    List<RecentQuestionDto> getRecentQuestionList(Long memberId);

}
