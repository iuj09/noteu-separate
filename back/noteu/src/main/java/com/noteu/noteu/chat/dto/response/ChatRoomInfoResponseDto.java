package com.noteu.noteu.chat.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomInfoResponseDto {
    List<ChatRoomResponseDto> chatRoomResponseDtos;
    Long loginId;
}
