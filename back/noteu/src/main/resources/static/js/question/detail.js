$(document).ready(function() {
    // SimpleMDE 초기화
    var contentElement = $("#content")[0];
    var simplemde = contentElement ? new SimpleMDE({element: contentElement}) : null;

    // SimpleMDE의 내용이 변경될 때마다 HTML로 변환하여 표시
    if (simplemde) {
        simplemde.codemirror.on("change", function() {
            var content = simplemde.markdown(simplemde.value());
            $("#contentPreview").html(content);
        });
    }

    $('.createdAt').each(function() {
        var createdAt = $(this).text();
        var formattedCreatedAt = formatCreatedAt(createdAt);
        $(this).text(formattedCreatedAt);
    });

    $(".userProfile").on("click", function (){
       var memberId = $(this).siblings(".memberId").val();

       location.href = "/members/account/" + memberId;
    });

    $(".memberRole").each(function (index, element) {
        console.log($(element).val());
        if($(element).val() === "TEACHER") {
            $(this).siblings(".answerMember").append("<span class='badge bg-primary me-1'>Teacher</span>");
        }
    });


    $("#deleteBtn").on("click", function (){
        if(confirm("해당 게시물을 삭제 하시겠습니까?")){
            var subjectId = $("#subjectId").val();
            var questionPostId = $("#questionPostId").val();

            $.ajax({
                url: "/subjects/" + subjectId + "/questions/delete/" + questionPostId,
                type: "GET",
                success: function (response) {
                    location.href = "/subjects/" + subjectId + "/questions";
                    alert("게시물 삭제가 완료되었습니다.");
                },
                error: function (request, status, error) {
                    console.log("code:"+request.status+"\n"+"error:"+error);
                }
            });
        }
    });

    $("#submitBtn").click(function (){
        if(confirm("댓글 작성을 완료하시겠습니까?")){
            var subjectId = $("#subjectId").val();
            var questionPostId = $("#questionPostId").val();
            var questionComment = $("#questionComment").val();

            $.ajax({
                url: "/subjects/" + subjectId + "/questions/" + questionPostId + "/question-comment",
                type:"POST",
                data:{"questionCommentContent": questionComment},
                success:function (response) {
                    location.reload();
                    alert("댓글 작성이 완료되었습니다.");
                },
                error:function () {
                    console.log("데이터 전송 실패");
                }
            });
        }
    });
8
    $(".deleteCommentBtn").on("click", function () {
        if(confirm("해당 댓글을 삭제 하시겠습니까?")){
            var subjectId = $("#subjectId").val();
            var questionPostId = $("#questionPostId").val();
            var questionCommentId = $(this).siblings(".questionPostCommentId").val();

            $.ajax({
                url: "/subjects/" + subjectId + "/questions/" + questionPostId + "/delete/" + questionCommentId,
                type:"GET",
                success:function (response){
                    location.reload();
                },
                error:function () {
                    console.log("데이터 전송 실패");
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
