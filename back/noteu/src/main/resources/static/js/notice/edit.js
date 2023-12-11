const btn_edit = document.getElementById("btn_edit");

var subjectId = document.getElementById("subjectId").value;
var noticeId = document.getElementById("noticeId").value;

btn_edit.addEventListener("click", () => {
    var noticeContent = document.getElementById("noticeContent").value;
    var noticeTitle = document.getElementById("noticeTitle").value;
    
    $.ajax({
        type: "POST",
        url: `/subjects/${subjectId}/notices/edit/${noticeId}`,
        // headers: {
        //     'Authorization': `Bearer ${getCookie("access_token")}`,
        // },
        data: {
            "noticeId": noticeId,
            "noticeTitle": noticeTitle,
            "noticeContent": noticeContent
        },
        datatype: "JSON",
        success: function(data) {
            alert("수정이 완료되었습니다.");
            location.href=`/subjects/${subjectId}/notices`;
        },
        error: function(error) {
            alert("수정에 실패되었습니다.");
        },
    });
});