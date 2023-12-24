import { Card } from 'react-bootstrap';
import { useLocation } from 'react-router-dom';

const TaskComments = ({ task }) => {

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


    const handleDownload = () => {
        const downloadLink = document.createElement('a');;
        downloadLink.href = `/subjects/${subjectId}/tasks/down?fileName=${task.taskCommentFileName}`;
        downloadLink.download = task.taskCommentFileName;
        downloadLink.click();
    };

    return (
        <Card>
            <Card.Body>
                <div className="d-flex">
                    <div className="w-100">
                        <h5 className="mt-0">
                            {task.taskCommentTitle}
                        </h5>
                        {task.member.memberName}
                        <hr />
                        <div>
                            파일 이름: <span style={{ color: 'blue', cursor: 'pointer' }} onClick={handleDownload}>{task.taskCommentFileName}</span>
                        </div>
                        <br />
                    </div>
                </div>
            </Card.Body>
        </Card>
    );
};

export default TaskComments;
