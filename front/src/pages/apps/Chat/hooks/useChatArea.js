import {useCallback, useEffect, useState} from 'react';
import * as yup from 'yup';
import avatar from '@/assets/images/users/avatar-1.jpg';
import axios from 'axios';
import useChatUsers from './useChatUsers';
import {formatLocalDateTime, sendMessage} from './chat';

export const chatSchema = yup.object().shape({
    newMessage: yup.string().required('Please enter your messsage'),
})

export default function useChatArea(selectedUser, setMessages) {
    const {loginMemberName, loginId} = useChatUsers();
    const [toUser, setToUser] = useState({
        id: loginId,
        name: loginMemberName,
        avatar: avatar
    });

    console.log("loginMemberName :" + loginMemberName);
    console.log("loginId :" + loginId);

    /*
     *  Fetches the messages for selected user
     */
    const getMessagesForUser = useCallback(() => {
        const subjectId = 1;

        if (selectedUser) {
            const response = axios.get(`http://localhost:8081/subjects/${subjectId}/chats/rooms/api?roomId=${selectedUser.id}`, {
                headers: {
                    Authorization: `Bearer_eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwcm9maWxlIjoiL2ZpbGUvcHJvZmlsZS9kZWZhdWx0LnBuZyIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJtZW1iZXJOYW1lIjoibmFtZUEiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWFhYSIsImlhdCI6MTcwMjg2ODEyMCwiZXhwIjo3NzAyODY4MDYwfQ.RX-VkBUbWn7OxDVu5DEe6jCOOEOMDDfBYHOdg-oqPk0`
                }
            })
                .then(response => {
                    let chats = [];
                    console.log(response.data);

                    response.data.forEach(chat => {
                        const sendOn = formatLocalDateTime(chat.createdAt);
                        const message = chat.message;
                        const roomId = chat.roomId;
                        const senderId = chat.senderId;
                        const senderName = chat.senderName;

                        chats.push({
                            roomId,
                            from: {
                                senderId: senderId,
                                name: senderName,
                            },
                            message: {
                                type: 'text',
                                value: {text: message}
                            },
                            sendOn
                        });
                    });

                    console.log("chats : " + JSON.stringify(chats));

                    setMessages(
                        chats
                    );
                })
                .catch(error => {
                    console.error('There was an error!', error);
                });


        }
    }, [selectedUser]);

    useEffect(() => {
        getMessagesForUser();
    }, [getMessagesForUser]);

    /**
     * sends the chat message
     */
    const sendChatMessage = (value) => {
        sendMessage(selectedUser, value.newMessage, loginId, loginMemberName);
        // 메시지 전송 후에 input 비우기
        document.getElementById('chat-form').reset();
    };

    return {
        loginId,
        loginMemberName,
        toUser,
        sendChatMessage,
        recvChat,
    };
}

export const recvChat = (recv) => {
    console.log("리비스 받은값 이거 찍여야됨 : " + JSON.stringify(recv));

    // let newUserMessages = [...userMessages];
    // newUserMessages.push({
    // 	id: recv.roomId,
    // 	from: {
    // 		id: recv.senderId,
    // 		name: recv.senderName,
    // 	},
    // 	message: { type: 'text', value: { text: recv.message } },
    // 	sendOn: formatLocalDateTime(recv.createdAt),
    // });

    // setUserMessages(newUserMessages);
}
