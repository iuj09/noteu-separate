import {useEffect, useState} from 'react';
import axios from 'axios';
import {connect} from "@/pages/apps/Chat/hooks/chat.js";

export default function useChatApp() {
  const [selectedUser, setSelectedUser] = useState();

  /**
   * On user change
   */
  const onUserChange = (user) => {
    setSelectedUser(user);
  };

  // useEffect(() => {
  //   const subjectId = 1;
  //
  //     axios.get(`http://localhost:8081/subjects/${subjectId}/chats/rooms`, {
  //       headers: {
  //         Authorization: `Bearer_eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwcm9maWxlIjoiL2ZpbGUvcHJvZmlsZS9kZWZhdWx0LnBuZyIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJtZW1iZXJOYW1lIjoibmFtZUEiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWFhYSIsImlhdCI6MTcwMjUxMzEzNSwiZXhwIjo3NzAyNTEzMDc1fQ.Ll2FuZvT4TesKYZ8LtDyi8LEMXkwAdg3zqHuR8c-XRk`
  //       }
  //     })
  //       .then(response => {
  //         let rooms = [];
  //         console.log(response.data);
  //         setLoginId(response.data.loginId);
  //         setLoginMemberName(response.data.loginMemberName);
  //
  //         response.data.chatRoomResponseDtos.forEach(room => {
  //           const id = room.id;
  //           const participants = room.participants; // 참가자 배열
  //           const email = participants[0].email;
  //           const friendId = participants[0].id;
  //           const avatar = participants[0].profile;
  //           const phone = participants[0].tel;
  //           const name = participants[0].membername;
  //           const lastMessage = room.lastMessage;
  //           const lastMessageOn = room.lastMessageDateTime;
  //
  //           connect(id, loginMemberName);
  //
  //           console.log('roomId : ' + room.id);
  //           rooms.push({
  //             id,
  //             email,
  //             friendId,
  //             avatar,
  //             phone,
  //             name,
  //             lastMessage,
  //             lastMessageOn
  //           });
  //         });
  //
  //         setSelectedUser(rooms[0]);
  //         console.log("rooms : " + rooms);
  //       })
  //       .catch(error => {
  //         console.error('There was an error!', error);
  //       });
  //   }, []);

  return { selectedUser, onUserChange };
}
