$(document).ready(function() {
   var selectedFiles = []; // 선택한 파일 목록을 저장할 배열

   // 파일 선택 시 파일 미리보기 추가
   $('#files').change(function() {
      var files = $(this).prop("files");

      // 기존 파일 미리보기 삭제
      $('#fileThumbnail').empty();

      // 새로운 파일 미리보기 추가
      for (var i = 0; i < files.length; i++) {
         $.addFilePreview(files[i]);
         selectedFiles.push(files[i]); // 선택한 파일을 selectedFiles 배열에 추가
      }
   });

   // 작성 클릭 시
   $("#submitBtn").click(function() {
      var subjectId = $("#subjectId").val();
      //formData 생성
      var formData = new FormData();
      // 제목과 내용 입력값 가져오기
      var title = $("#title").val();
      var content = $("#content").val();
      formData.append("referenceRoomTitle", title);
      formData.append("referenceRoomContent", content);

      if (selectedFiles.length === 0) {
         alert("파일을 추가해 주세요.");
         return;
      }

      for (var i = 0; i < selectedFiles.length; i++) {
         formData.append("referenceFile", selectedFiles[i]);
      }

      console.log(subjectId);

      // FormData의 key 확인
      for (let key of formData.keys()) {
         console.log(key);
      }

      // FormData의 value 확인
      for (let value of formData.values()) {
         console.log(value);
      }

      // AJAX 요청 생성
      $.ajax({
         url: "/subjects/" + subjectId + "/references",
         type: "POST",
         data: formData,
         enctype: "multipart/form-data",
         processData: false,
         contentType: false,
         success: function(response) {
            console.log("데이터 전송 성공");
            location.href = "/subjects/" + subjectId + "/references";
         },
         error: function(request, status, error) {
            console.log("code:"+request.status+"\n"+"error:"+error);
         }
      });
   });

   $.createFilePreview = function (file) {
      var filePreview = $('<div class="file-preview"></div>');
      var fileName = $("<span class='file-name'></span>").text(file.name);
      var deleteButton = $('<button class="btn btn-danger btn-sm delete-button">Delete</button>').click(function() {
         $.deleteFilePreview(filePreview, file);
      });

      filePreview.append(fileName);
      filePreview.append(deleteButton);

      return filePreview;
   }

   $.addFilePreview = function (file) {
      var fileThumbnail = $("#fileThumbnail");
      var filePreview = $.createFilePreview(file);

      fileThumbnail.append(filePreview);
   }

   $.deleteFilePreview = function (filePreview, file) {
      filePreview.remove();
      selectedFiles = selectedFiles.filter(function(f) {
         return f.name !== file.name;
      });
   }
});
