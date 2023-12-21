import { CheckInput, Form, PageBreadcrumb, PasswordInput, TextInput } from '@/components';
import { Button, Col, Row } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { Link, Navigate } from 'react-router-dom';
import AccountWrapper from '../AccountWrapper';
import useRegister from './useRegister';
import RadioInput from "@/components/Form/RadioInput.jsx";

const BottomLink = () => {
	const { t } = useTranslation();

	return (
		<Row className="mt-3">
			<Col className="text-center">
				<p className="text-muted">
					{t('Already have account?')}
					<Link to={'/account/login'} className="text-muted ms-1">
						<b>{t('Log In')}</b>
					</Link>
				</p>
			</Col>
		</Row>
	);
}; 

export default function Register() {
	const { t } = useTranslation();

	const { loading, register, isAuthenticated, schema } = useRegister();

	return (
		<>
			{isAuthenticated && <Navigate to="/" replace />}
			<PageBreadcrumb title="Register" />
			<AccountWrapper bottomLinks={<BottomLink />}>
				<div className="text-center m-auto">
					<h4 className="text-dark-50 text-center mt-0 fw-bold">{t('Sign Up')}</h4>
					<p className="text-muted mb-4">
						{t(
							"Don't have an account?"
						)}
						<br/>
						{t('Create your account, it takes less than a minute!')}

					</p>
				</div>

				<Form
					onSubmit={register}
					schema={schema}
				>
					<TextInput
						label={t('ID')}
						type="text"
						name="username"
						placeholder={t('Enter your ID')}
						containerClass="mb-3"
					/>
					<PasswordInput
						label={t('Password')}
						name="password"
						placeholder={t('Enter password')}
						containerClass="mb-2"
					/>
					<PasswordInput
						label={t('Confirm Password')}
						name="confirmPassword"
						placeholder={t('Confirm password')}
						containerClass="mb-3"
					/>
					<TextInput
						label={t('Name')}
						type="text"
						name="memberName"
						placeholder={t('Enter your name')}
						containerClass="mb-3"
					/>
					<TextInput
						label={t('Email address')}
						type="text"
						name="email"
						placeholder={t('Enter your email')}
						containerClass="mb-3"
					/>
					<TextInput
						label={t('Mobile')}
						type="text"
						name="tel"
						placeholder={t('Enter your mobile')}
						containerClass="mb-3"
					/>
{/* <input class="form-check-input" id="teacher" type='radio' name='role' value='ROLE_TEACHER' th:field="*{role}">
<label for="teacher" class="form-check-label ms-1">Teacher</label>
</div>
<div class="form-check-inline">
<input class="form-check-input" id="student" type='radio' name='role' value='ROLE_STUDENT' th:field="*{role}">
<label for="student" class="form-check-label ms-1">Student</label> */}
					<label htmlFor="role" className="form-label">Type</label>
					<div id="role" className="mb-3 d-flex justify-content-center">
						<div className="mx-2">
							<RadioInput
								type="radio"
								name="role"
								id="teacher"
								value="ROLE_TEACHER"
								className="mx-2"
								label="Teacher"
							/>
						</div>
						<div className="mx-2">
							<RadioInput
								type="radio"
								name="role"
								id="student"
								value="ROLE_STUDENT"
								className="mx-2"
								label="Student"
							/>
						</div>
					</div>
					<CheckInput
						name="checkbox"
						type="checkbox"
						containerClass="mb-2"
						label={
							<>
								I accept
						              		<span className="text-muted cursor-pointer ms-1">
									Terms and Conditions
								</span>
							</>
						}
						defaultChecked
					/>

					<div className="mb-3 text-center">
						<Button variant="primary" type="submit" disabled={loading}>
							{t('Sign Up')}
						</Button>
					</div>
				</Form>
			</AccountWrapper>
		</>
	);
}
