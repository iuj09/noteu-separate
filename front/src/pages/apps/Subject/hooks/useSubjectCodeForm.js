import axios from 'axios';
import * as yup from 'yup';

export default function useSubjectCodeForm() {

	/*
	 * form validation schema
	 */
	const schema = yup.object().shape({
		subjectCode: yup.string().required('subject code is required'),
	});

	/**
	 * Handle the form submission
	 */
	const handleValidSubmit = async (value) => {
		const token = localStorage.getItem("_NOTEU_AUTH").replace(/^"(.*)"$/, '$1');

		const data = {
			subjectCode: value.subjectCode,
		};

		console.log(data.subjectCode);

		try {
			const response = await axios.post('http://localhost:8081/subjects/input-code', data, {
				headers: {
					'Authorization': `${token}`,
					'Content-Type': 'application/json', // JSON 형식으로 전송
				},
			});

			if (response.status === 200) {
				alert("과목 가입이 완료되었습니다.");
				location.href = "/apps/subjects/list";
			} else if (response.status === 409) {
				alert("해당 과목에 이미 가입이 되어있습니다.")
				console.log("Error:", response.status);
			}
		} catch (error) {
			alert("과목 가입 실패")
			console.error("Error:", error);
		}
	};
	return {
		schema,
		handleValidSubmit,
	};
}
