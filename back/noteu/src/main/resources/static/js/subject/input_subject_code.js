const btn_submit = document.getElementById("btn_submit");
        
btn_submit.addEventListener("click", () => {
    var subjectCode = document.getElementById("subjectCode").value;
    console.log(subjectCode);
    $.ajax({
        type: "POST",
        url: "/subjects/input-code",
        data: {
            "subjectCode": subjectCode,
        },
        success: function (data, textStatus, xhr) {
            if (xhr.status === 200) {
                console.log("data.result: " + data.result);
                alert(data);
                location.href = "/subjects";
            } else if (xhr.status === 409) {
                alert(data);
            }
        },
        error: function (error) {
            alert("과목코드를 잘못 입력하셨습니다. \n 과목코드를 다시 확인해주세요.");
        },
    });
});
