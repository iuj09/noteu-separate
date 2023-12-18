import axios from "axios";
import { useState } from "react"

export default function LoginRequest() {
    // 입력 양식의 값을 담을 state 변수
    const [inputs, setInputs] = useState({ username: '', password: '' });
    const { username, password } = inputs;

    const onChange = (e) => {
        const { name, value } = e.target;
        setInputs({
            ...inputs,
            [name]: value // 여러 개의 입력 양식들을 한 번에 가져옴
        })
    }
    const login = () => {
        axios.post(`http://localhost:8081/auth/login`,
            {
                username: username,
                password: password
            }).then(res => {
                if (res.status === 201) {
                    let dto = res.data;
                    alert(dto);
                    alert(dto.id + '/' + dto.username + '/' + dto.password);
                } else {
                    alert('error');
                }
            })
    }
}