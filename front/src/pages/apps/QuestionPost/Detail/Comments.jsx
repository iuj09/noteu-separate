import { Card } from 'react-bootstrap';

const Comments = (props) => {

	const { comments, commentCount } = props;
	return (
		<Card>
			<Card.Body>
				<h4 className="mt-0 mb-3">Comments ({commentCount})</h4>

				{comments?.map((comment) => (
					<div className="d-flex align-items-start mt-2">
						<img className="me-3 avatar-sm rounded-circle" src={avatar3} alt="" />
						<div className="w-100 overflow-hidden">
							<h5 className="mt-0">{comment.memberName}</h5>
							{comment.questionCommentContent}
						</div>
					</div>
				))}
				<textarea
					className="form-control form-control-light mb-2"
					placeholder="Write message"
					id="example-textarea"
					rows={3}
				></textarea>
				<div className="text-end">
					<div className="btn-group mb-2 ms-2">
						<button type="button" className="btn btn-primary btn-sm">
							Submit
						</button>
					</div>
				</div>
			</Card.Body>
		</Card>
	);
};

export default Comments;
