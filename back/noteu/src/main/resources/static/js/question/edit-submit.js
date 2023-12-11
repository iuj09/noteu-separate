$(document).ready(function (){
    var simplemde = new SimpleMDE({element: $("#content")[0]});

    $("#submitBtn").click(function() {
        if(confirm("해당 게시물을 수정 하시겠습니까?")){
            var subjectId = $("#subjectId").val();
            var questionId = $("#questionPostId").val();
            var title = $("#title").val();
            var content = simplemde.value();

            console.log(subjectId);
            console.log(questionId)
            console.log(title);
            console.log(content);

            $.ajax({
                url: "/subjects/" + subjectId + "/questions/edit/" + questionId,
                type: "POST",
                data: {"questionPostTitle": title, "questionPostContent":content},
                success:function(response){
                    location.href = "/subjects/" + subjectId + "/questions";
                    alert("게시물 수정이 완료되었습니다.");
                },
                error:function (request, status, error){
                    console.log("code:"+request.status+"\n"+"error:"+error);
                }
            });
        }

    });

    $("#cancelBtn").click(function (){
        if(confirm("수정중인 게시물은 저장되지 않습니다.\n취소하시겠습니까?")){
            var subjectId = $("#subjectId").val();
            var questionId = $("#questionPostId").val();
            location.href = "/subjects/" + subjectId + "/questions/" + questionId;
        }
    });
});
