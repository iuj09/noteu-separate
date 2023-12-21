import axios from 'axios';
import { Row, Col } from 'react-bootstrap';
import { PageBreadcrumb } from '@/components';
import ProjectCard from './ProjectCard';
import { Link } from 'react-router-dom';
import { useState, useEffect } from 'react';

const SubjectList = () => {
	const [list, setList] = useState([]);

	const token = localStorage.getItem("_NOTEU_AUTH").replace(/^"(.*)"$/, '$1');
    useEffect(() => {
        axios.get('http://localhost:8081/subjects', {headers:{Authorization:token}})
        .then(res => {
            if(res.status === 200 || res.status === 204) {
                setList(res.data);
            } else {
                console.log("error : " + res.status);
            }
        })
    },[])

	return (
		<>
			<PageBreadcrumb title="Subject List" subName="Subject" />

			<Row className="mb-2">
				<Col sm={4}>
					<Link to="/apps/subjects/create" className="rounded-pill mb-3 btn btn-danger">
						<i className="mdi mdi-plus"></i> Create Subject
					</Link>
					<Link to="/apps/subjects/input-code" className="rounded-pill mb-3 btn btn-success">
						<i className="mdi mdi-plus"></i> Join Subject
					</Link>
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
