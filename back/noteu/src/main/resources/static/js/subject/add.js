const btn_submit = document.getElementById("btn_submit");

        btn_submit.addEventListener("click", () => {
            var subjectName = document.getElementById("subjectName").value;
            $.ajax({
                type: "POST",
                url: "/subjects",
                data: {
                    "subjectName": subjectName,
                },
                success: function(data) {
                    console.log("data.result: " + data.result);
                    alert("등록이 완료되었습니다");
                    location.href = "/subjects";
                },
                error: function(error) {
                    console.log(subjectName);
                    alert("등록 실패: " + JSON.stringify(error.responseJSON));
                },
            });
        });