import axios from 'axios';
import { Row, Col, Card, Button } from 'react-bootstrap';
import { Form, CardTitle, PageBreadcrumb } from '@/components';
import { useState, useEffect } from 'react';
import TaskComments from './TaskComments';
import jwt_decode from 'jwt-decode';
import { useLocation } from 'react-router-dom';

const DetailTask = () => {
  const [list, setList] = useState([]);
  const [data, setData] = useState(null);
  const [commentText, setCommentText] = useState('');
  const [taskCommentFile, setTaskCommentFile] = useState(null);
  const token = localStorage.getItem("_NOTEU_AUTH").replace(/^"(.*)"$/, '$1');

  const decodedToken = jwt_decode(token);

  const userId = decodedToken.username;

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

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`http://localhost:8081/subjects/${subjectId}/tasks`, {
          headers: { Authorization: token },
        });

        if (response.status === 200) {
          setData(response.data);
          console.log(response.data);
          const storedComments = localStorage.getItem('taskComments');
          if (storedComments) {
            // setList(JSON.parse(storedComments).filter((comment) => comment.member.username === userId));
            setList(JSON.parse(storedComments));
          } else if (response.data[0].taskCommentList) {
            setList(response.data[0].taskCommentList);
          }
        } else {
          console.log("error : " + response.status);
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, []);

  const fetchTaskComments = async () => {
    if (data && data.length > 0) {
      try {
        const taskCommentsResponse = await axios.get(`http://localhost:8081/subjects/${subjectId}/tasks/1`, {
          headers: { Authorization: token },
        });

        if (taskCommentsResponse.status === 200) {
          const commentsArray = taskCommentsResponse.data.taskCommentList || [taskCommentsResponse.data.taskCommentList];
          setList(commentsArray);
          localStorage.setItem('taskComments', JSON.stringify(commentsArray));
        } else {
          console.log("error: " + taskCommentsResponse.status);
        }
      } catch (error) {
        console.error("Error fetching task comment data:", error);
      }
    }
  };

  const handleCommentTextChange = (event) => {
    setCommentText(event.target.value);
  };

  const handleFileUpload = (event) => {
    const selectedFile = event.target.files[0];
    setTaskCommentFile(selectedFile);

    if (selectedFile) {
      console.log("Selected File Name: ", selectedFile.name);
    } else {
      console.log("No file selected.");
    }
  };

  const handleSubmit = async (event) => {
    const formData = new FormData();
    formData.append('taskCommentTitle', commentText);
    formData.append('taskCommentFile', taskCommentFile);

    try {
      const response = await axios.post(`http://localhost:8081/subjects/${subjectId}/tasks/1/task-comment`, formData, {
        headers: {
          'Authorization': token,
          'Content-Type': 'multipart/form-data',
        },
      });

      if (response.status === 201) {
        alert("과제 제출이 완료되었습니다.");
        fetchTaskComments();
      } else {
        alert("과제 제출에 실패하였습니다.");
        console.log("Error:", response.status);
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <>
      <PageBreadcrumb title="Task Detail" subName="Task" />

      <Row>
        <Col>
          <Card className="d-block">
            <Card.Body>
              <CardTitle
                containerClass="d-flex justify-content-between align-items-center mb-2"
                icon="ri-more-fill"
                title={<h3>{data ? data[0].taskTitle : 'Loading...'}</h3>}
                menuItems={[
                  { label: 'Edit', icon: 'mdi mdi-pencil' },
                  { label: 'Delete', icon: 'mdi mdi-delete' },
                ]}
              />
              <hr />

              {data ? data[0].deadLine : 'Loading...'}

              <hr />

              <p className="text-muted mb-2">
                {data ? data[0].taskContent : 'Loading...'}
              </p>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row>
        <Col>
          <div className="border rounded mt-4">
            <Form className="comment-area-box" onSubmit={handleSubmit}>
              <textarea
                rows={3}
                className="form-control border-0 resize-none"
                value={commentText}
                onChange={handleCommentTextChange}
              />
              <div className="p-2 bg-light d-flex justify-content-between align-items-center">
                <div>
                  <label htmlFor="taskCommentFile" className="btn btn-sm px-1 btn-light">
                    <i className="mdi mdi-upload"></i>
                  </label>
                  <input
                    type="file"
                    id="taskCommentFile"
                    name="taskCommentFile"
                    style={{ display: 'none' }}
                    onChange={handleFileUpload}
                  />
                </div>
                <Button type="submit" variant="success">
                  <i className="uil uil-message me-1"></i>Submit
                </Button>
              </div>
            </Form>
          </div>
        </Col>
      </Row>

      <Row>
        {list && list.length > 0 && (
          list.map((task) => (
            <Col xxl={8} xl={7} key={'taskComment-' + task.id}>
              <TaskComments task={task} />
            </Col>
          ))
        )}
      </Row>
    </>
  );
};

export { DetailTask };
