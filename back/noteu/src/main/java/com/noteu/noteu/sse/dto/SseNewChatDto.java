package com.noteu.noteu.sse.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SseNewChatDto {
    Long memberId;
    Long friendId;
    Long subjectId;
    Long roomId;
}
