import { Row, Col, Card, Button} from 'react-bootstrap';
import { useState } from 'react';
import { Form as RHForm } from '@/components';
import {
	PageBreadcrumb,
	TextInput
} from '@/components';
import { useQuestionPostForm } from '../hooks';
import { useNavigate, useLocation } from 'react-router-dom';
import SimpleMDEReact from 'react-simplemde-editor';

// styles
import 'easymde/dist/easymde.min.css';

const CreateQuestionPost = () => {

	const { schema, handleValidSubmit } = useQuestionPostForm();
    const [markdownContent, setMarkdownContent] = useState('');

    const handleMarkdownChange = (value) => {
        setMarkdownContent(value);
    };

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

    const onSubmit = (data) => {
        data.content = markdownContent;
        handleValidSubmit(data, subjectId);
    };

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
									<RHForm onSubmit={onSubmit} schema={schema}>
										<Row>
											<Col xl={12}>
												<TextInput
													type="text"
													name="title"
													id="title"
													label="Title"
													placeholder="Enter reference title"
													containerClass={'mb-3'}
													key="questionPostTitle"
												/>

                                                <SimpleMDEReact
                                                    value={markdownContent}
                                                    onChange={handleMarkdownChange}
                                                />
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

export { CreateQuestionPost };
