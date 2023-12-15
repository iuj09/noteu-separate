import axios from 'axios';
import { Row, Col, Card } from 'react-bootstrap';
import Files from './Files';
import { CardTitle, PageBreadcrumb } from '@/components';
import { useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';

const DetailReferenceRoom = () => {
  
  const {referenceRoomId} = useParams();
  const [data, setData] = useState(null);
  const token = localStorage.getItem("_NOTEU_AUTH");

  useEffect(() => {
    axios.get("http://localhost:8081/subjects/1/references/" + referenceRoomId, {headers:{Authorization:token}})
    .then(res => {
      if(res.status === 200) {
        setData(res.data);
      } else {
        console.log("error : " + res.status);
      }
    })
  },[])

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
                  { label: 'Edit', icon: 'mdi mdi-pencil' },
                  { label: 'Delete', icon: 'mdi mdi-delete' },
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
