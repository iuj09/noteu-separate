import {Col, Form, InputGroup, Row} from 'react-bootstrap';
import {Form as RHForm, TextAreaInput, TextInput} from '@/components';
import {useForm} from "react-hook-form";

const PersonalInfo = ({ member }) => {
	console.log('information 값 확인: ' + JSON.stringify(member));
	console.log('information 필드 확인: ' + member.memberName);
	return (
		<>
			<h5 className="mb-3 text-uppercase bg-light p-2">
				<i className="mdi mdi-account-circle me-1"></i> Information
			</h5>
			<Row>
				<Col>
					<TextAreaInput
						label="About Me"
						name="introduction"
						placeholder={!member.introduction || member.introduction.trim() === '' ? "소개 글을 입력해보세요!" : member.introduction}
						rows={4}
						containerClass={'mb-3'}
						key="introduction"
					/>
				</Col>
			</Row>
			<Row>
				<Col className="mb-3">
					<TextInput
						label="Name"
						type="text"
						name="memberName"
						placeholder={member.memberName}
						key="memberName"
					/>
				</Col>
			</Row>
			<Row>
				<Col className="mb-3">
					<TextInput
						label="Email Address"
						type="email"
						name="email"
						placeholder={member.email}
						key="email"
					/>
				</Col>
			</Row>
			<Row>
				<Col className="mb-3">
					<TextInput
						label="Mobile"
						type="text"
						name="tel"
						placeholder={member.tel}
						key="tel"
					/>
				</Col>
			</Row>
		</>
	);
};

const Social = ({ socialinfo }) => {
	return (
		<>
			<h5 className="mb-3 text-uppercase bg-light p-2">
				<i className="mdi mdi-earth me-1"></i> Social
			</h5>

			<Row>
				{socialinfo.map((item, index) => {
					return (
						<Col key={index.toString()} md={6} className="mb-3">
							<Form.Label> {item.label} </Form.Label>
							<InputGroup className="mb-0">
								<span className="input-group-text">
									<i className={item.icon}></i>
								</span>
								<Form.Control placeholder={item.placeholder} disabled={true} />
							</InputGroup>
						</Col>
					);
				})}
			</Row>
		</>
	);
};

const Settings = ({ member }) => {
	const { control } = useForm({
		defaultValues: {
			memberName: member.memberName
		},
	});
	const socialInfo = [
		{
			label: 'Facebook',
			icon: 'mdi mdi-facebook',
			placeholder: 'Url',
		},
		{
			label: 'Twitter',
			icon: 'mdi mdi-twitter',
			placeholder: 'Username',
		},
		{
			label: 'Instagram',
			icon: 'mdi mdi-instagram',
			placeholder: 'Url',
		},
		{
			label: 'Linkedin',
			icon: 'mdi mdi-linkedin',
			placeholder: 'Url',
		},
		{
			label: 'Skype',
			icon: 'mdi mdi-skype',
			placeholder: '@username',
		},
		{
			label: 'Github',
			icon: 'mdi mdi-github',
			placeholder: 'Username',
		},
	];

	return (
		<RHForm onSubmit={(e) => e.preventDefault()}>
			<PersonalInfo member={member} />
			<Social socialinfo={socialInfo}/>

			<div className="text-end">
				<button type="submit" className="btn btn-success mt-2">
					<i className="mdi mdi-content-save"></i> Save
				</button>
			</div>
		</RHForm>
	);
};

export default Settings;
