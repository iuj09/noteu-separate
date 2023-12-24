import { Row, Col, Card, Button } from 'react-bootstrap';
import { Form as RHForm } from '@/components';
import {
	PageBreadcrumb,
	TextInput,
} from '@/components';
import { useSubjectCodeForm } from '../hooks';

const JoinSubject = () => {

	const { schema, handleValidSubmit } = useSubjectCodeForm();

	return (
		<>
			<PageBreadcrumb title="Join Subject" subName="Subject" />

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
													name="subjectCode"
													label="Subject Code"
													placeholder="Enter subject code"
													containerClass={'mb-3'}
													key="name"
												/>
											</Col>
										</Row>

										<Row className="mt-2">
											<Col>
												<div className="button-list d-flex flex-wrap gap-2">
													<Button type='submit' variant="primary">
														Join
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

export { JoinSubject };
