import { Card, Table } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const ReferenceRoomTable = (props) => {

    const { list } = props;

	return (
		<Card>
			<Card.Body>
				<h4 className="header-title">자료실</h4>
				<p className="text-muted font-14">
                    수업에 필요한 자료 파일들을 다운로드 받을 수 있는 공간입니다.
				</p>

				<Table className="mb-0" hover>
					<thead>
						<tr>
							<th>#</th>
							<th>Title</th>
							<th>Date</th>
							<th>Views</th>
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
								<tr key={item.referenceRoomId}>
									<th scope="row">{item.referenceRoomId}</th>
									<td>
                                        <Link to={`/apps/referenceRoom/detail/${item.referenceRoomId}`}>
                                            <div>
                                                {item.referenceRoomTitle}
                                            </div>
                                        </Link>
                                    </td>
									<td>{`${year}-${month}-${day} ${hours}시 ${minutes}분`}</td>
									<td>{item.views}</td>
								</tr>
							);
						})}
					</tbody>
				</Table>
			</Card.Body>
		</Card>
	);
};

export { ReferenceRoomTable };
