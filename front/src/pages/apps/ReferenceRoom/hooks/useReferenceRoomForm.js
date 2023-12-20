import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import * as yup from 'yup';

export default function useReferenceRoomForm() {

	const navigate = useNavigate();
	/*
	 * form validation schema
	 */
	const schema = yup.object().shape({
		title: yup.string().required('Title is required'),
		content: yup.string().required('Content is required'),
	  });

	/**
	 * Handle the form submission
	 */
	const handleValidSubmit = async (value, selectedFiles) => {
		const token = localStorage.getItem("_NOTEU_AUTH").replace(/^"(.*)"$/, '$1');

		const formData = new FormData();


		const referenceRoomData = {
			referenceRoomTitle: value.title,
			referenceRoomContent: value.content,
		};

		const dataBlob = new Blob([JSON.stringify(referenceRoomData)], {
			type: 'application/json'
		});

		formData.append('addRequestReferenceRoomDTO', dataBlob);
	  
		// form의 파일을 formData에 추가
		if (Array.isArray(selectedFiles)) {
			selectedFiles.forEach((file) => {
		  	formData.append('referenceFile', file);
			});
		}

		const response = await axios.post('http://localhost:8081/subjects/1/references', formData, {
			headers: {
			  'Authorization': `${token}`,  // 토큰을 사용하여 인증
			  'Content-Type': 'multipart/form-data',
			},
		  });

		  alert("자료실 게시글 작성이 완료되었습니다.");
		  navigate('/apps/referenceRoom/list');
	};

	return {
		schema,
		handleValidSubmit,
	};
}
