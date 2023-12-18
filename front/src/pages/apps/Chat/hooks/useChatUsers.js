import {useEffect, useState} from 'react';
import axios from 'axios';
import {connect, formatLocalDateTime, recvMessage, sock, ws} from './chat';
import SockJS from "sockjs-client";
import {Stomp} from "@stomp/stompjs";

export default function useChatUsers(onUserSelect, setMessages) {
  const [loginMemberName, setLoginMemberName] = useState();
  const [loginId, setLoginId] = useState();  // 상태로 관리하도록 변경
  const [user, setUser] = useState([]);
  const [users, setUsers] = useState([]);
  const [friends, setFriends] = useState([]);
  const [selectedUser, setSelectedUser] = useState();
  const [roomId, setRoomId] = useState(); // roomId 상태 추가
  const [selectedGroup, setSelectedGroup] = useState('Chats');


  useEffect(() => {
  const subjectId = 1;
  
    axios.get(`http://localhost:8081/subjects/${subjectId}/chats/rooms`, {
      headers: {
        Authorization: `Bearer_eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwcm9maWxlIjoiL2ZpbGUvcHJvZmlsZS9kZWZhdWx0LnBuZyIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJtZW1iZXJOYW1lIjoibmFtZUEiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWFhYSIsImlhdCI6MTcwMjg2ODEyMCwiZXhwIjo3NzAyODY4MDYwfQ.RX-VkBUbWn7OxDVu5DEe6jCOOEOMDDfBYHOdg-oqPk0`
      }
    })
      .then(response => {
        let rooms = [];
        console.log("testetst" + JSON.stringify(response.data));
        setLoginId(response.data.loginId); 
        setLoginMemberName(response.data.loginMemberName);

        response.data.chatRoomResponseDtos.forEach(room => {
          const id = room.id;
          const participants = room.participants; // 참가자 배열
          const email = participants[0].email;
          const friendId = participants[0].id;
          const avatar = participants[0].profile;
          const phone = participants[0].tel;
          const name = participants[0].membername;
          const lastMessage = room.lastMessage;
          const lastMessageOn = room.lastMessageDateTime;

          connect(id, loginMemberName);

          console.log('roomId : ' + room.id);
          rooms.push({
            id,
            email,
            friendId,
            avatar,
            phone,
            name,
            lastMessage,
            lastMessageOn
          });
        });

        setUsers(rooms);
        setUser(rooms);
        setSelectedUser(rooms[0]);
        console.log("rooms : " + rooms);
      })
      .catch(error => {
        console.error('There was an error!', error);
      });
  }, []);

  useEffect(() => {
    const sock = new SockJS("http://localhost:8081/ws/chat");
    const ws = Stomp.over(sock);

    ws.connect({}, () => {
      ws.subscribe(`/topic/chats/room/${roomId}`, (message) => {
        const parse = JSON.parse(message.body);
        console.log("roomId : " + roomId);
        if (parse.messageType !== "ENTER") {
          setMessages((oldMessages) => [...oldMessages, {
            roomId : parse.roomId,
            from: {
              senderId: parse.senderId,
              name: parse.senderName,
            },
            message: {
              type: 'text',
              value: {text: parse.message}
            },
            sendOn : formatLocalDateTime(parse.createdAt)
          }]);
        }
      });
    });

    // 방에 들어갈 때마다 roomId를 설정
    setRoomId(roomId);

    // ws.subscribe("/topic/chats/room/" + "1", message => {
      console.log("메시지를 받았어요!~~~~~~~~~");
      // const recv = JSON.parse(message.body)
      // recvMessage(recv);
    // });
  }, [roomId]);

  /**
   * Filter users
   */
  const filterUsers = (group) => {
    setSelectedGroup(group);
    setUser(
      group !== 'Chats'
        ? [...users].filter((u) => u.groups.toLowerCase().indexOf(group.toLowerCase()) >= 0)
        : [...users]
    );
  };

  /**
   * Search the user  
   * @param {*} text
   */
  const search = (text) => {
    setUser(
      text
        ? [...users].filter((u) => u.name.toLowerCase().indexOf(text.toLowerCase()) >= 0)
        : [...users]
    );
  };

  /**
   * Activates the user
   * @param {*} user
   */
  const activateUser = (user) => {
    setSelectedUser(user);

    // 선택된 유저의 roomId로 설정
    setRoomId(user.id);

    if (onUserSelect) {
      onUserSelect(user);
    }
  };

  return {
    user,
    selectedUser,
    loginId,
    selectedGroup,
    filterUsers,
    search,
    activateUser,
    loginMemberName,
  };
}
