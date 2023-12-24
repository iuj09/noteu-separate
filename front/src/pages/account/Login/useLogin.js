import { authApi } from '@/common/api';
import { useAuthContext, useNotificationContext } from '@/common/context';
import { useMemo, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import * as yup from 'yup';

export const loginFormSchema = yup.object({
	username: yup.string().matches(/^[a-z0-9]+$/, '영어 소문자와 숫자로 구성해주세요.').required('Please enter ID'),
	password: yup.string().required('Please enter password'),
});

export default function useLogin() {
	const [loading, setLoading] = useState(false);
	const location = useLocation();
	const navigate = useNavigate();

	const { isAuthenticated, saveSession } = useAuthContext();
	const { showNotification } = useNotificationContext();

	const redirectUrl = useMemo(
		() => (location.state && location.state.from ? location.state.from.pathname : '/'),
		[location.state]
	);

	const login = async (values) => {
		setLoading(true);
		try {
			const res = await authApi.login(values);
			if (!res.statusCode) {
				saveSession({ ...(res.data ?? {}), token: res.headers.authorization });
				navigate("/apps/subjects/list");
			}
		} catch (error) {
			console.log(error);
			showNotification({
				message: error.toString().slice(error.toString().indexOf(':') + 2), type: 'error'
			});
		} finally {
			setLoading(false);
		}
	};

	return { loading, login, redirectUrl, isAuthenticated };
}
