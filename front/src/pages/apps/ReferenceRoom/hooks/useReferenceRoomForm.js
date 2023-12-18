import * as yup from 'yup';

export default function useProjectForm() {

	/*
	 * form validation schema
	 */
	const schema = yup.object().shape({
		name: yup.string().required('Please enter Project Name'),
	});

	/**
	 * Handle the form submission
	 */
	const handleValidSubmit = (value) => {
		console.log({ ...value });
	};

	return {
		schema,
		handleValidSubmit,
	};
}
