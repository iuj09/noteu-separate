$(document).ready(function() {
    const div_edit = document.getElementById("div_edit");
    const div_delete = document.getElementById("div_delete");
    const btn_task_comment = document.getElementById("btn_task_comment");
    const btn_submit = document.getElementById("btn_submit");
    const div_delete_comment = document.getElementById("div_delete_comment");
    
    var taskCommentFile = document.getElementById("taskCommentFile");
    var taskId = document.getElementById("taskId").value;
    var subjectId = document.getElementById("subjectId").value;

    btn_task_comment.addEventListener("click", (e) => {
        e.preventDefault();
        taskCommentFile.click();
    });

    btn_submit.addEventListener("click", () => {
        var taskId = document.getElementById("taskId").value;
        var taskCommentTitle = document.getElementById("taskCommentTitle").value;
        var taskCommentFile = document.getElementById("taskCommentFile").files[0];

        console.log(taskCommentFile);

        var formData = new FormData();
        formData.append("taskCommentTitle", taskCommentTitle);
        formData.append("taskCommentFile", taskCommentFile);

        $.ajax({
            type: "POST",
            url: `/subjects/${subjectId}/tasks/${taskId}/task-comment`,
            data: formData,
            processData: false,
            contentType: false,
            datatype: "JSON",
            success: function(data) {
                alert("과제 제출이 완료되었습니다.");
                location.reload();
            },
            error: function(error) {
                alert("과제 제출 실패");
            },
        });
    });

    div_delete_comment.addEventListener("click", () => {
        if(!confirm("삭제하시겠습니까?")){
            alert("삭제가 취소되었습니다.");
            return;
        }

        var taskId = document.getElementById("taskId").value;
        var taskCommentId = document.getElementById("taskCommentId").value;

        $.ajax({
            type: "POST",
            url: `/subjects/${subjectId}/tasks/${taskId}/${taskCommentId}`,
            data: {
                "taskCommentId": taskCommentId
            },
            datatype: "JSON",
            success: function(data) {
                alert("과제 삭제가 완료되었습니다.");
                location.reload();
            },
            error: function(error) {
                alert("과제 삭제 실패");
            },
        });
    });

    div_delete.addEventListener("click", () => {
        $.ajax({
            type: "POST",
            url: `/subjects/${subjectId}/tasks/${taskId}`,
            data: {
                "taskId": taskId
            },
            datatype: "JSON",
            success: function(data) {
                alert("삭제가 완료되었습니다.");
                location.href=`/subjects/${taskId}/tasks`;
            },
            error: function(error) {
                alert("삭제에 실패되었습니다.");
            },
        });
    });

    div_edit.addEventListener("click", () => {
        location.href = `/subjects/${subjectId}/tasks/${taskId}/edit-form`;
    });
    
});

function upload(input) {
    var selectedFile = input.files[0];
    if (selectedFile) {
        console.log("Selected File Name: ", selectedFile.name);
    } else {
        console.log("No file selected.");
    }
}