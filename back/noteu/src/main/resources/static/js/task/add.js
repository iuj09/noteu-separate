const btn_submit = document.getElementById("btn_submit");

$(document).ready(function () {
    $('#deadLine').datepicker({
        format: 'd-M-yyyy',
        autoclose: true
    });
});

btn_submit.addEventListener("click", () => {
    var subjectId = document.getElementById("subjectId").value;

    var taskTitle = document.getElementById("taskTitle").value;
    var taskContent = document.getElementById("taskContent").value;
    
    var deadLineValue = document.getElementById("deadLineInput").value;
    var formattedDate = new Date(deadLineValue).toISOString();

    $.ajax({
        type: "POST",
        url: `/subjects/${subjectId}/tasks`,
        data: {
            "taskTitle": taskTitle,
            "taskContent": taskContent,
            "deadLine": formattedDate,
        },
        datatype: "JSON",
        success: function(data) {
            alert("과제 등록 완료");
            location.href=`/subjects/${subjectId}/tasks`;
        },
        error: function(error) {
            alert("과제 등록 실패");
            console.log(formattedDate);
        },
    });
});
