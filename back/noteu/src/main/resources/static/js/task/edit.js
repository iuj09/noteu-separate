const btn_edit = document.getElementById("btn_edit");

var subjectId = document.getElementById("subjectId").value;
var taskId = document.getElementById("taskId").value;

btn_edit.addEventListener("click", () => {
    var taskTitle = document.getElementById("taskTitle").value;
    var taskContent = document.getElementById("taskContent").value;

    var deadLineValue = document.getElementById("deadLineInput").value;
    var formattedDate = new Date(deadLineValue).toISOString();
    
    $.ajax({
        type: "POST",
        url: `/subjects/${subjectId}/tasks/edit/${taskId}`,
        data: {
            "taskId": taskId,
            "taskTitle": taskTitle,
            "taskContent": taskContent,
            "deadLine": formattedDate,
        },
        datatype: "JSON",
        success: function(data) {
            alert("수정이 완료되었습니다.");
            location.href=`/subjects/${subjectId}/tasks`;
        },
        error: function(error) {
            alert("수정에 실패되었습니다.");
        },
    });
});