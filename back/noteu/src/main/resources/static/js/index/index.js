$(document).ready(function (){
   $("#join").on("click", function (){
      location.href = "/auth/sign-up";
   });

   $("#login").on("click", function (){
      location.href = "/auth/login";
   });
});

// 초기 카운트 값을 설정합니다.
let like1Count = 172;
let heart1Count = 890;
let firecracker1Count = 121;
let heart2Count = 898;
let trophy1Count = 122;

let firecracker2Count = 1004;
let heart3Count = 486;
let thumbsUp1Count = 8282;

document.getElementById('like1Button').addEventListener('click', function() {
   like1Count++;
   document.getElementById('like1Count').innerText = like1Count;
});

document.getElementById('heart1Button').addEventListener('click', function() {
   heart1Count++;
   document.getElementById('heart1Count').innerText = heart1Count;
});

document.getElementById('firecracker1Button').addEventListener('click', function() {
   firecracker1Count++;
   document.getElementById('firecracker1Count').innerText = firecracker1Count;
});

document.getElementById('heart2Button').addEventListener('click', function() {
   heart2Count++;
   document.getElementById('heart2Count').innerText = heart2Count;
});

document.getElementById('trophy1Button').addEventListener('click', function() {
   trophy1Count++;
   document.getElementById('trophy1Count').innerText = trophy1Count;
});


document.getElementById('firecracker2Button').addEventListener('click', function() {
   firecracker2Count++;
   document.getElementById('firecracker2Count').innerText = firecracker2Count;
});

document.getElementById('heart3Button').addEventListener('click', function() {
   heart3Count++;
   document.getElementById('heart3Count').innerText = heart3Count;
})

document.getElementById('thumbsUp1Button').addEventListener('click', function() {
   thumbsUp1Count++;
   document.getElementById('thumbsUp1Count').innerText = thumbsUp1Count;
})

function copyToClipboard(val) {
   var t = document.createElement("textarea");
   document.body.appendChild(t);
   t.value = val;
   t.select();
   document.execCommand('copy');
   document.body.removeChild(t);
   alert('코드 복사 완료!')
}