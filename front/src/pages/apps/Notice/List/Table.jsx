import { Card, Table } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { useLocation } from 'react-router-dom';

const NoticeTable = (props) => {

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

	const { list } = props;

	return (
		<Card>
			<Card.Body>
				<h4 className="header-title">공지사항</h4>
				<p className="text-muted font-14">
					해당 과목의 공지사항 입니다.
				</p>

				<Table className="mb-0" hover>
					<thead>
						<tr>
							<th>#</th>
							<th>Title</th>
							<th>Date</th>
						</tr>
					</thead>
					<tbody>
						{list && list.length > 0 ? (
							list.map((item) => {
								const createdAt = new Date(item.createdAt);
								const year = createdAt.getFullYear();
								const month = createdAt.getMonth() + 1;  // getMonth()는 0부터 시작하므로 1을 더해줍니다.
								const day = createdAt.getDate();
								const hours = createdAt.getHours();
								const minutes = createdAt.getMinutes();
								return (
									<tr key={item.id}>
										<th scope="row">{item.id}</th>
										<td>
											<Link to={`/apps/subjects/${subjectId}/notices/detail/${item.id}`}>
												<div>
													{item.noticeTitle}
												</div>
											</Link>
										</td>
										<td>{`${year}-${month}-${day} ${hours}시 ${minutes}분`}</td>
									</tr>
								);
							})
						) : (
							<tr>
								<td colSpan="4">등록된 공지사항이 없습니다.</td>
							</tr>
						)}

					</tbody>
				</Table>
			</Card.Body>
		</Card>
	);
};

export { NoticeTable };