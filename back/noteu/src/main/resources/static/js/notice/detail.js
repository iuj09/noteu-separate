$(document).ready(function() {
    const div_edit = document.getElementById("div_edit");
    const div_delete = document.getElementById("div_delete");

    var noticeId = document.getElementById("noticeId").value;
    var subjectId = document.getElementById("subjectId").value;

    console.log(subjectId)

    div_delete.addEventListener("click", () => {
        $.ajax({
            type: "POST",
            url: `/subjects/${subjectId}/notices/${noticeId}`,
            // headers: {
            //     'Authorization': `Bearer ${getCookie("access_token")}`,
            // },
            data: {
                "noticeId": noticeId
            },
            datatype: "JSON",
            success: function(data) {
                alert("삭제가 완료되었습니다.");
                location.href=`/subjects/${subjectId}/notices`;
            },
            error: function(error) {
                alert("삭제에 실패되었습니다.");
            },
        });
    });

    div_edit.addEventListener("click", () => {
        location.href = `/subjects/${subjectId}/notices/${noticeId}/edit-form`;
    })
});