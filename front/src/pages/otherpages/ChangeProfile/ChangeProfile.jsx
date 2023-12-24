import {PageBreadcrumb} from '@/components';
import {extractClaims} from "@/pages/account/Login/extractClaims.js";
import {useEffect, useState} from "react";
import axios from "axios";
import {fetchData} from "@/pages/otherpages/Profile2/fetchData.js";
import { useNavigate } from 'react-router-dom';

const ChangeProfile = () => {
	const navigate = useNavigate();

	const memberInfo = extractClaims();
	const memberId = memberInfo.userId;
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

	const imageUrl = 'http://localhost:8081' + memberDto.profile;
	const token = localStorage.getItem("_NOTEU_AUTH").replace(/^"(.*)"$/, '$1')

	const [file, setFile] = useState(null);

	const handleFileChange = (event) => {
		setFile(event.target.files[0]);
	};

	const handleSubmit = async (event) => {
		event.preventDefault();

		if (!file) {
			alert('Please select a file.');
			return;
		}

		try {
			const formData = new FormData();
			formData.append('profileFile', file);

			await axios.put('http://localhost:8081/members/profile/' + memberId, formData, {
				headers: {
					'Authorization': `${token}`,
					'Content-Type': 'multipart/form-data',
				}
			});
			console.log('File uploaded successfully!');
			navigate('/pages/profile2');
			location.reload();
		} catch (error) {
			console.error('Error uploading file:', error);
		}
	};

		return (
			<>
				<PageBreadcrumb title="Profile" subName="Pages"/>

				<div className="account-pages pt-2 pt-sm-5 pb-4 pb-sm-5">
					<div className="container">
						<div className="row justify-content-center">
							<div className="col-xxl-4 col-lg-5">
								<div className="card">
									{/* 로고 */}
									<div className="card-header py-4 text-center bg-primary">
										<a href="/">
                  <span>
                    <img src="/src/assets/images/noteuLogo.png" alt="로고" height="22"/>
                  </span>
										</a>
									</div>
									<div className="card-body p-4">
										<div className="text-center w-75 m-auto">
											<img
												src={imageUrl}
												height="64"
												alt="member profile"
												className="rounded-circle avatar-lg img-thumbnail"
											/>
											<h4 className="text-dark-50 text-center mt-3 fw-bold">{memberInfo.memberName}</h4>
											<p className="text-muted mb-4">프로필 사진을 변경해보세요!</p>
										</div>

										<form onSubmit={handleSubmit}>
											<div className="mb-3">
												<label htmlFor="profile" className="form-label">
													프로필
												</label>
												<input className="form-control" type="file" id="profile"
													   name="profileFile" onChange={handleFileChange}/>
											</div>
											<div className="mb-0 text-center">
												<button className="btn btn-primary" type="submit">
													저장
												</button>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</>
		);
	};

export { ChangeProfile };
