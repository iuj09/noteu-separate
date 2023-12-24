import axios from 'axios';
import { Card, Row, Button } from 'react-bootstrap';
import { useLocation } from 'react-router-dom';

const Files = (props) => {

	const { reference } = props;

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
	console.log("subjectId :" + subjectId);

	const download = (id, referenceName) => {
		axios.get(`http://localhost:8081/subjects/${subjectId}/references/down?id=${id}&referenceName=${referenceName}`, {headers:{Authorization:token}, responseType:'blob'}, )
		.then(res => {
			if(res.status === 200) {
				const url = window.URL.createObjectURL(new Blob([res.data]));
				const link = document.createElement('a');
				link.href = url;
				link.setAttribute('download', referenceName);
				document.body.appendChild(link);
				link.click();
			} else {
				console.log("error : " + res.status);
			}
		})
	}

	return (
		<Card>
			<Card.Body>
				<h5 className="card-title mb-3">DownLoadFiles</h5>

				{reference?.map((file) => (
					<Card key={file.id} className="mb-1 shadow-none border">
						<div className="p-2">
							<Row className="align-items-center">
								<div className="col-auto">
									<div className="avatar-sm">
										<span className="avatar-title rounded">{file.referenceType.split('/')[0]}</span>
									</div>
								</div>
								<div className="col ps-0">
									<div className="text-muted fw-bold">
										{file.referenceName}
									</div>
									<p className="mb-0">{file.referenceSize} MB</p>
								</div>
								<div className="col-auto">
									<Button variant="link" className="btn btn-link btn-lg text-muted" onClick={() => download(file.id, file.referenceName)}>
										<i className="ri-download-2-line"></i>
									</Button>
								</div>
							</Row>
						</div>
					</Card>
				 ))}
			</Card.Body>
		</Card>
	);
};

export default Files;
