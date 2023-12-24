import { useState, useEffect } from 'react';
import { Row, Col, Card } from 'react-bootstrap';
import { PageBreadcrumb } from '@/components';
import { Link, useLocation } from 'react-router-dom';
import { TaskTable } from './Table';
import axios from 'axios';

const ListTask = () => {

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
	console.log("subjectId :" + subjectId);

	const [list, setList] = useState([]);
	const token = localStorage.getItem("_NOTEU_AUTH").replace(/^"(.*)"$/, '$1');

	useEffect(() => {
		axios.get(`http://localhost:8081/subjects/${subjectId}/tasks`, { headers: { Authorization: token } })
			.then(res => {
				if (res.status === 200 || res.status === 204) {
					console.log("status: " + res.status);
					setList(res.data);
				} else {
					console.log("error: " + res.status);
				}
			})
	}, []);

	return (
		<>
			<PageBreadcrumb title="Task List" subName="Task" />

			<Row>
				<Col xs={12}>
					<Card>
						<Card.Body>
							<Row className="mb-2">
								<Col sm={5}>
									<Link to={`/apps/subject/${subjectId}/task/create`} className="btn btn-danger btn-rounded mb-2">
										<i className="mdi mdi-plus-circle me-2"></i> Create
									</Link>
								</Col>
							</Row>
							<TaskTable list={list} />

						</Card.Body>
					</Card>
				</Col>
			</Row>
		</>
	);
};

export { ListTask };
