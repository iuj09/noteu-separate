import { Card, Table } from 'react-bootstrap';
import { Link, useLocation } from 'react-router-dom';

const TaskTable = (props) => {

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
				<h4 className="header-title">공지사항</h4>
				<p className="text-muted font-14">
					해당 과목의 과제 입니다.
				</p>

				<Table className="mb-0" hover>
					<thead>
						<tr>
							<th>#</th>
							<th>Title</th>
							<th>Dead Line</th>
						</tr>
					</thead>
					<tbody>
						{list && list.length > 0 ? (
							list.map((item) => {
								const dead_line = new Date(item.deadLine.replace(' ', 'T'));
								const year = dead_line.getFullYear();
								const month = dead_line.getMonth() + 1;  // getMonth()는 0부터 시작하므로 1을 더해줍니다.
								const day = dead_line.getDate();
								const hours = dead_line.getHours();
								const minutes = dead_line.getMinutes();

								return (
									<tr key={item.id}>
										<th scope="row">{item.id}</th>
										<td>
											<Link to={`/apps/subjects/${subjectId}/tasks/detail/${item.id}`}>
												<div>
													{item.taskTitle}
												</div>
											</Link>
										</td>
										<td>{`${year}-${month}-${day} ${hours}시 ${minutes}분`}</td>
									</tr>
								);
							})
						) : (
							<tr>
								<td colSpan="4">등록된 과제가 없습니다.</td>
							</tr>
						)}

					</tbody>
				</Table>
			</Card.Body>
		</Card>
	);
};

export { TaskTable };
