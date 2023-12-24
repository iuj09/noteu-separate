import { Row, Col, Card, Button } from 'react-bootstrap';
import { Form as RHForm } from '@/components';
import {
	PageBreadcrumb,
	TextInput,
} from '@/components';
import { useSubjectForm } from '../hooks';

const CreateSubject = () => {

	const { schema, handleValidSubmit } = useSubjectForm();

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
