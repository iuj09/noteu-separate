import axios from 'axios';
import { Row, Col, Card } from 'react-bootstrap';
import { CardTitle, PageBreadcrumb } from '@/components';
import { useLocation } from 'react-router-dom';
import { useState, useEffect } from 'react';

const DetailNotice = () => {

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
  
  const [data, setData] = useState(null);
  const token = localStorage.getItem("_NOTEU_AUTH").replace(/^"(.*)"$/, '$1');

  useEffect(() => {
    axios.get(`http://localhost:8081/subjects/${subjectId}/notices`, {headers:{Authorization:token}})
    .then(res => {
      if(res.status === 200) {
        console.log(res.data)
        setData(res.data);
      } else {
        console.log("error : " + res.status);
      }
    })
  },[])

  return (
    <>
      <PageBreadcrumb title="Notice Detail" subName="Notice" />

      <Row>
        <Col>
          <Card className="d-block">
            <Card.Body>
              <CardTitle
                containerClass="d-flex justify-content-between align-items-center mb-2"
                icon="ri-more-fill"
                title={<h3>{data ? data[0].noticeTitle : 'Loding...'}</h3>}
                menuItems={[
                  { label: 'Edit', icon: 'mdi mdi-pencil' },
                  { label: 'Delete', icon: 'mdi mdi-delete' },
                ]}
              />
              <hr />

              <p className="text-muted mb-2">
                {data ? data[0].noticeContent : 'Loding...'}
              </p>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </>
  );
};

export { DetailNotice };
