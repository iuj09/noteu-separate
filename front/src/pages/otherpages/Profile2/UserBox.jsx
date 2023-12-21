import {Card} from 'react-bootstrap';
import {Link} from 'react-router-dom';
import {useEffect, useState} from "react";
import {extractClaims} from "@/pages/account/Login/extractClaims.js";
import {fetchData} from "@/pages/otherpages/Profile2/fetchData.js";

const UserBox = () => {

	const [memberDto, setMemberDto] = useState({});

	useEffect(() => {
		const data = async () => {
			const userId = extractClaims().userId;
			const token = extractClaims().token;

			const userData = await fetchData(userId, token);

			if (userData) {
				setMemberDto(userData.memberDto);
			}
		};
		data();
	}, []);

	return (
		<Card className="text-center">
			<Card.Body>
				<img src={memberDto.profile} className="rounded-circle avatar-lg img-thumbnail" alt="" />
				<h4 className="mb-0 mt-2">{memberDto.memberName}</h4>
				<p className="text-muted font-14">{memberDto.role}</p>
				<button type="button" className="btn btn-success btn-sm mb-2">
					Message
				</button>
				<div className="text-start mt-3">
					<h4 className="font-13 text-uppercase">About Me :</h4>
					{memberDto.introduction === null || memberDto.introduction === "" ? (
						<p className="text-muted font-13 mb-3">
							소개 글이 없습니다.
						</p>
					) : (
						<p className="text-muted font-13 mb-3">
							{memberDto.introduction}
						</p>
					)}
					<p className="text-muted mb-2 font-13">
						<strong>Name :</strong>
						<span className="ms-2">{memberDto.memberName}</span>
					</p>

					<p className="text-muted mb-2 font-13">
						<strong>Email :</strong>
						<span className="ms-2">{memberDto.email}</span>
					</p>

					<p className="text-muted mb-2 font-13">
						<strong>Mobile :</strong>
						<span className="ms-2 ">{memberDto.tel}</span>
					</p>
				</div>
				<ul className="social-list list-inline mt-3 mb-0">
					<li className="list-inline-item">
						<Link to="" className="social-list-item border-primary text-primary">
							<i className="mdi mdi-facebook"></i>
						</Link>
					</li>
					<li className="list-inline-item">
						<Link to="" className="social-list-item border-danger text-danger">
							<i className="mdi mdi-google"></i>
						</Link>
					</li>
					<li className="list-inline-item">
						<Link to="" className="social-list-item border-info text-info">
							<i className="mdi mdi-twitter"></i>
						</Link>
					</li>
					<li className="list-inline-item">
						<Link to="" className="social-list-item border-secondary text-secondary">
							<i className="mdi mdi-github"></i>
						</Link>
					</li>
				</ul>
			</Card.Body>
		</Card>
	);
};

export default UserBox;
