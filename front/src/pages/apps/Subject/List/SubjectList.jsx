import axios from 'axios';
import { Row, Col } from 'react-bootstrap';
import { PageBreadcrumb } from '@/components';
import ProjectCard from './ProjectCard';
import { Link } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { extractClaims } from '@/pages/account/Login/extractClaims';

const SubjectList = () => {

	const [list, setList] = useState([]);
	const token = localStorage.getItem("_NOTEU_AUTH").replace(/^"(.*)"$/, '$1');

	useEffect(() => {
		axios.get(`http://localhost:8081/subjects`, { headers: { Authorization: token } })
			.then(res => {
				if (res.status === 200 || res.status === 204) {
					setList(res.data);
				} else {
					console.log("error : " + res.status);
				}
			})
	}, [])

	const roleType = extractClaims().roleType;

	return (
		<>
			<PageBreadcrumb title="Subject List" subName="Subject" />

			<Row className="mb-2">
				<Col sm={4}>
					{
						roleType === "Teacher" && (
							< Link to={`/apps/subjects/create`} className="rounded-pill mb-3 btn btn-danger">
								<i className="mdi mdi-plus-circle me-2"></i> Create Subject
							</Link>
						)
					}
					{
						roleType === "Student" && (
							<Link to={`/apps/subjects/input-code`} className="rounded-pill mb-3 btn btn-success">
								<i className="mdi mdi-plus-circle me-2"></i> Join Subject
							</Link>
						)
					}
				</Col>
			</Row>

			<Row>
				{list.map((subject) => {
					return (
						<Col md={6} xxl={3} key={'subject-' + subject.id}>
							<ProjectCard subject={subject} />
						</Col>
					);
				})}
			</Row>
		</>
	);
};

export { SubjectList };
