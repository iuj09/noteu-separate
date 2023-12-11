package com.noteu.noteu.question.converter;

import com.noteu.noteu.question.dto.QuestionCommentDTO;
import com.noteu.noteu.question.dto.QuestionPostDTO;
import com.noteu.noteu.question.dto.request.RequestQuestionCommentDTO;
import com.noteu.noteu.question.dto.request.RequestQuestionPostDTO;
import com.noteu.noteu.question.dto.response.DetailResponseQuestionPostDTO;
import com.noteu.noteu.question.dto.response.GetAllResponseQuestionPostDTO;
import com.noteu.noteu.question.dto.response.ResponseQuestionCommentDTO;
import com.noteu.noteu.question.entity.QuestionComment;
import com.noteu.noteu.question.entity.QuestionPost;

import java.util.List;
import java.util.stream.Collectors;

public interface QuestionPostConverter {

    /**
     *
     * @param requestQuestionPostDTO
     * @return QuestionPostDTO
     */
    default QuestionPostDTO addRequestQuestionPostDtoToQuestionPostDto(RequestQuestionPostDTO requestQuestionPostDTO) {
        return QuestionPostDTO.builder()
                .questionPostTitle(requestQuestionPostDTO.getQuestionPostTitle())
                .questionPostContent(requestQuestionPostDTO.getQuestionPostContent())
                .build();
    }

    /**
     *
     * @param questionPostDTO
     * @return QuestionPost (Entity)
     */
    default QuestionPost questionPostDtoToQuestionPostEntity(QuestionPostDTO questionPostDTO) {
        return QuestionPost.builder()
                .subject(questionPostDTO.getSubject())
                .member(questionPostDTO.getMember())
                .questionPostTitle(questionPostDTO.getQuestionPostTitle())
                .questionPostContent(questionPostDTO.getQuestionPostContent())
                .build();
    }

    /**
     *
     * @param requestQuestionCommentDTO
     * @return
     */
    default QuestionComment requestQuestionCommentDtoToQuestionCommentEntity(RequestQuestionCommentDTO requestQuestionCommentDTO) {
        return QuestionComment.builder()
                .questionPost(requestQuestionCommentDTO.getQuestionPost())
                .member(requestQuestionCommentDTO.getMember())
                .questionCommentContent(requestQuestionCommentDTO.getQuestionCommentContent())
                .build();
    }

    /**
     *
     * @param questionComment
     * @return ResponseQuestionCommentDTO
     */
    default ResponseQuestionCommentDTO questionCommentEntityToResponseQuestionCommentDTO(QuestionComment questionComment) {
        return ResponseQuestionCommentDTO.builder()
                .questionCommentId(questionComment.getId())
                .questionPost(questionComment.getQuestionPost())
                .memberId(questionComment.getMember().getId())
                .memberName(questionComment.getMember().getMemberName())
                .memberRole(questionComment.getMember().getRole())
                .profile(questionComment.getMember().getProfile())
                .questionCommentContent(questionComment.getQuestionCommentContent())
                .createdAt(questionComment.getCreatedAt())
                .modifiedAt(questionComment.getModifiedAt())
                .build();
    }

    /**
     *
     * @param questionPost
     * @param questionComments
     * @return DetailResponseQuestionPostDTO
     */
    default DetailResponseQuestionPostDTO toDetailResponseQuestionPostDto(QuestionPost questionPost, List<QuestionComment> questionComments) {

        List<QuestionComment> matchingComments = questionComments.stream()
                .filter(questionComment -> questionComment.getQuestionPost().getId() == questionPost.getId())
                .toList();

        List<ResponseQuestionCommentDTO> responseQuestionCommentDTOList = matchingComments.stream()
                .map(this::questionCommentEntityToResponseQuestionCommentDTO)
                .collect(Collectors.toList());

        return DetailResponseQuestionPostDTO.builder()
                .questionPostId(questionPost.getId())
                .subject(questionPost.getSubject())
                .memberId(questionPost.getMember().getId())
                .memberName(questionPost.getMember().getMemberName())
                .profile(questionPost.getMember().getProfile())
                .questionPostTitle(questionPost.getQuestionPostTitle())
                .questionPostContent(questionPost.getQuestionPostContent())
                .comment(responseQuestionCommentDTOList)
                .commentCount(responseQuestionCommentDTOList.size())
                .createdAt(questionPost.getCreatedAt())
                .modifiedAt(questionPost.getModifiedAt())
                .build();
    }

    default GetAllResponseQuestionPostDTO toGetAllResponseReferenceRoomDTO(QuestionPost questionPost) {
        return GetAllResponseQuestionPostDTO.builder()
                .questionPostId(questionPost.getId())
                .subject(questionPost.getSubject())
                .memberId(questionPost.getMember().getId())
                .memberName(questionPost.getMember().getMemberName())
                .profile(questionPost.getMember().getProfile())
                .questionPostTitle(questionPost.getQuestionPostTitle())
                .createdAt(questionPost.getCreatedAt())
                .build();
    }
}
