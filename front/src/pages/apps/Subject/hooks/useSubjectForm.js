import axios from 'axios';
import * as yup from 'yup';

export default function useSubjectForm() {

	/*
	 * form validation schema
	 */
	const schema = yup.object().shape({
		subjectName: yup.string().required('subject name is required'),
	});

	/**
	 * Handle the form submission
	 */
	const handleValidSubmit = async (value) => {
		const token = localStorage.getItem("_NOTEU_AUTH").replace(/^"(.*)"$/, '$1');

		const data = {
			subjectName: value.subjectName,
		};

		try {
			const response = await axios.post('http://localhost:8081/subjects', data, {
				headers: {
					'Authorization': `${token}`,
					'Content-Type': 'application/json', // JSON 형식으로 전송
				},
			});

			if (response.status === 201) {
				alert("과목 생성이 완료되었습니다.");
				location.href = "/apps/subjects/list";
			} else {
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
