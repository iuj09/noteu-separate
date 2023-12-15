import { Card, Row } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const Files = (props) => {

	const { reference } = props;

	return (
		<Card>
			<Card.Body>
				<h5 className="card-title mb-3">DownLoad Files</h5>

				{reference?.map((file) => (
					<Card className="mb-1 shadow-none border">
						<div className="p-2">
							<Row className="align-items-center">
								<div className="col-auto">
									<div className="avatar-sm">
										<span className="avatar-title rounded">{file.referenceType}</span>
									</div>
								</div>
								<div className="col ps-0">
									<Link to="" className="text-muted fw-bold">
										{file.referenceName}
									</Link>
									<p className="mb-0">{file.referenceSize} MB</p>
								</div>
								<div className="col-auto">
									<Link to="" className="btn btn-link btn-lg text-muted">
										<i className="ri-download-2-line"></i>
									</Link>
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
