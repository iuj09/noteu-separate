import HttpClient from '../helpers/httpClient';
import axios from 'axios';

function AuthService() {
  return {
    login: (values) => {
      console.log('login start');
      console.log(values);
      return axios.post(`http://3.36.72.106:8081/auth/login`, values
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
      console.log('sign-up start');
      console.log(values);
      return axios.post(`http://3.36.72.106:8081/auth/sign-up`, values
      ).then(res => {
        if (!res.data.statusCode) {
          console.log('sign-up request success!');
          return res;
        } else {
          console.log('sign-up request failed.');
          throw new Error('Please check the information and try again.');
        }
      })
    },
    forgetPassword: (values) => {
      return HttpClient.post('/forget-password/', values);
    },
  };
}

export default AuthService();
