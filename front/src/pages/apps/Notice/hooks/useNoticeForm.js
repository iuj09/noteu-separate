import axios from 'axios';
import * as yup from 'yup';
import { useLocation } from 'react-router-dom';

export default function useNoticeForm() {

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
	console.log("subjectId :" + subjectId);

	/*
	 * form validation schema
	 */
	const schema = yup.object().shape({
		noticeTitle: yup.string().required('Notice Title is required'),
		noticeContent: yup.string().required('Notice Content is required'),
	});

	/**
	 * Handle the form submission
	 */
	const handleValidSubmit = async (value) => {
		const token = localStorage.getItem("_NOTEU_AUTH").replace(/^"(.*)"$/, '$1');

		const data = {
			noticeTitle: value.noticeTitle,
			noticeContent: value.noticeContent,
		};

		try {
			const response = await axios.post(`http://localhost:8081/subjects/${subjectId}/notices`, data, {
				headers: {
					'Authorization': `${token}`,
					'Content-Type': 'application/json', // JSON 형식으로 전송
				},
			});

			if (response.status === 201) {
				alert("공지사항 등록이 완료되었습니다.");
				location.href = history.back();
			} else {
				alert("공지사항 등록 실패");
				console.log("Error:", response.status);
			}
		} catch (error) {
			console.error("Error:", error);
		}
	};
	return {
		schema,
		handleValidSubmit,
	};
}
