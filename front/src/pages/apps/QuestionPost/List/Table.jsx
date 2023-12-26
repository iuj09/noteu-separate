import { Card, Table } from 'react-bootstrap';
import { Link, useLocation } from 'react-router-dom';

const QuestionPostTable = (props) => {

    const { list } = props;

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

	return (
		<Card>
			<Card.Body>
				<h4 className="header-title">질문게시판</h4>
				<p className="text-muted font-14">
					공부 중 궁금한 점이 생기면 질문글을 작성 해 주세요.
				</p>

				<Table className="mb-0" hover>
					<thead>
						<tr>
							<th>#</th>
							<th>Title</th>
							<th>Writer</th>
							<th>Date</th>
							<th><i className="mdi mdi-eye" /></th>
							<th><i className="mdi mdi-comment-text-multiple" /></th>
						</tr>
					</thead>
					<tbody>
						{list.map((item) => {
                            const createdAt = new Date(item.createdAt);
                            const year = createdAt.getFullYear();
                            const month = createdAt.getMonth() + 1;  // getMonth()는 0부터 시작하므로 1을 더해줍니다.
                            const day = createdAt.getDate();
                            const hours = createdAt.getHours();
                            const minutes = createdAt.getMinutes();
							return (
								<tr key={item.questionPostId}>
									<th scope="row">{item.questionPostId}</th>
									<td>
                                        <Link to={`/apps/subjects/${subjectId}/questionPost/detail/${item.questionPostId}`}>
                                            <div>
                                                {item.questionPostTitle}
                                            </div>
                                        </Link>
                                    </td>
									<td>{item.memberName}</td>
									<td>{`${year}-${month}-${day} ${hours}시 ${minutes}분`}</td>
									<td>{item.views}</td>
									<td>{item.commentCnt}</td>
								</tr>
							);
						})}
					</tbody>
				</Table>
			</Card.Body>
		</Card>
	);
};

export { QuestionPostTable };
