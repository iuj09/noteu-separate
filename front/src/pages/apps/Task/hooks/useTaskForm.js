import axios from 'axios';
import * as yup from 'yup';
import { useState } from 'react';
import { useLocation } from 'react-router-dom';

export default function useTaskForm() {

	const location = useLocation();
	const url = location.pathname;
	let subjectsIndex = url.indexOf("/subjects/");
	// "/subjects/" 다음에 숫자를 찾기
	let numberStartIndex = subjectsIndex + "/subjects/".length;
	let numberEndIndex = url.indexOf("/", numberStartIndex);

	if (numberEndIndex === -1) {
		// 숫자가 URL의 끝에 있는 경우
		numberEndIndex = url.length;
	}

	// 숫자 추출
	const subjectId = url.substring(numberStartIndex, numberEndIndex);


	const [deadLine, setDeadLine] = useState(new Date());

	/*
	 * form validation schema
	 */
	const schema = yup.object().shape({
		taskTitle: yup.string().required('Task Title is required'),
		taskContent: yup.string().required('Task Content is required'),
	});

	/**
	 * Handle the form submission
	 */

	const handleValidSubmit = async (value) => {
		const token = localStorage.getItem("_NOTEU_AUTH").replace(/^"(.*)"$/, '$1');

		console.log(value, deadLine);

		const data = {
			taskTitle: value.taskTitle,
			taskContent: value.taskContent,
			deadLine: deadLine,
		};

		console.log(value);

		console.log(data.taskTitle);
		console.log(data.taskContent);
		console.log(data.deadLine);

		try {
			const response = await axios.post(`http://localhost:8081/subjects/${subjectId}/tasks`, data, {
				headers: {
					'Authorization': `${token}`,
					'Content-Type': 'application/json', // JSON 형식으로 전송
				},
			});

			if (response.status === 201) {
				alert("과제 등록이 완료되었습니다.");
				location.href = history.back();
			} else {
				alert("과제 등록 실패");
				console.log("Error:", response.status);
			}
		} catch (error) {
			console.error("Error:", error);
		}
	};
	return {
		schema,
		deadLine,
		setDeadLine,
		handleValidSubmit,
	};
}
