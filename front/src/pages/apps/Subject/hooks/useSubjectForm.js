// import { useState } from 'react';
// import * as yup from 'yup';

// // images
// import avatar1 from '@/assets/images/users/avatar-6.jpg';
// import avatar2 from '@/assets/images/users/avatar-7.jpg';
// import avatar3 from '@/assets/images/users/avatar-8.jpg';
// import avatar4 from '@/assets/images/users/avatar-9.jpg';
// import avatar5 from '@/assets/images/users/avatar-10.jpg';
// import avatar6 from '@/assets/images/users/avatar-4.jpg';
// import avatar7 from '@/assets/images/users/avatar-5.jpg';
// import avatar8 from '@/assets/images/users/avatar-1.jpg';

// export default function useProjectForm() {
// 	const [teamMembers] = useState([
// 		{ value: 'Shreyu N', name: 'Shreyu N', image: avatar2 },
// 		{ value: 'Greeva N', name: 'Greeva N', image: avatar5 },
// 		{ value: 'Dhyanu B', name: 'Dhyanu B', image: avatar4 },
// 		{ value: 'Mannat B', name: 'Mannat B', image: avatar6 },
// 		{ value: 'Katu S', name: 'Katu S', image: avatar7 },
// 		{ value: 'Nik N', name: 'Nik N', image: avatar1 },
// 		{ value: 'Rik N', name: 'Rik N', image: avatar8 },
// 	]);

// 	const [selectedTeamMembers, setSelectedTeamMembers] = useState([
// 		{ value: 'Shreyu N', name: 'Shreyu N', image: avatar1 },
// 		{ value: 'Greeva N', name: 'Greeva N', image: avatar2 },
// 		{ value: 'Dhyanu B', name: 'Dhyanu B', image: avatar3 },
// 	]);

// 	/*
// 	 *  add selected team members
// 	 */
// 	const selectTeamMembers = (e) => {
// 		if (e.length !== 0) {
// 			const isAlreadySelected = selectedTeamMembers.filter(
// 				(x) => x['name'] && x['name'] === e[0].name
// 			);
// 			if (isAlreadySelected && isAlreadySelected.length === 0) {
// 				setSelectedTeamMembers([...selectedTeamMembers, e[0]]);
// 			}
// 		}
// 	};

// 	/*
// 	 * form validation schema
// 	 */
// 	const schema = yup.object().shape({
// 		name: yup.string().required('Please enter Project Name'),
// 	});

// 	/**
// 	 * Handle the form submission
// 	 */
// 	const handleValidSubmit = (value) => {
// 		console.log(value);
// 		console.log({ ...value });
// 	};

// 	return {
// 		teamMembers,
// 		selectedTeamMembers,
// 		schema,
// 		handleValidSubmit,
// 		selectTeamMembers,
// 	};
// }

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
