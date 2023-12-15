import HttpClient from '../helpers/httpClient';
import axios from 'axios';

function AuthService() {
  return {
    login: async (values) => {
      console.log('login start');
      console.log(values);
      return axios.post(`http://localhost:8081/auth/login`, values
      ).then(res => {
          if (!res.data.statusCode) {
            console.log('login request success!');
            return res;
          } else {
            console.log('login request failed.');
            throw new Error('ID or Password is incorrect.');
          }
      })
    },
    logout() {
      return HttpClient.post('/logout/', {});
    },
    register: (values) => {
      return HttpClient.post('/register/', values);
    },
    forgetPassword: (values) => {
      return HttpClient.post('/forget-password/', values);
    },
  };
}

export default AuthService();
