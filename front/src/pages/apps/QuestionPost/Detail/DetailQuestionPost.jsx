import axios from 'axios';
import { Row, Col, Card } from 'react-bootstrap';
import { CardTitle, PageBreadcrumb } from '@/components';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { extractClaims } from "@/pages/account/Login/extractClaims.js";
import Comments from './Comments';



const DetailQuestionPost = () => {
  
  const {questionPostId} = useParams();
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

  const writer = extractClaims().userId;

  useEffect(() => {
    axios.get(`http://localhost:8081/subjects/${subjectId}/questions/${questionPostId}`, {headers:{Authorization:token}, withCredentials: true})
    .then(res => {
      if(res.status === 200) {
        setData(res.data);
      } else {
        console.log("error : " + res.status);
      }
    })
  },[])

  const editQuestionPost = () => {
    navigate(`/apps/subjects/${subjectId}/questionPost/update/${questionPostId}`);
  };

  const deleteQuestionPost = async () => {
    try{
      const response = await axios.delete(`http://localhost:8081/subjects/${subjectId}/questions/${questionPostId}`, {headers:{Authorization:token}});
      console.log(response.status);
      navigate(`/apps/subjects/${subjectId}/questionPost/list`);
    } catch(error) {
      console.log("error : " + error);
    }
  }

  return (
    <>
      <PageBreadcrumb title="QuestionPost Detail" subName="QuestionPost" />

      <Row>
        <Col>
          <Card className="d-block">
            <Card.Body>
            <CardTitle
                containerClass="d-flex justify-content-between align-items-center mb-2"
                icon="ri-more-fill"
                title={<h3>{data ? data.questionPostTitle : 'Loding...'}</h3>}
                menuItems={(data && writer === data.memberId) && ([
                  { label: 'Edit', icon: 'mdi mdi-pencil', onClick: editQuestionPost },
                  { label: 'Delete', icon: 'mdi mdi-delete', onClick: deleteQuestionPost },
                ])}
              />
            <hr />
            <div dangerouslySetInnerHTML={{ __html: data ? data.questionPostContent : 'Loading...' }}>
            </div>
            </Card.Body>
          </Card>

          <Comments comment={data ? data.comment : []} commentCount={data ? data.commentCount : ""} />
        </Col>
      </Row>
    </>
  );
};

export { DetailQuestionPost };
