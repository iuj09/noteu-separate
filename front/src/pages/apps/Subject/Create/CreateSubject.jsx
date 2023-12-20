import { useState } from 'react';
import { Row, Col, Card, Button } from 'react-bootstrap';
import { Form as RHForm } from '@/components';
import {
	PageBreadcrumb,
	TextInput,
} from '@/components';
import { useSubjectForm } from '../hooks';
import axios from 'axios';
import { useNavigate } from "react-router-dom";

const CreateSubject = () => {

	const { schema, handleValidSubmit } = useSubjectForm();
	const navigate = useNavigate();

	const token = localStorage.getItem("_NOTEU_AUTH").replace(/^"(.*)"$/, '$1');

	const [subjectName, setSubjectName] = useState('')

	const create = () => {

		console.log("2. subject name: ", subjectName);
		axios.post('http://localhost:8081/subjects', { subjectName: subjectName }, {
			headers: { Authorization: token }
		})
			.then(res => {
				if (res.status === 201) {
					alert("과목 생성이 완료되었습니다.");
					navigate("/apps/subjects/list");
				} else {
					console.log("error : " + res.status);
				}
			})
	};

	return (
		<>
			<PageBreadcrumb title="Create Subject" subName="Subject" />

			<Row>
				<Col>
					<Card>
						<Card.Body>
							<Row>
								<Col>
									<RHForm onSubmit={handleValidSubmit} schema={schema}>
										<Row>
											<Col xl={12}>
												<TextInput
													type="text"
													name="subjectName"
													label="Subject Name"
													placeholder="Enter subject name"
													containerClass={'mb-3'}
													key="name"
												/>
											</Col>
										</Row>

										<Row className="mt-2">
											<Col>
												<div className="button-list d-flex flex-wrap gap-2">
													<Button type='submit' variant="primary">
														Create
													</Button>
												</div>
											</Col>
										</Row>
									</RHForm>
								</Col>
							</Row>
						</Card.Body>
					</Card>
				</Col>
			</Row>
		</>
	);
};

export { CreateSubject };
