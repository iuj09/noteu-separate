import axios from 'axios';
import { Link, useLocation } from 'react-router-dom';
import { Row, Col, Card } from 'react-bootstrap';
import { PageBreadcrumb } from '@/components';
import { QuestionPostTable } from './Table.jsx';
import { useState, useEffect } from 'react';
import Search from './Search';
import { extractClaims } from "@/pages/account/Login/extractClaims.js";

const ListQuestionPost = () => {

    const [list, setList] = useState([]);
    const token = localStorage.getItem("_NOTEU_AUTH").replace(/^"(.*)"$/, '$1');
	
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

    const roleType = extractClaims().roleType;

    useEffect(() => {
        axios.get(`http://localhost:8081/subjects/${subjectId}/questions`, {headers:{Authorization:token}})
        .then(res => {
            if(res.status === 200) {
                setList(res.data.content);
                console.log(res.data.content);
            } else {
                console.log("error : " + res.status);
            }
        })
    },[])
	return (
		<>
			<PageBreadcrumb title="QuestionPost List" subName="QuestionPost" />

			<Row>
				<Col xs={12}>
					<Card>
						<Card.Body>
							<Row className="mb-2">
								<Col sm={5}>
                                    { roleType === "Teacher" && (
                                        <Link to={`/apps/subjects/${subjectId}/questionPost/create`} className="rounded-pill btn btn-danger mb-2">
                                            <i className="mdi mdi-plus-circle me-2"></i> Create
                                        </Link>
                                    )}
								</Col>
								<Col>
									<Search />
								</Col>
							</Row>
                            <QuestionPostTable list={list}/>
							
						</Card.Body>
					</Card>
				</Col>
			</Row>
		</>
	);
};

export { ListQuestionPost };
