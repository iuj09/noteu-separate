package com.noteu.noteu.chat.controller;

import com.noteu.noteu.chat.dto.ChatRoomResponse;
import com.noteu.noteu.chat.dto.response.ChatMessageResponseDto;
import com.noteu.noteu.chat.dto.response.ChatRoomInfoResponseDto;
import com.noteu.noteu.chat.dto.response.ChatRoomResponseDto;
import com.noteu.noteu.chat.service.RestChatService;
import com.noteu.noteu.member.dto.MemberInfo;
import com.noteu.noteu.member.dto.response.MemberResponseDto;
import com.noteu.noteu.sse.SseEmitterService;
import com.noteu.noteu.sse.dto.SseNewChatDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("subjects/{subject-id}/chats")
public class ChatRoomController {

    private final RestChatService restChatService;
    private final SseEmitterService sseEmitterService;

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    public ResponseEntity<ChatRoomInfoResponseDto> getAllRoom(@PathVariable("subject-id") Long subjectId,
                                                              @AuthenticationPrincipal MemberInfo memberInfo) {

        ChatRoomInfoResponseDto res = restChatService.findAllById(subjectId, memberInfo);

        return ResponseEntity.ok(res);
    }

    // 같은반 친구들 목록 반환
    @GetMapping("/friends")
    public ResponseEntity<List<MemberResponseDto>> getAllFriend(@PathVariable("subject-id") Long subjectId,
                                                                @AuthenticationPrincipal MemberInfo memberInfo) {
        List<MemberResponseDto> allSubjectsBySubjectId = restChatService.findAllSubjectsBySubjectId(subjectId, memberInfo.getId());
        log.info("멤버 정보들을 다 가져옵니다. {}", allSubjectsBySubjectId);

        return ResponseEntity.ok(allSubjectsBySubjectId);
    }

    @PostMapping("/rooms")
    public ResponseEntity<ChatRoomResponse> createRoom(@RequestBody Map<String, Long> requestBody,
                                                       @AuthenticationPrincipal MemberInfo memberInfo,
                                                       @PathVariable("subject-id") Long subjectId) {
        Long friendId = requestBody.get("friendId");
        ChatRoomResponse chatRoomResponseDto = restChatService.createRoom(subjectId, friendId, memberInfo.getId());

        if (chatRoomResponseDto instanceof ChatRoomResponseDto) {
            Long roomId = ((ChatRoomResponseDto) chatRoomResponseDto).getId();
            SseNewChatDto sseNewChatDto = SseNewChatDto.builder()
                    .memberId(friendId)
                    .friendId(memberInfo.getId())
                    .subjectId(subjectId)
                    .roomId(roomId)
                    .build();
            log.info("sseNewChatDto 생성 확인 : {}", sseNewChatDto);
            sseEmitterService.newChatRoom(sseNewChatDto);
        }

        return ResponseEntity
                .created(URI.create("/subjects/" + subjectId + "/chats"))
                .body(chatRoomResponseDto);
    }

    @GetMapping("/rooms/api")
    public ResponseEntity<List<ChatMessageResponseDto>> pastChat(@RequestParam Long roomId) {
        List<ChatMessageResponseDto> chatMessageResponseDtos = restChatService.findPastChat(roomId);
        log.info("이전 채팅 불러오기 : {}", chatMessageResponseDtos);

        return ResponseEntity.ok(chatMessageResponseDtos);
    }
}
