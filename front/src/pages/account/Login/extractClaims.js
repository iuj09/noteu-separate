import jwt_decode from 'jwt-decode';

const extractClaims = () => {
    const token = localStorage.getItem("_NOTEU_AUTH").replace(/^"(.*)"$/, '$1');

    try {
        // 토큰 디코딩하여 클레임 추출
        const decodedToken = jwt_decode(token);

        // 클레임에서 정보 추출
        const userId = decodedToken.userId;
        const username = decodedToken.username;
        const memberName = decodedToken.memberName;
        const profile = decodedToken.profile;

        const role = decodedToken.roles[0].authority;
        let roleType;
        if (role === 'ROLE_ADMIN') {
            roleType = 'Admin';
        } else if (role === 'ROLE_TEACHER') {
            roleType = 'Teacher';
        } else if (role === 'ROLE_STUDENT') {
            roleType = 'Student';
        }

        return { token, userId, username, memberName, profile, role, roleType };
    } catch (error) {
        console.error('Error decoding token:', error);
        return null;
    }
};

export { extractClaims }