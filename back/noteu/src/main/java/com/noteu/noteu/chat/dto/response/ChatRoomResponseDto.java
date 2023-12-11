package com.noteu.noteu.chat.dto.response;

import com.noteu.noteu.chat.dto.ChatRoomResponse;
import com.noteu.noteu.member.dto.response.MemberResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomResponseDto implements ChatRoomResponse {
    Long id;
    Long subjectId;
    String lastMessage;
    LocalDateTime lastMessageDateTime;
    List<MemberResponseDto> participants = new ArrayList<>();
}
