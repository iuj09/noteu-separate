import { authApi } from '@/common/api';
import { useAuthContext, useNotificationContext } from '@/common/context';

import * as yup from 'yup';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

export default function useRegister() {
	const [loading, setLoading] = useState(false);

	const { t } = useTranslation();
	const navigate = useNavigate();

	const { isAuthenticated } = useAuthContext();
	const { showNotification } = useNotificationContext();

	const schema = yup.object().shape({
		username: yup.string().required(t('Please enter ID.')),
		password: yup
			.string()
			.required(t('Please enter password.'))
			.min(8, 'Password is too short - should be 8 chars minimum.')
			.matches(/[a-zA-Z]/, 'Password can only contain latin letters.'),
		confirmPassword: yup.string().oneOf([yup.ref('password')], 'Passwords must match.'),
		memberName: yup.string().required(t('Please enter name.')),
		email: yup.string().email('Please enter valid email.').required(t('Please enter email.')),
		tel: yup.number().required(t('Please enter mobile.')),
		role: yup.string().required(t('Please choose type.')),
		checkbox: yup.boolean().required('You must agree before submitting.')
	});

	const register = async ( values ) => {
		console.log('sign-up!!');
		// const { username, password, confirmPassword, memberName, email, tel, role } = data;
		setLoading(true);
		try {
			console.log(values);
			const res = await authApi.register(values);
			if (res.status === 201) {
				showNotification({
					message: 'Registration successful. Welcome aboard!',
					type: 'success',
				});
				navigate('/account/login');
			}
		} catch (e) {
			console.log(e);
			showNotification({ message: e.toString(), type: 'error' });
		} finally {
			setLoading(false);
		}
	};

	return { loading, register, isAuthenticated, schema };
}
