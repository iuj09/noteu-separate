const About = ({ subjects }) => {
	console.log('about 값 확인: ' + subjects);
	return (
		<>
			<div className="tab-pane" id="post">
				<h5 className="text-uppercase mb-3"><i className="mdi mdi-cards-variant me-1"></i> My Subject</h5>
			</div>

			<div className="timeline-alt pt-0 pb-0">
				<h5 className="mt-1 mb-2 text-center">
					{subjects.length === 0 && '가입한 과목이 없습니다.'}
				</h5>
				{Array.isArray(subjects) && subjects.map((subject, index) => (
					<div key={subject.subjectId} className="card ribbon-box">
						<div className="card-body">
							<div className="ribbon ribbon-warning float-end d-flex align-content-center">
								<i className="mdi mdi-bulletin-board me-1"></i>
								<span>{subject.teacherName}</span>
							</div>
							<h5 className="text-dark float-start fs-4 mt-0">{subject.subjectName}</h5>
							<div className="ribbon-content">
              <span className="badge bg-light text-dark ms-auto">
                {'Joined At : ' + new Date(subject.joinedAt).toLocaleDateString('ko-KR')}
              </span>
							</div>
						</div>
					</div>
				))}
			</div>
		</>
	);
};

export default About;
