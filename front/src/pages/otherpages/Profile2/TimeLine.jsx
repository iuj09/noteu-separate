import { useState } from 'react';
import { Link } from 'react-router-dom';
import classnames from 'classnames';
import avatar3 from '@/assets/images/users/avatar-1.jpg';

const TimeLine = ({ recentPosts }) => {
	console.log('timeline 값 확인: ' + recentPosts);
	const [user] = useState({ id: 1, avatar: avatar3 });

	return (
		<>
			<div className="tab-pane" id="post">
				<h5 className="text-uppercase mb-3"><i className="mdi mdi-cards-variant me-1"></i> My Question</h5>
				<h5 className="mt-1 mb-2 text-center">{recentPosts.length === 0 && '작성한 질문 게시글이 없습니다.'}</h5>

				{Array.isArray(recentPosts) && recentPosts.map((recentPost, index) => (
					<div key={index} className="border border-light rounded p-2 mb-3">
						<div className="d-flex">
							<div className="d-flex gap-1">
								<h4><span className="badge badge-info-lighten">{recentPost.subjectName}</span></h4>
								<h4 className="m-1 mt-2 mb-0">{recentPost.questionPostTitle}</h4>
							</div>
							<div className="ms-auto">
              <span className="badge bg-light text-dark mt-2 mb-1 mr-1">
                <small>{new Date(recentPost.createdAt).toLocaleString('ko-KR', { year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric' })}</small>
              </span>
							</div>
						</div>
						<hr className="mt-0" />
						<p className="m-1" dangerouslySetInnerHTML={{ __html: recentPost.questionPostContent }}></p>
						<hr className="border border-1" />
						<div className="mt-2 mb-0">
							<a href={`/subjects/${recentPost.subjectId}/questions/${recentPost.questionId}`} className="btn btn-sm btn-link text-muted">
								<i className="mdi mdi-comment-text-multiple"></i>&ensp;Comment&ensp;
								{recentPost.commentCount !== 0 && <span className="badge badge-danger-lighten">{recentPost.commentCount}</span>}
							</a>
							<a href={`/subjects/${recentPost.subjectId}/questions/${recentPost.questionId}`} className="btn btn-sm btn-link text-muted">
								<i className="mdi mdi-arrow-right-bottom"></i> Read More
							</a>
						</div>
					</div>
				))}
			</div>
		</>
	);
};

export default TimeLine;
