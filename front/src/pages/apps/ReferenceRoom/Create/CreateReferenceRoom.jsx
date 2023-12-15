import { Row, Col, Card, Form, Button } from 'react-bootstrap';
import { Form as RHForm } from '@/components';
import {
	PageBreadcrumb,
	FileUploader,
	TextInput,
	TextAreaInput,
} from '@/components';
import { useReferenceRoomForm } from '../hooks';
import { useNavigate } from 'react-router-dom';

const CreateReferenceRoom = () => {

	const { schema, handleValidSubmit } = useReferenceRoomForm();

    const navigate = useNavigate();

    const create = () => {

    }

    const cancel = () => {
        if(confirm("작성중인 내용은 저장되지 않습니다. 정말 취소하시겠습니까?")) {
            navigate('/apps/referenceRoom/list');
        }
    }
        
	return (
		<>
			<PageBreadcrumb title="Create ReferenceRoom" subName="ReferenceRoom" />

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
													name="title"
													label="Title"
													placeholder="Enter reference title"
													containerClass={'mb-3'}
													key="title"
												/>

												<TextAreaInput
													name="content"
													label="Content"
													placeholder="Enter some brief about reference.."
													rows={5}
													containerClass={'mb-3'}
													key="content"
												/>
                                                <Form.Group className="mb-3 mt-3 mt-xl-0">
													<Form.Label>Files</Form.Label>
													<p className="text-muted font-14">
														Recommended thumbnail size 800x400 (px).
													</p>
													<FileUploader />
												</Form.Group>
											</Col>
										</Row>

										<Row className="mt-2">
											<Col>
                                                <div className="button-list d-flex flex-wrap gap-2">
												    <Button variant="primary" onClick={create}>
													    Create
												    </Button>
                                                    <Button variant="danger" onClick={cancel}>
													    Cancel
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

export { CreateReferenceRoom };
