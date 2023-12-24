import { Row, Col, Card, Button, Form } from 'react-bootstrap';
import { Form as RHForm } from '@/components';
import {
  PageBreadcrumb,
  CustomDatePicker,
  TextInput,
  TextAreaInput,
} from '@/components';
import { useTaskForm } from '../hooks';

const CreateTask = () => {

  const { schema, deadLine, setDeadLine, handleValidSubmit } = useTaskForm();

  return (
    <>
      <PageBreadcrumb title="Create Task" subName="Task" />

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
                          name="taskTitle"
                          label="Task Title"
                          placeholder="Enter Task Title"
                          containerClass={'mb-3'}
                          key="name"
                        />
                      </Col>
                    </Row>

                    <Row>
                      <Col xl={6}>
                        <Form.Group className="mb-3 position-relative">
                          <Form.Label>Dead Line</Form.Label>
                          <CustomDatePicker
                            name="deadLine"
                            className="form-control"
                            value={deadLine}
                            onChange={(date) => {setDeadLine(date); console.log(date);}}
                          />
                        </Form.Group>
                      </Col>
                    </Row>

                    <Row>
                      <Col xl={6}>
                        <TextAreaInput
                          name="taskContent"
                          label="Task Content"
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

export { CreateTask };
