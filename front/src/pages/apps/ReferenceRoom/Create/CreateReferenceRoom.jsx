import { Row, Col, Card, Form, Button} from 'react-bootstrap';
import { Form as RHForm } from '@/components';
import {
	PageBreadcrumb,
	FileUploader,
	TextInput,
	TextAreaInput,
} from '@/components';
import { useReferenceRoomForm } from '../hooks';
import { useNavigate, useLocation } from 'react-router-dom';
import { Controller, useForm } from 'react-hook-form';
import { useState } from 'react';

const CreateReferenceRoom = () => {

	const { schema, handleValidSubmit } = useReferenceRoomForm();

	const { control } = useForm();
	const [ selectedFiles, setSelectedFiles ] = useState([]);

    const navigate = useNavigate();

	const location = useLocation();
	const url = location.pathname;
	let subjectsIndex = url.indexOf("/subjects/");
	// "/subjects/" 다음에 숫자를 찾기
	let numberStartIndex = subjectsIndex + "/subjects/".length;
	let numberEndIndex = url.indexOf("/", numberStartIndex);

	if (numberEndIndex === -1) {
		// 숫자가 URL의 끝에 있는 경우
		numberEndIndex = url.length;
	}

	// 숫자 추출
	const subjectId = url.substring(numberStartIndex, numberEndIndex);

    const cancel = () => {
        if(confirm("작성중인 내용은 저장되지 않습니다. 정말 취소하시겠습니까?")) {
            navigate(`/apps/subjects/${subjectId}/referenceRoom/list`);
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
									<RHForm onSubmit={(data) => handleValidSubmit(data, selectedFiles, subjectId)} schema={schema}>
										<Row>
											<Col xl={12}>
												<TextInput
													type="text"
													name="title"
													id="title"
													label="Title"
													placeholder="Enter reference title"
													containerClass={'mb-3'}
													key="referenceRoomTitle"
												/>

												<TextAreaInput
													name="content"
													id="content"
													label="Content"
													placeholder="Enter some brief about reference.."
													rows={5}
													containerClass={'mb-3'}
													key="referenceRoomContent"
												/>
                                                <Form.Group className="mb-3 mt-3 mt-xl-0">
													<Form.Label>Files</Form.Label>
													<p className="text-muted font-14">
														Recommended thumbnail size 800x400 (px).
													</p>
													<Controller
														name="selectedFiles"
														control={control}
														defaultValue={[]}
														render={({ field }) => (
														<FileUploader {...field} onFileUpload={(files) => {
															setSelectedFiles(files);
															field.onChange(files);
														}} />
													)}/>
												</Form.Group>
											</Col>
										</Row>

										<Row className="mt-2">
											<Col>
                                                <div className="button-list d-flex flex-wrap gap-2">
												    <Button type="submit" variant="primary">
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
