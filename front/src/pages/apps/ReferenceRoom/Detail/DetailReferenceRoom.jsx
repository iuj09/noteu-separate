import axios from 'axios';
import { Row, Col, Card } from 'react-bootstrap';
import Files from './Files';
import { CardTitle, PageBreadcrumb } from '@/components';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { extractClaims } from "@/pages/account/Login/extractClaims.js";


const DetailReferenceRoom = () => {
  
  const {referenceRoomId} = useParams();
  const [data, setData] = useState(null);
  const token = localStorage.getItem("_NOTEU_AUTH").replace(/^"(.*)"$/, '$1');
  const navigate = useNavigate();

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

  const roleType = extractClaims().roleType;

  useEffect(() => {
    axios.get(`http://localhost:8081/subjects/${subjectId}/references/${referenceRoomId}`, {headers:{Authorization:token}, withCredentials: true})
    .then(res => {
      if(res.status === 200) {
        setData(res.data);
      } else {
        console.log("error : " + res.status);
      }
    })
  },[])

  const editReferenceRoom = () => {
    navigate(`/apps/subjects/${subjectId}/referenceRoom/update/${referenceRoomId}`);
  };

  const deleteReferenceRoom = async () => {
    try{
      const response = await axios.delete(`http://localhost:8081/subjects/${subjectId}/references/${referenceRoomId}`, {headers:{Authorization:token}});
      console.log(response.status);
      navigate(`/apps/subjects/${subjectId}/referenceRoom/list`);
    } catch(error) {
      console.log("error : " + error);
    }
  }

  return (
    <>
      <PageBreadcrumb title="ReferenceRoom Detail" subName="ReferenceRoom" />

      <Row>
        <Col>
          <Card className="d-block">
            <Card.Body>
              <CardTitle
                containerClass="d-flex justify-content-between align-items-center mb-2"
                icon="ri-more-fill"
                title={<h3>{data ? data.referenceRoomTitle : 'Loding...'}</h3>}
                menuItems={ roleType === "Teacher" && ([
                  { label: 'Edit', icon: 'mdi mdi-pencil', onClick: editReferenceRoom },
                  { label: 'Delete', icon: 'mdi mdi-delete', onClick: deleteReferenceRoom },
                ])}
              />
              <hr />
              <p className="text-muted mb-2">
                {data ? data.referenceRoomContent : 'Loding...'}
              </p>
              <Files reference={data ? data.reference : []} />
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </>
  );
};

export { DetailReferenceRoom };
