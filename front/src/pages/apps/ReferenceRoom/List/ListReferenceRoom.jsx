import axios from 'axios';
import { Link } from 'react-router-dom';
import { Row, Col, Card } from 'react-bootstrap';
import { PageBreadcrumb } from '@/components';
import { ReferenceRoomTable } from './Table';
import { useState, useEffect } from 'react';

const ListReferenceRoom = () => {

    const [list, setList] = useState([]);
    const token = localStorage.getItem("_NOTEU_AUTH");
    console.log(token);

    useEffect(() => {
        axios.get('http://localhost:8081/subjects/1/references', {headers:{Authorization:token}})
        .then(res => {
            if(res.status === 200) {
                setList(res.data.content);
            } else {
                console.log("error : " + res.status);
            }
        })
    },[])
	return (
		<>
			<PageBreadcrumb title="ReferenceRoom List" subName="ReferenceRoom" />

			<Row>
				<Col xs={12}>
					<Card>
						<Card.Body>
							<Row className="mb-2">
								<Col sm={5}>
									<Link to="/apps/referenceRoom/create" className="btn btn-danger btn-rounded mb-2">
										<i className="mdi mdi-plus-circle me-2"></i> Create
									</Link>
								</Col>
							</Row>
                            <ReferenceRoomTable list={list}/>
							
						</Card.Body>
					</Card>
				</Col>
			</Row>
		</>
	);
};

export { ListReferenceRoom };
