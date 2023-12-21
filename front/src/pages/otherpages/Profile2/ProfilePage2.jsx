import { Row, Col, Card, Tab, Nav } from 'react-bootstrap';
import { Messages, PageBreadcrumb } from '@/components';
import UserBox from './UserBox';
import About from './About';
import TimeLine from './TimeLine';
import Settings from './Settings';
import { projects, posts } from './data';
import {useEffect, useState} from "react";
import {extractClaims} from "@/pages/account/Login/extractClaims.js";
import {fetchData} from "@/pages/otherpages/Profile2/fetchData.js";

const ProfilePage2 = () => {

	const [subjects, setSubjects] = useState({});
	const [recentPosts, setRecentPosts] = useState({});
	const [member, setMember] = useState({});

	useEffect(() => {
		const data = async () => {
			const userId = extractClaims().userId;
			const token = extractClaims().token;

			const userData = await fetchData(userId, token);

			if (userData) {
				setSubjects(userData.subjectInfoList);
				setRecentPosts(userData.recentQuestionList);
				setMember(userData.memberDto);
			}
		};
		data();
	}, []);

	console.log('과목들:' + JSON.stringify(subjects));
	console.log(projects);
	console.log('최근글:' + recentPosts);
	console.log('내정보:' + JSON.stringify(member));

	return (
		<>
			<PageBreadcrumb title="Profile 2" subName="Pages" />
			<Row>
				<Col xl={4} lg={5}>
					{/* User information */}
					<UserBox />

					{/* User's recent messages */}
					{/*<Messages />*/}
				</Col>
				<Col xl={8} lg={7}>
					<Tab.Container defaultActiveKey="aboutme">
						<Card>
							<Card.Body>
								<Nav
									as="ul"
									variant="pills"
									className="nav nav-pills bg-nav-pills nav-justified mb-3"
								>
									 <Nav.Item as="li" className="nav-item">
										<Nav.Link href="" eventKey="aboutme" className=" rounded-0">
											Subject
										</Nav.Link>
									</Nav.Item>
									<Nav.Item as="li" className="nav-item">
										<Nav.Link
											href=""
											eventKey="timeline"
											className=" rounded-0"
										>
											Recent Post
										</Nav.Link>
									</Nav.Item>
									<Nav.Item as="li" className="nav-item">
										<Nav.Link
											href=""
											eventKey="settings"
											className=" rounded-0"
										>
											Settings
										</Nav.Link>
									</Nav.Item>
								</Nav>

								<Tab.Content>
									<Tab.Pane eventKey="aboutme">
										<About subjects={subjects} />
									</Tab.Pane>
									<Tab.Pane eventKey="timeline">
										<TimeLine recentPosts={recentPosts} />
									</Tab.Pane>
									<Tab.Pane eventKey="settings">
										<Settings member={member} />
									</Tab.Pane>
								</Tab.Content>
							</Card.Body>
						</Card>
					</Tab.Container>
				</Col>
			</Row>
		</>
	);
};

export { ProfilePage2 };
