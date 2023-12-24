import { Row, Col, Card, Button } from 'react-bootstrap';
import { Form as RHForm } from '@/components';
import {
  PageBreadcrumb,
  TextInput,
  TextAreaInput,
} from '@/components';

const CreateNotice = () => {

  const { schema, handleValidSubmit } = useNoticeForm();

  return (
    <>
      <PageBreadcrumb title="Create Notice" subName="Notice" />

      <Row>
        <Col>
          <Card>
            <Card.Body>
              <Row>
                <Col>
                  <RHForm onSubmit={handleValidSubmit} schema={schema}>
                    <Row>
                      <Col xl={6}>
                        <TextInput
                          type="text"
                          name="noticeTitle"
                          label="Notice Title"
                          placeholder="Enter Notice Title"
                          containerClass={'mb-3'}
                          key="name"
                        />
                      </Col>
                    </Row>

                    <Row>
                      <Col xl={6}>
                        <TextAreaInput
                          name="noticeContent"
                          label="Notice Content"
                          rows={10}
                          containerClass={'mb-3'}
                          key="name"
                        />
                      </Col>
                    </Row>

                    <Row className="mt-2">
                      <Col>
                        <Button type="submit" variant="success">
                          Create
                        </Button>
                      </Col>
                    </Row>
                  </RHForm>
                </Col>
              </Row>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </>
  );
};

export { CreateNotice };
