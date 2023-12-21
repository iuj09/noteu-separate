import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { recvChat } from './useChatArea';  // 파일 경로에 맞게 수정

export function sendMessage(selectedUser, newMessage, loginId, loginMemberName) {
    ws.send("/app/chat/message", {}, JSON.stringify({
        messageType: 'TALK',
        roomId: selectedUser.id,
        senderId: loginId,
        senderName: loginMemberName,
        message: newMessage
    }));
}

export function recvMessage(recv) {
    console.log("리시브로 받은 메시지입니다. : " + JSON.stringify(recv));

    recvChat(recv);
}

export let sock;
export let ws;

let reconnect;
export function connect(roomId, senderName) {
    console.log(`roomId : ${roomId}`);
    console.log(`senderName : ${senderName}`);
    console.log("소켓 연결 시작");
    sock = new SockJS("http://localhost:8081/ws/chat");
    ws = Stomp.over(sock);

    // 연결 시도 함수
    function tryConnect() {
        console.log("연결 시도 중...");
        sock = new SockJS("/ws/chat");
        ws = Stomp.over(sock);
        connect(roomId, senderName); // 다시 connect 함수 호출
    }

    ws.connect({}, function () {
        setTimeout(function () {
            ws.subscribe("/topic/chats/room/" + roomId, function (message) {
                console.log("메시지를 받았어요!~~~~~~~~~");
                const recv = JSON.parse(message.body)
                recvMessage(recv);
            });

            ws.send("/app/chat/message", {}, JSON.stringify({
                messageType: 'ENTER',
                roomId: roomId,
                senderName: senderName
            }));
        }, 1000);

        // 성공적인 연결 시 재연결 횟수를 리셋
        reconnect = 0;
    }, function (error) {
        if (reconnect++ <= 5) {
            console.log("연결 실패");
            setTimeout(tryConnect, 10 * 1000); // 10초 후에 다시 시도
        } else {
            console.log("연결 재시도 횟수 초과");
        }
    });
}

export function formatLocalDateTime(parsedTime) {
    const date = new Date(parsedTime);
    const hours = date.getHours();
    const minutes = date.getMinutes();
    const ampm = hours >= 12 ? 'pm' : 'am';
    const twelveHourFormat = (hours % 12) || 12;
    const formattedTime = twelveHourFormat + ':' + (minutes < 10 ? '0' : '') + minutes + ' ' + ampm;

    return formattedTime;
}
