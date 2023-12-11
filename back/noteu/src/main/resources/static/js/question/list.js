$(document).ready(function (){
    $('.createdAt').each(function() {
        var createdAt = $(this).text();
        var formattedCreatedAt = formatCreatedAt(createdAt);
        $(this).text(formattedCreatedAt);
    });

    $("#search").on("submit", function () {
       var searchType = $("#searchType").val();
       var searchWord = $("#searchWord").val();

       if(searchType == 0) {
           alert("검색 유형을 선택 해 주세요.");
           event.preventDefault();
       } else {
           if(searchWord.length < 2) {
               alert("두 음절 이상의 단어로 검색 해 주세요");
               event.preventDefault();
           }
       }
    });

    $(".detail").click(function (){
        var questionId = $(this).parents().siblings(".questionPostId").val();
        var subjectId = $(this).parents().siblings(".subjectId").val();

        console.log(questionId);
        console.log(subjectId);

        $.ajax({
            url: "/subjects/" + subjectId + "/questions/" + questionId,
            type: "GET",
            success: function (response) {
                location.href = "/subjects/" + subjectId + "/questions/" + questionId;
            },
            error:function(request, status, error){
                console.log("code:"+request.status+"\n"+"error:"+error);
            }
        });
    });

    $(".userProfile").on("click", function(){
        var memberId = $(this).parents().siblings(".memberId").val();

        location.href = "/members/account/" + memberId;
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
