import axios from "axios";

const fetchData = async (userId, token) => {
    try {
        const response = await axios.get(`http://localhost:8081/members/account/${userId}`, {
            headers: token,
        });

        if (response.status === 200) {
            console.log('account info');
            console.log(response.data);
            return response.data;
        } else {
            console.log(`error: ${response.status}`);
            return null;
        }
    } catch (error) {
        console.error('Error fetching user data:', error);
        return null;
    }
};

export { fetchData };