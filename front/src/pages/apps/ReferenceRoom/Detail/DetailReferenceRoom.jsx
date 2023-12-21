import axios from 'axios';
import { Row, Col, Card } from 'react-bootstrap';
import Files from './Files';
import { CardTitle, PageBreadcrumb } from '@/components';
import { useNavigate, useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';

const DetailReferenceRoom = () => {
  
  const {referenceRoomId} = useParams();
  const [data, setData] = useState(null);
  const token = localStorage.getItem("_NOTEU_AUTH").replace(/^"(.*)"$/, '$1');
  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`http://localhost:8081/subjects/1/references/${referenceRoomId}`, {headers:{Authorization:token}, withCredentials: true})
    .then(res => {
      if(res.status === 200) {
        setData(res.data);
      } else {
        console.log("error : " + res.status);
      }
    })
  },[])

  const editReferenceRoom = () => {
    navigate(`/apps/referenceRoom/update/${referenceRoomId}`);
  };

  const deleteReferenceRoom = async () => {
    try{
      const response = await axios.delete(`http://localhost:8081/subjects/1/references/${referenceRoomId}`, {headers:{Authorization:token}});
      console.log(response.status);
      navigate(`/apps/referenceRoom/list/${referenceRoomId}`);
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
                menuItems={[
                  { label: 'Edit', icon: 'mdi mdi-pencil', onClick: editReferenceRoom },
                  { label: 'Delete', icon: 'mdi mdi-delete', onClick: deleteReferenceRoom },
                ]}
              />

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
