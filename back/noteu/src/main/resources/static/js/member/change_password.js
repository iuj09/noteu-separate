let req = new XMLHttpRequest();
var previousPasswordResult = false;
var passwordResult = false;
const previousPassword = document.getElementById('previous_password');
const password1 = document.getElementById('password1');
const password2 = document.getElementById('password2');
const previousPasswordMsg = document.getElementById('previous_password_msg');
const passwordMsg = document.getElementById('password_msg');
const passwordCheckMsg = document.getElementById('password_check_msg');
const passwordRegExp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$";

function passwordChange() {
    $.ajax({
        url: "/members/pw-check",
        type: "POST",
        data: {"previousPassword": previousPassword.value, "newPassword": password1.value},
        dataType: "text",
        success: function (data) {
            if (data === "1") {
                previousPassword.classList.remove('is-invalid');
                previousPassword.classList.add('is-valid');
                previousPasswordMsg.innerHTML = '';
                previousPasswordResult = true;
            } else if (data === "0") {
                previousPassword.classList.remove('is-valid');
                previousPassword.classList.add('is-invalid');
                previousPasswordMsg.innerHTML = '이전 비밀번호가 일치하지 않습니다.';
                previousPasswordResult = false;
            }
        },
        error: function () {
            console.log("비밀번호 일치 확인 ajax 요청 실패");
        }
    });
}

const passwordVaild = () => {
    if (!new RegExp(passwordRegExp).test(password1.value)) {
        password1.classList.remove('is-valid');
        password2.classList.remove('is-valid');
        password1.classList.add('is-invalid');
        passwordMsg.innerHTML = '영어, 숫자, 특수 문자를 포함하여 8자 이상, 15자 이하로 입력해주세요.';
        passwordResult = false;
    } else {
        password1.classList.remove('is-invalid');
        password1.classList.add('is-valid');
        passwordCheck();
    }
};

const passwordCheck = () => {
    if (password1.value === password2.value) {
        password2.classList.remove('is-invalid');
        password2.classList.add('is-valid');
        passwordCheckMsg.innerHTML = '';
        passwordResult = true;
    } else {
        password2.classList.remove('is-valid');
        password2.classList.add('is-invalid');
        passwordMsg.innerHTML = '';
        passwordCheckMsg.innerHTML = '비밀번호와 비밀번호 확인이 일치하지 않습니다.';
        passwordResult = false;
    }
};

const preventSubmit = () => {
    const form = document.getElementById("f");
    form.addEventListener("submit", (event) => {
        event.preventDefault();
    });
};

const changePassword = () => {
    if (!previousPasswordResult) {
        preventSubmit();
        passwordChange();
    }
    if (!passwordResult) {
        preventSubmit();
        passwordVaild();
    }
    if (previousPasswordResult && passwordResult) {
        document.getElementById("f").submit();
    }
}
