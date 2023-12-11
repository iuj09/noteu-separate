$(document).ready(function (){
    var createdAt = $("#createdAt").text();
    var formattedCreatedAt = formatCreatedAt(createdAt);
    $("#createdAt").text(formattedCreatedAt);

    var subjectId = $("#subjectId").val();
    var referenceRoomId = $("#referenceRoomId").val();

    $("#deleteBtn").on("click", function (){
       if(confirm("해당 게시물을 삭제하시겠습니까?")){
           $.ajax({
               url: "/subjects/" + subjectId + "/references/delete/" + referenceRoomId,
               type: "GET",
               success: function (response) {
                   location.href = "/subjects/" + subjectId + "/references";
                   alert("게시물 삭제가 완료되었습니다.");
               },
               error: function (request, status, error) {
                   console.log("code:"+request.status+"\n"+"error:"+error);
               }
           })
       }
    });
});

function formatCreatedAt(createdAt) {
    var date = new Date(createdAt);
    var year = date.getFullYear();
    var month = ('0' + (date.getMonth() + 1)).slice(-2);
    var day = ('0' + date.getDate()).slice(-2);
    var hours = ('0' + date.getHours()).slice(-2);
    var minutes = ('0' + date.getMinutes()).slice(-2);
    return year + '-' + month + '-' + day + ' ' + hours + '시 ' + minutes + '분';
}
