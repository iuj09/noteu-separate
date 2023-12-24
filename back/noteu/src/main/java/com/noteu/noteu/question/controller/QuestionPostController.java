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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/subjects/{subject-id}/questions")
public class QuestionPostController {

    private final QuestionPostService questionPostService;
    private final CommonUtil commonUtil;

    @PostMapping
    public ResponseEntity<Void> addQuestionPost(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                  @RequestBody RequestQuestionPostDTO requestQuestionPostDTO) {
        if(memberInfo == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        } else {
            String renderedContent = commonUtil.markdown(requestQuestionPostDTO.getQuestionPostContent());
            requestQuestionPostDTO.setQuestionPostContent(renderedContent);
            questionPostService.save(requestQuestionPostDTO, subjectId, memberInfo.getId());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    public ResponseEntity<Page<GetAllResponseQuestionPostDTO>> questionPostList(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                   @RequestParam(value="page", defaultValue="0") int page) {
        if(memberInfo == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        } else {
            Page<GetAllResponseQuestionPostDTO> dtoList = questionPostService.getAll(page, subjectId);
            return ResponseEntity.ok(dtoList);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<Page<GetAllResponseQuestionPostDTO>> getQuestionPostBySearchWord(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                              @RequestParam(value="page", defaultValue="0") int page, @RequestBody SearchRequestQuestionPostDTO searchRequestQuestionPostDTO) {
        if(memberInfo == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        } else {
            Page<GetAllResponseQuestionPostDTO> dtoList = null;
            if(searchRequestQuestionPostDTO.getSearchType().equals("title")) {
                dtoList = questionPostService.getByTitle(page, subjectId, searchRequestQuestionPostDTO.getSearchWord());
            } else if (searchRequestQuestionPostDTO.getSearchType().equals("member")) {
                dtoList = questionPostService.getByMember(page, subjectId, searchRequestQuestionPostDTO.getSearchWord());
            }

            return ResponseEntity.ok(dtoList);
        }
    }

    @GetMapping("/{questionPostId}")
    public ResponseEntity<DetailResponseQuestionPostDTO> getQuestionPostById(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
            @PathVariable Long questionPostId) {
        if(memberInfo == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        } else {
            DetailResponseQuestionPostDTO detailResponseQuestionPostDTO = questionPostService.getById(questionPostId);
            String renderedContent = commonUtil.markdown(detailResponseQuestionPostDTO.getQuestionPostContent());
            detailResponseQuestionPostDTO.setQuestionPostContent(renderedContent);
            return ResponseEntity.ok(detailResponseQuestionPostDTO);
        }

    }


    @PutMapping("/{questionPostId}")
    public ResponseEntity<Void> updateQuestionPostById(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                         @PathVariable Long questionPostId, @RequestBody RequestQuestionPostDTO requestQuestionPostDTO){
        if(memberInfo == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        } else if(memberInfo.getId() != questionPostService.getById(questionPostId).getMemberId()) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        } else {
            questionPostService.updateById(requestQuestionPostDTO, questionPostId);

            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }
    }

    @DeleteMapping("/{questionPostId}")
    public ResponseEntity<Void> deleteQuestionPostById(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                         @PathVariable Long questionPostId){
        if(memberInfo == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        } else if(memberInfo.getId() != questionPostService.getById(questionPostId).getMemberId()) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }
        questionPostService.deleteById(questionPostId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
