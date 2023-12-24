import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import * as yup from 'yup';

export default function useQuestionPostForm() {

	const navigate = useNavigate();
	/*
	 * form validation schema
	 */
	const schema = yup.object().shape({
		title: yup.string().required('Title is required'),
	  });

	/**
	 * Handle the form submission
	 */
	const handleValidSubmit = async (value, subjectId) => {
		
		const token = localStorage.getItem("_NOTEU_AUTH").replace(/^"(.*)"$/, '$1');

		const requestQuestionPostDTO = {
			questionPostTitle: value.title,
			questionPostContent: value.content,
		};

		console.log(value.title);
		console.log(value.content);

		try {
			const response = await axios.post(`http://localhost:8081/subjects/${subjectId}/questions`, requestQuestionPostDTO, {
			headers: {
                'Authorization': `${token}`,
                'Content-Type': 'application/json',
            },
		  	});
		  	if(response.status === 201) {
				alert("질문게시글 작성이 완료되었습니다.");
		  		navigate(`/apps/subjects/${subjectId}/questionPost/list`);
		  	} else {
				console.log("error : " + response.status);
			}
		  
		} catch(error) {
			console.log("error : " + error);
		}
		
	};

	return {
		schema,
		handleValidSubmit,
	};
}
