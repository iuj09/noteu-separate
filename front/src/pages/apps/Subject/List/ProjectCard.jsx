import { Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import classNames from 'classnames';


const ProjectCard = ({ subject }) => {

	return (
		<Card className="d-block">

			<Card.Body>
			<h4 className="mt-0">
				<Link to={`/apps/subjects/${subject.id}/notices/list`} className="text-title">
				{subject.subjectName}
				</Link>
			</h4>
			<hr />
			<span>
				{subject.subjectCode}
			</span>
			</Card.Body>
			
		</Card>
	);
};

export default ProjectCard;
