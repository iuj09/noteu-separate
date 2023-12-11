const del = () => {
    const memberId = document.getElementById("delete_member").value;
    console.log(memberId);
    if (confirm("정말로 탈퇴하시겠습니까?")) {
        location.href = "/members/delete/" + memberId;
    } else {
        alert("탈퇴가 취소되었습니다.");
        return;
    }
}

var nameResult = false;
var emailResult = false;
var telResult = false;

const nameCheck = () => {
    var name = document.getElementById('name');
    var nameMsg = document.getElementById('name_msg');

    if (name.value.length === 0) {
        name.classList.remove('is-valid');
        name.classList.add('is-invalid');
        nameMsg.innerHTML = '이름을 입력해주세요.';
        nameResult = false;
    } else if (new RegExp("^[a-zA-Z]+$").test(name.value) || new RegExp("^[가-힣]+$").test(name.value)) {
        name.classList.remove('is-invalid');
        name.classList.add('is-valid');
        nameMsg.innerHTML = '';
        nameResult = true;
    } else {
        name.classList.remove('is-valid');
        name.classList.add('is-invalid');
        nameMsg.innerHTML = '영어 또는 한글만 입력할 수 있습니다.';
        nameResult = false;
    }
};

const emailCheck = () => {
    var email = document.getElementById('email');
    var emailInvaildMsg = document.getElementById('email_invalid_msg');
    var emailVaildMsg = document.getElementById('email_valid_msg');
    $.ajax({
        url: "/auth/email-check",
        type: "get",
        data: {"email": email.value},
        dataType: "text",
        success: function (flag) {
            if (!new RegExp("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$").test(email.value)) {
                email.classList.remove('is-valid');
                email.classList.add('is-invalid');
                emailInvaildMsg.innerHTML = "올바른 이메일 형식으로 입력해주세요.";
                emailResult = false;
            } else if (flag) {
                email.classList.remove('is-invalid');
                email.classList.add('is-valid');
                emailVaildMsg.innerHTML = "사용 가능한 이메일입니다.";
                emailResult = true;
            } else {
                email.classList.remove('is-valid');
                email.classList.add('is-invalid');
                emailInvaildMsg.innerHTML = "사용 불가능한 이메일입니다.";
                emailResult = false;
            }
        },
        error: function () {
            alert("Email Check error");
        },
    });
};;

const telCheck = () => {
    var tel = document.getElementById('tel');
    var telInvaildMsg = document.getElementById('tel_invalid_msg');
    var telVaildMsg = document.getElementById('tel_valid_msg');
    $.ajax({
        url: "/auth/tel-check",
        type: "get",
        data: {"email": tel.value},
        dataType: "text",
        success: function (flag) {
            if (!new RegExp("^01([0|1|6|7|8|9]?)([0-9]{3,4})([0-9]{4})$").test(tel.value)) {
                tel.classList.remove('is-valid');
                tel.classList.add('is-invalid');
                telInvaildMsg.innerHTML = "올바른 번호를 입력해주세요.";
                telResult = false;
            } else if (flag) {
                tel.classList.remove('is-invalid');
                tel.classList.add('is-valid');
                telVaildMsg.innerHTML = "사용 가능한 번호입니다.";
                telResult = true;
            } else {
                tel.classList.remove('is-valid');
                tel.classList.add('is-invalid');
                telInvaildMsg.innerHTML = "사용 불가능한 번호입니다.";
                telResult = false;
            }
        },
        error: function () {
            alert("Tel Check error");
        },
    });
};

const preventSubmit = () => {
    const form = document.querySelector("form");
    form.addEventListener("submit", (event) => {
        event.preventDefault();
    });
};

const edit = () => {
    if (!nameResult) {
        preventSubmit();
        nameCheck();
    }
    if (!emailResult) {
        preventSubmit();
        emailCheck();
    }
    if (!telResult) {
        preventSubmit()
        telCheck();
    }
    if (nameResult && emailResult && telResult) {
        document.getElementById("f").submit();
    }
}