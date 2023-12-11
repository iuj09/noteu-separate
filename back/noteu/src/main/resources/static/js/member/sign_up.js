let req = new XMLHttpRequest();
var idResult = false;
var passwordResult = false;
var nameResult = false;
var emailResult = false;
var telResult = false;
var roleResult = false;
var termResult = false;

const idCheck = () => {
    var username = document.getElementById('username');
    var usernameInvalidMsg = document.getElementById('username_invalid_msg');
    var usernameValidMsg = document.getElementById('username_valid_msg');
    $.ajax({
        url: "/auth/id-check",
        type: "get",
        data: {"id": username.value},
        dataType: "text",
        success: function (res) {
            const parse = JSON.parse(res);
            if (!parse.flag) {
                username.classList.remove('is-valid');
                username.classList.add('is-invalid');
                usernameInvalidMsg.innerHTML = parse.msg;
                idResult = false;
            } else {
                username.classList.remove('is-invalid');
                username.classList.add('is-valid');
                usernameValidMsg.innerHTML = parse.msg;
                idResult = true;
            }
        },
        error: function () {
            alert("ID Check error");
        },
    })
};

const passwordVaild = () => {
    var password1 = document.getElementById('password1');
    var password2 = document.getElementById('password2');
    var passwordMsg = document.getElementById('password_msg');
    const passwordRegExp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$";

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
    var password1 = document.getElementById('password1');
    var password2 = document.getElementById('password2');
    var passwordMsg = document.getElementById('password_msg');
    var passwordCheckMsg = document.getElementById('password_check_msg');

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

window.onload = function() {
    var form = document.getElementById('f');
    var teacherRadio = document.getElementById('teacher');
    var studentRadio = document.getElementById('student');
    var roleMsg = document.getElementById('role_msg');
    var termCheckbox = document.getElementById('term_checkbox');
    var termMsg = document.getElementById('term_msg');

    teacherRadio.addEventListener('change', handleRadioChange);
    studentRadio.addEventListener('change', handleRadioChange);
    termCheckbox.addEventListener('change', handleCheckboxChange);

    form.addEventListener('submit', function (event) {
        if (!teacherRadio.checked && !studentRadio.checked) {
            event.preventDefault();
            teacherRadio.classList.add('is-invalid');
            studentRadio.classList.add('is-invalid');
            roleMsg.innerHTML = '타입을 선택해주세요.';
        }

        if (!termCheckbox.checked) {
            event.preventDefault();
            termCheckbox.classList.add('is-invalid');
            termMsg.innerHTML = 'You must agree before submitting.';
        }
    });

    function handleRadioChange() {
        if (teacherRadio.checked || studentRadio.checked) {
            teacherRadio.classList.remove('is-invalid');
            studentRadio.classList.remove('is-invalid');
            teacherRadio.classList.add('is-valid');
            studentRadio.classList.add('is-valid');
            roleMsg.innerHTML = '';
            roleResult = true;
        }
    }

    function handleCheckboxChange() {
        if (termCheckbox.checked) {
            termCheckbox.classList.remove('is-invalid');
            termCheckbox.classList.add('is-valid');
            termMsg.innerHTML = '';
            termResult = true;
        } else {
            termCheckbox.classList.remove('is-valid');
            termMsg.innerHTML = '';
            termResult = false;
        }
    }
}

const preventSubmit = () => {
    const form = document.querySelector("form");
    form.addEventListener("submit", (event) => {
        event.preventDefault();
    });
};

const join = () => {
    if (!idResult) {
        preventSubmit();
        var username = document.getElementById('username');
        var usernameInvalidMsg = document.getElementById('username_invalid_msg');
        username.classList.remove('is-valid');
        username.classList.add('is-invalid');
        usernameInvalidMsg.innerHTML = 'ID Check required.';
    }
    if (!passwordResult) {
        preventSubmit();
        passwordVaild();
        passwordCheck();
    }
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
    if (!(roleResult && termResult)) {
        preventSubmit();
    }
    if (idResult && passwordResult && nameResult && emailResult && telResult && roleResult && termResult) {
        document.getElementById("f").submit();
    }
}
