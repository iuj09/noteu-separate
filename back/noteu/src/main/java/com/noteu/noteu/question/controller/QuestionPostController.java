package com.noteu.noteu.question.controller;

import com.noteu.noteu.common.CommonUtil;
import com.noteu.noteu.member.dto.MemberInfo;
import com.noteu.noteu.question.dto.request.RequestQuestionPostDTO;
import com.noteu.noteu.question.dto.request.SearchRequestQuestionPostDTO;
import com.noteu.noteu.question.dto.response.DetailResponseQuestionPostDTO;
import com.noteu.noteu.question.dto.response.GetAllResponseQuestionPostDTO;
import com.noteu.noteu.question.service.QuestionPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subjects/{subject-id}/questions")
public class QuestionPostController {

    private final QuestionPostService questionPostService;
    private final CommonUtil commonUtil;

    @GetMapping("add-form")
    public String addForm(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId, Map map) {

        map.put("subjectId", subjectId);

        return "layout/question/add";
    }

    @PostMapping
    public String addQuestionPost(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                  @ModelAttribute RequestQuestionPostDTO requestQuestionPostDTO) {

        String renderedContent = commonUtil.markdown(requestQuestionPostDTO.getQuestionPostContent());
        requestQuestionPostDTO.setQuestionPostContent(renderedContent);
        questionPostService.save(requestQuestionPostDTO, subjectId, memberInfo.getId());

        return "redirect:/subjects/{subject-id}/questions";
    }

    @GetMapping
    public String questionPostList(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                   @RequestParam(value="page", defaultValue="0") int page, Map map) {
        Page<GetAllResponseQuestionPostDTO> dtoList = questionPostService.getAll(page, subjectId);
        map.put("questionPost", dtoList);
        map.put("subjectId", subjectId);

        return "layout/question/list";
    }

    @PostMapping("/search")
    public String getQuestionPostBySearchWord(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                              @RequestParam(value="page", defaultValue="0") int page, @ModelAttribute SearchRequestQuestionPostDTO searchRequestQuestionPostDTO, Map map) {
        Page<GetAllResponseQuestionPostDTO> dtoList = null;
        if(searchRequestQuestionPostDTO.getSearchType().equals("title")) {
            dtoList = questionPostService.getByTitle(page, subjectId, searchRequestQuestionPostDTO.getSearchWord());
        } else if (searchRequestQuestionPostDTO.getSearchType().equals("member")) {
            dtoList = questionPostService.getByMember(page, subjectId, searchRequestQuestionPostDTO.getSearchWord());
        }
        map.put("questionPost", dtoList);
        map.put("subjectId", subjectId);

        return "layout/question/list";
    }

    @GetMapping("/{questionPostId}")
    public String getQuestionPostById(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
            @PathVariable Long questionPostId, Map map) {
        try{
            DetailResponseQuestionPostDTO detailResponseQuestionPostDTO = questionPostService.getById(questionPostId);
            String renderedContent = commonUtil.markdown(detailResponseQuestionPostDTO.getQuestionPostContent());
            detailResponseQuestionPostDTO.setQuestionPostContent(renderedContent);
            map.put("questionPost", detailResponseQuestionPostDTO);
        } catch (Exception e) {
            System.out.println(e);
        }
        map.put("subjectId", subjectId);

        return "layout/question/detail";
    }

    @GetMapping("/edit-form/{questionPostId}")
    public String updateForm(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                             @PathVariable Long questionPostId ,Map map) {
        try {
            DetailResponseQuestionPostDTO detailResponseQuestionPostDTO = questionPostService.getById(questionPostId);
            map.put("questionPost", detailResponseQuestionPostDTO);
        } catch (Exception e) {
            System.out.println(e);
        }

        map.put("subjectId", subjectId);

        return "layout/question/edit";
    }

    @PostMapping("/edit/{questionPostId}")
    public String updateQuestionPostById(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                         @PathVariable Long questionPostId, RequestQuestionPostDTO requestQuestionPostDTO){
        questionPostService.updateById(requestQuestionPostDTO, questionPostId);

        return "redirect:/subjects/{subject-id}/questions";
    }

    @GetMapping("/delete/{questionPostId}")
    public String deleteQuestionPostById(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                         @PathVariable Long questionPostId){
        questionPostService.deleteById(questionPostId);

        return "redirect:/subjects/{subject-id}/questions";
    }
}
