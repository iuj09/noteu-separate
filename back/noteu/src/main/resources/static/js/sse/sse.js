import {connect, findAllRoom} from '../chat/chat.js';
import {pastChat} from '../chat/chat.js';
import {subjectId} from '../chat/chat.js';
import {senderName} from '../chat/chat.js';

const eventSource = new EventSource("/sse");

eventSource.addEventListener('connect', e => {
    const {data: receivedCount} = e;
    console.log("count event data", receivedCount);
});

eventSource.addEventListener('newChatRoom', e => {
    const {data: memberInfo} = e;
    console.log("memberInfo : " + memberInfo);
    let parseMemberInfo = JSON.parse(memberInfo);

    const eventSubjectId = parseMemberInfo.subjectId;
    console.log("eventSubjectId : " + eventSubjectId);
    console.log("getSubjectId : " + subjectId);

    if (String(eventSubjectId) === String(subjectId)) {
        console.log("여기 들어왔단거는 잘 실행된거임");
        const memberId = parseMemberInfo.memberId;
        const friendId = parseMemberInfo.friendId;
        const roomId = parseMemberInfo.roomId;

        connect(roomId, senderName);
        pastChat(roomId, friendId, memberId);
        findAllRoom(subjectId, null);
    }
});

eventSource.onerror = function (event) {
    console.error("Error: ", event);
};
